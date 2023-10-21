package bigodatabase

import bigodatabase.Args.{baseUrls, initialUsersPerSecCount, journeyCountPerUser, loadChangeSecs}
import bigodatabase.Feeder.{feeder, requests}
import io.gatling.core.Predef._
import io.gatling.http.HeaderNames.{Accept, ContentType}
import io.gatling.http.HeaderValues.ApplicationJson
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class BigODatabaseSimulation extends Simulation {

  private val protocol = http.baseUrls(baseUrls)
    .header(ContentType, ApplicationJson)
    .header(Accept, ApplicationJson)

  private val rampedUpUserLoad = List(
    nothingFor(2.seconds),
    atOnceUsers(initialUsersPerSecCount),
    constantUsersPerSec(initialUsersPerSecCount).during(loadChangeSecs)
  )

  setUp(
    scenario("Load Testing")
      .feed(feeder)
      .exec(requests)
      .inject(rampedUpUserLoad)
      .protocols(protocol)
  ).assertions(
    global.successfulRequests.percent.is(100)
  )
}
