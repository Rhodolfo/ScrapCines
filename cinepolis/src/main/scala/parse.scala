package com.rho.scrap

object CinepolisParse {
  
  import com.rho.client.RhoClient
  import scala.util.matching.Regex
  import net.liftweb.json._
  import com.rho.scrap.CinepolisClasses.{Cinema}

  implicit val formats = DefaultFormats

  def parseZones(body: String): List[String] = {
    val list = (new Regex("id=.cmbCiudades.+?</select>") findFirstIn body) match {
      case Some(x) => x
      case None => throw new Error("parseZones error")
    }
    (new Regex("Clave=\"(.+?)\"") findAllMatchIn list).toList map {_.group(1)}
  }

  def parseCinemas(body: String): List[Cinema] = {
    for {
      result @ JObject(x) <- (parse(body) \ "d" \ "Cinemas")
      JField("CityKey",JString(cityKey)) <- result.obj
      JField("CityName",JString(cityName)) <- result.obj
      JField("Id",JInt(id)) <- result.obj
      JField("Key",JString(key)) <- result.obj
      JField("Name",JString(name)) <- result.obj
    } yield {
      Cinema(cityKey, cityName, id.toInt, key, name)
    }
  }

}
