package com.rho.scrap 

object CinepolisClasses {

  private def cl(s: String): String = {
    import scala.util.matching.Regex 
    new Regex("\\|") replaceAllIn(s," ")
  }
  private def concat(a: String, b: String): String = if (a.isEmpty) a else a+"|"+b

  case class Cinema(CityKey: String, CityName: String, Id: Int, Key: String, Name: String)
  case class Complex(IdComplejo: Int, Nombre: String, CodigoCiudad: String, CodigoComplejo: String, 
    Direccion: String, Latitud: String, Longitud: String, Salas: Int) {
    override def toString: String = {
      List(IdComplejo.toString,Nombre,CodigoCiudad,CodigoComplejo, 
      Direccion,Latitud,Longitud,Salas.toString).map(cl).foldLeft[String]("")(concat) 
    }
    def header: String = {
      List("IdComplejo","Nombre","CodigoCiudad","CodigoComplejo",
      "Direccion","Latitud","Longitud","Salas").map(cl).foldLeft[String]("")(concat)
    } 
  }

}
