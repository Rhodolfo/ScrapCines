package com.rho.scrap

object CinepolisHTTP {
  
  import org.apache.http.client.methods.HttpPost
  import org.apache.http.entity.StringEntity
  import org.apache.http.impl.client.DefaultHttpClient
  import com.rho.client.RhoClient
  import com.rho.scrap.CinepolisParse.{parseZones,parseCinemas}
  import com.rho.scrap.CinepolisClasses.{Cinema}

  val scheme = "http"
  val host = "www.cinepolis.com"
  val client = new RhoClient(Scheme="http",Host=host)
  val optionsPath = "/cartelera/cdmx-centro/"
  val cinemasPath = "/Cartelera.aspx/GetNowPlayingByCity"
  
  def getZones: List[String] = parseZones(client.doGET(Map(),optionsPath))

  def getCinemas(zone: String): List[Cinema] = {
    val json = "{\"claveCiudad\":\""+zone+"\",\"esVIP\":false}"
    val post = new HttpPost(scheme+"://"+host+cinemasPath)
    post.setHeader("content-type", "application/json")
    post.setEntity(new StringEntity(json))
    parseCinemas(client.doRequest(post))
  }

}
