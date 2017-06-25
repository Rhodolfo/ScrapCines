package com.rho.scrap

object CinemexParse {
  
  import scala.util.matching.Regex
  import com.rho.scrap.CinemexClasses.{Zone,Cinema}

  def parseZones(body: String): List[Zone] = {
    val options = (new Regex("cinemas-select-city.+?</select>") findFirstIn body) match {
      case Some(x) => x
      case None => throw new Error("Danger Zone")
    }
    (new Regex("value=.(\\d+?)\\D.*?>(.+?)<") findAllMatchIn options).toList
      .map {x => Zone(x.group(1).toInt,x.group(2))}
  }

  def parseCinemas(body: String): List[Cinema] = {
    def extractCinema(entry: String): Cinema = {
      val pos = (new Regex("data-pos=\"(.+?)\"") findFirstMatchIn entry) match {
        case Some(x) => x.group(1)
        case None => throw new Error("Pos")
      }
      val address = (new Regex("data-address=\"(.+?)\"") findFirstMatchIn entry) match {
        case Some(x) => x.group(1)
        case None => throw new Error("Address")
      }
      val (url,name) = (new Regex("<a\\s+?href=\"(.+?)\">(.+?)</a>") findFirstMatchIn entry) match {
        case Some(x) => (x.group(1),x.group(2))
        case None => throw new Error("URL")
      }
      Cinema(pos,address,url,name)
    }
    (new Regex("<li\\s+?id=.cinema.+?\"\\s+?class=.cinema-item.+?</li>") findAllIn body).toList
      .map(extractCinema)
  }

}
