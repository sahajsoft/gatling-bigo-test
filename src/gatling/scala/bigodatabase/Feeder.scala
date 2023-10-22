package bigodatabase

import bigodatabase.Args.{baseUrls, feedCount}
import io.gatling.core.Predef.exec
import io.gatling.core.feeder.FeederBuilderBase
import io.gatling.core.structure.ChainBuilder
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import java.util.UUID.randomUUID
import scala.util.Random


object Feeder {

  val feeder: FeederBuilderBase[String] = (1 to feedCount ).map(feedInput).circular

  private def getUrl(): String = {
    val index = Random.between(0, baseUrls.size)
    baseUrls(index)
  }

  private val putProbeData: ChainBuilder = exec(
    http("Put Probe Data")
      .put(session => getUrl() + s"/probe/" + session("newProbeId").as[String])
      .body(StringBody(requestBody("${newEventId}", "${newData}")))
      .check(status.is(200))
      .check(jsonPath("$.probeId").is("${newProbeId}"))
      .check(jsonPath("$.eventReceivedTime").saveAs("savedEventReceivedTime"))
      .check(jsonPath("$.eventId").is("${newEventId}"))
      .check(jsonPath("$.data").is("${newData}"))
  )

  private val getProbeData: ChainBuilder = exec(
    http("Get Probe Data")
      .get(session => getUrl() + s"/probe/" + session("newProbeId").as[String])
      .check(status.is(200))
      .check(jsonPath("$.probeId").is("${newProbeId}"))
//      .check(jsonPath("$.eventReceivedTime").is("${savedEventReceivedTime}"))
      .check(jsonPath("$.eventId").is("${newEventId}"))
      .check(jsonPath("$.data").is("${newData}"))
  )

  val requests: Seq[ChainBuilder] = List(putProbeData, getProbeData)

  private def requestBody(eventId: String, data: String): String = compact(render(("eventId" -> eventId) ~ ("data" -> data)))

  private def generateProbeId(): String = {
    "PRB" + Random.between(1000000000L, 10000000000L).toString
  }

  private def generateData(n: Int): String = {
    Iterator.continually(Random.nextPrintableChar()).filter(_.isLetterOrDigit).take(n).mkString
  }

  private def feedInput(iterationCount: Int) = {
    println(s"Creating feed input $iterationCount")
    Map(
      "newProbeId" -> generateProbeId(),
      "newEventId" -> randomUUID().toString,
      "newData" -> generateData(Random.between(100, 1000))
    )
  }

}
