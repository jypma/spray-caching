organization := "com.tradeshift"

name := "spray-caching"

version := "0.1-201603291310"

licenses += ("MIT", url("http://opensource.org/licenses/Apache-2.0"))

scalaVersion := "2.11.7"

scalacOptions ++= "-deprecation" :: "-feature" :: "-target:jvm-1.8" :: Nil

bintrayVcsUrl := Some("git@github.com:jypma/spray-caching")

resolvers += Resolver.bintrayRepo("jypma", "maven")

libraryDependencies += "org.scala-lang" % "scala-library" % scalaVersion.value

libraryDependencies ++= {
  val akkaVersion = "2.4.2"

  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test"
  )
}

libraryDependencies += "com.googlecode.concurrentlinkedhashmap" % "concurrentlinkedhashmap-lru" % "1.4.2"

libraryDependencies ++= Seq("org.specs2" %% "specs2-core" % "3.7.2" % "test")

libraryDependencies += "org.scala-lang.modules" %% "scala-java8-compat" % "0.7.0"

scalacOptions in Test ++= Seq("-Yrangepos")
