// External Github dependencies
val ClientVer = "v1.1"
val FUtilsVer = "v1.2"
lazy val ClientLoc = "https://github.com/Rhodolfo/RhoClient.git#%s".format(ClientVer)
lazy val FUtilsLoc = "https://github.com/Rhodolfo/RhoFileUtils.git#%s".format(FUtilsVer)
lazy val rhoClient = RootProject(uri(ClientLoc))
lazy val rhoFUtils = ProjectRef(uri(FUtilsLoc),"filefuncs")
lazy val rhoFCheck = ProjectRef(uri(FUtilsLoc),"checkpoints")

// Common settings
lazy val commonSettings = Seq(
  libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1",
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  organization := "com.rho",
  version := "1.0.0",
  scalacOptions := Seq("-feature"),
  scalaVersion := "2.12.1")

// JSON parsing library
lazy val json = Seq(libraryDependencies += "net.liftweb" %% "lift-json" % "3.0.1")

lazy val cinepolis = (project in file("cinepolis"))
  .settings(commonSettings: _*)
  .settings(json: _*)
  .settings(name := "Cinepolis")
  .dependsOn(rhoClient)
  .dependsOn(rhoFUtils)
  .dependsOn(rhoFCheck)

lazy val cinemex = (project in file("cinemex"))
  .settings(commonSettings: _*)
  .settings(name := "Cinemex")
  .dependsOn(rhoClient)
  .dependsOn(rhoFUtils)
  .dependsOn(rhoFCheck)
