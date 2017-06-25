package com.rho.scrap 

object CinemexClasses {

  private def cl(s: String): String = {
    import scala.util.matching.Regex 
    new Regex("\\|") replaceAllIn(s," ")
  }
  private def concat(a: String, b: String): String = if (a.isEmpty) b else a+"|"+b

  case class Zone(id: Int, name: String) {
    val valid: Boolean = if (id>0) true else throw new Error("id cannot be null")
  }
  case class Cinema(pos: String, address: String, url: String, name: String) {
    val (lat,lon) = pos.split(",") match {case x:(Array[String]) => (x(0),x(1))}
    override def toString: String = List(lat,lon,address,url,name).map(cl).foldLeft[String]("")(concat) 
    def header: String = List("lat","lon","address","url","name").foldLeft[String]("")(concat)
  }

}
