package com.rho.scrap

object CinepolisParse {
  
  import scala.util.matching.Regex
  import net.liftweb.json._
  import com.rho.scrap.CinepolisClasses.{Cinema,Complex}

  implicit val formats = DefaultFormats

  private def changeKeys(body: String): String = {
    def iter(list: List[(String,String)], acc: String): String = {
      if (list.isEmpty) acc
      else {
        val (key,newKey) = list.head
        iter(list.tail, new Regex(key) replaceAllIn(acc,newKey))
      }
    }
    val keyList = List(
      ("Id","id"), ("Key","key"), ("Name","name"),
      ("Citykey","cityKey"), ("Cityname","cityName"))
    iter(keyList, body)
  }

  def parseZones(body: String): List[String] = {
    val list = (new Regex("id=.cmbCiudades.+?</select>") findFirstIn body) match {
      case Some(x) => x
      case None => throw new Error("parseZones error")
    }
    (new Regex("Clave=\"(.+?)\"") findAllMatchIn list).toList map {_.group(1)}
  }

  def parseCinemas(body: String): List[Cinema] = {
    val json = (parse(body) \ "d" \ "Cinemas").children
    for {e <- json} yield {e.extract[Cinema]}
  }

  def parseComplex(body: String): Complex = (parse(body) \ "d" \ "datos_complejo").extract[Complex]

}
