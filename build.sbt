organization  := "com.example"

version       := "0.1"

scalaVersion  := "2.11.6"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")


libraryDependencies ++= {
  val akkaV = "2.3.9"
  val sprayV = "1.3.3"
  val AkkaHttpVersion = "10.1.13"
  Seq(
    "io.spray"            %%  "spray-can"     % sprayV,
    "io.spray"            %%  "spray-routing" % sprayV,
    "io.spray"            %%  "spray-testkit" % sprayV  % "test",
    "com.typesafe.akka"   %%  "akka-actor"    % akkaV,
    "com.typesafe.akka"   %%  "akka-testkit"  % akkaV   % "test",
    "org.specs2"          %%  "specs2-core"   % "2.3.11" % "test",
    "org.json4s" %% "json4s-native" % "3.7.0-M7",
    "com.typesafe.slick" %% "slick" % "3.3.1",
    "org.reactivemongo" %% "reactivemongo" % "1.0.1" % "provided",
    "org.scalatest" %% "scalatest" % "3.0.8" % Test,
    "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
    "org.mockito" % "mockito-core" % "2.8.47" % "test",
    "net.codingwell" %% "scala-guice" % "4.2.11"
    //"org.scalatest" %% "scalatest" % "3.3.0-SNAP3" % Test
    //"net.liftweb" %% "lift-json" % "3.4.3"
  )

}

Revolver.settings
