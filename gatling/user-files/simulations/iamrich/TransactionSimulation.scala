/*
 * Copyright 2011-2020 GatlingCorp (https://gatling.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package iamrich

import java.time.OffsetDateTime

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class TransactionSimulation extends Simulation {

  val ITERATIONS = 500
  val USERS = 200

  private val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("application/json;q=0.9,*/*;q=0.8")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  private val scn = scenario("Post transaction")
    .repeat(ITERATIONS) {
      exec(http("transaction")
        .post("/transaction")
        .header("Content-Type", "application/json")
        .body(StringBody(
          s"""
             |{
             |   "date": "${OffsetDateTime.now()}",
             |   "amount": 1.0001
             |}
             |""".stripMargin))
      )
    }

  setUp(scn.inject(rampUsers(USERS) during 10.seconds).protocols(httpProtocol))
}
