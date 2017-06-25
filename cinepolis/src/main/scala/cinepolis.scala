object Cinepolis {

  import com.rho.file.quickFunc.{makeDirectory,writeToFile}
  import com.rho.scrap.CinepolisHTTP.{getZones,getCinemas,getComplex}
  import com.rho.scrap.CinepolisClasses.{Cinema,Complex}

  val datdir = "data/"
  val prefix = "[Cinepolis] "

  def main(args: Array[String]): Unit = {
    val zones: List[String]    = getZones
    val cinemas: List[Cinema]  = zones.flatMap(getCinemas)
    println(prefix+"Number of Cinemas: "+cinemas.size)
    val complex: List[Complex] = cinemas.map(getComplex)
    saveData(complex)
  }

  def saveData(list: List[Complex]): Unit = {
    makeDirectory(datdir)
    def concat(a: String, b: Complex): String = if (a.isEmpty) b.toString else a+"\n"+b.toString
    val body = list.head.header+"\n"+list.foldLeft[String]("")(concat)
    writeToFile(file=datdir+"cinepolis",string=body,append=false,encoding="UTF-8")
  }

}
