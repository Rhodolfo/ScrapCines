package com.rho.scrap

object CinepolisHTTP {
  
  import org.apache.http.client.methods.HttpPost
  import org.apache.http.entity.StringEntity
  import org.apache.http.impl.client.DefaultHttpClient
  import com.rho.client.RhoClient
  import com.rho.scrap.CinepolisParse.{parseZones,parseCinemas,parseComplex}
  import com.rho.scrap.CinepolisClasses.{Cinema,Complex}

  val scheme = "http"
  val host = "www.cinepolis.com"
  val client = new RhoClient(Scheme="http",Host=host)
  val optionsPath = "/cartelera/cdmx-centro/"
  val cinemasPath = "/Cartelera.aspx/GetNowPlayingByCity"
  val complexPath = "/Cartelera.aspx/ObtenerInformacionComplejo"
  val prefix = "[HTTP] "
  
  def getZones: List[String] = {
    System.out.println(prefix+"Fetching zone list")
    val list = parseZones(client.doGET(Map(),optionsPath))
    System.out.println(prefix+"Done")
    list
  }

  private def constructPOST(json: String, path: String): HttpPost = {
    val post = new HttpPost(scheme+"://"+host+path)
    post.setHeader("content-type", "application/json")
    post.setEntity(new StringEntity(json))
    post
  }

  def getCinemas(zone: String): List[Cinema] = {
    val json = "{\"claveCiudad\":\""+zone+"\",\"esVIP\":false}"
    val post = constructPOST(json, cinemasPath)
    System.out.println(prefix+"Fetching Cinemas for zone "+zone)
    val body = client.doRequest(post)
    System.out.println(prefix+"Done")
    parseCinemas(body)
  }

  def getComplex(cinema: Cinema): Complex = {
    val json = "{\"idComplejo\":"+cinema.Id+",\"HijosComplejo\":\"\"}"
    val post = constructPOST(json, complexPath)
    System.out.println(prefix+"Fetching complex for "+cinema.Id+": "+cinema.Name)
    val body = client.doRequest(post)
    System.out.println(prefix+"Done")
    parseComplex(body)
  }

}
