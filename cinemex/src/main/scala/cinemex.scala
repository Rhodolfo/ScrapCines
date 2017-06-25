object Cinemex {

  import com.rho.file.quickFunc.{makeDirectory,writeToFile}
  import com.rho.scrap.CinemexHTTP.{getZones,getCinemas}
  import com.rho.scrap.CinemexClasses.{Zone,Cinema}

  val datdir = "data/"
  val prefix = "[Cinemex] "

  def main(args: Array[String]): Unit = {
    val zones = getZones
    val cinemas = zones.flatMap(getCinemas)
    saveData(cinemas)
  }

  def saveData(cinemas: List[Cinema]): Unit = {
    def concat(a: String, b: Cinema): String = if (a.isEmpty) b.toString else a+"\n"+b.toString
    val body = cinemas.head.header+"\n"+cinemas.foldLeft[String]("")(concat)
    makeDirectory(datdir)
    writeToFile(datdir+"cinemex",body,append=false,encoding="UTF-8")
  }

}
