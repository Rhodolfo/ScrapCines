object Cinepolis {

  import com.rho.file.quickFunc.writeToFile
  import com.rho.scrap.CinepolisHTTP.{getZones,getCinemas}


  val prefix = "[Cinepolis] "

  def main(args: Array[String]) {
    val zones = getZones
    val cinemas = zones.map(getCinemas)
    println(cinemas.size)
  }

}
