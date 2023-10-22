package bigodatabase

import scala.concurrent.duration.{DurationInt, FiniteDuration}

object Args {
  println("Test setup params")
  val baseUrls: List[String] = System.getProperty("baseUrls", "http://localhost:9000,http://localhost:9001").split(",").toList
  println(s"Base URL: $baseUrls")

  val initialUsersPerSecCount: Int = Integer.getInteger("initialUsersPerSecCount", 100).toInt
  val loadChangeSecs: FiniteDuration = Integer.getInteger("loadChangeSecs", 10).toInt.seconds
  val feedCount: Int = Integer.getInteger("feedCount", 100).toInt
  println(s"Feed count: $feedCount")
  println(s"User count: $initialUsersPerSecCount users over $loadChangeSecs.")

  println()
}
