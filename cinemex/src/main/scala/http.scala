package com.rho.scrap

object CinemexHTTP {
  
  import com.rho.client.RhoClient
  import com.rho.scrap.CinemexParse.{parseZones,parseCinemas}
  import com.rho.scrap.CinemexClasses.{Zone,Cinema}

  val scheme = "https"
  val host = "www.cinemex.com"
  val client = new RhoClient(Scheme=scheme,Host=host)
  val path = "/cines/"
  val prefix = "[HTTP] "

  def getZones: List[Zone] = {
    System.out.println(prefix+"Getting zones")
    val body = client.doGET(Map(),path+"1")
    System.out.println(prefix+"Done")
    parseZones(body)
  }

  def getCinemas(zone: Zone): List[Cinema] = {
    System.out.println(prefix+"Getting cinemas for zone "+zone.id+": "+zone.name)
    val body = client.doGET(Map(),path+zone.id)
    System.out.println(prefix+"Done")
    parseCinemas(body)
  }


}
