package bigodatabase

import scala.concurrent.duration.{DurationInt, FiniteDuration}

object Args {
  println("Test setup params")
  val baseUrls: List[String] = System.getProperty("baseUrls", "http://localhost:9000,http://localhost:9001").split(",").toList
  println(s"Base URL: $baseUrls")

  val initialUsersPerSecCount: Int = Integer.getInteger("initialUsersPerSecCount", 10000).toInt
  val loadChangeSecs: FiniteDuration = Integer.getInteger("loadChangeSecs", 60).toInt.seconds
  val journeyCountPerUser: Int = Integer.getInteger("journeyCountPerUser", 100).toInt
  println(s"User count: $initialUsersPerSecCount users over $loadChangeSecs with $journeyCountPerUser iterations per user")

  println()
}
