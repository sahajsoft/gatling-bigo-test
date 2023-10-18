package bigodatabase

import scala.concurrent.duration.{DurationInt, FiniteDuration}

object Args {
  println("Test setup params")
  val baseUrl: String = System.getProperty("baseUrl", "http://localhost:9000")
  println(s"Base URL: $baseUrl")

  val initialUsersPerSecCount: Int = Integer.getInteger("initialUsersPerSecCount", 10).toInt
  val loadChangeSecs: FiniteDuration = Integer.getInteger("loadChangeSecs", 10).toInt.seconds
  val journeyCountPerUser: Int = Integer.getInteger("journeyCountPerUser", 100).toInt
  println(s"User count: $initialUsersPerSecCount users over $loadChangeSecs with $journeyCountPerUser iterations per user")

  println()
}
