package resources.load_tests.gatlig
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._



class DepositEndpointLoadTest extends Simulation {

  // Конфигурация HTTP
  val httpProtocol = http
    .baseUrl("http://localhost:8085/api/v1") // Укажите базовый URL вашего API
    .header("Content-Type", "application/json") // Заголовок Content-Type JSON

  // Данные, которые будут отправляться в запросе
  val jsonPayload =
    """
      |{
      |  "walletId": "43fe0cf8-c985-4db7-b578-92f5c338d200",
      |  "operationType": "DEPOSIT",
      |  "amount": "1"
      |}
    """.stripMargin

  // Определение сценария нагрузки
  val scn = scenario("Deposit Endpoint Load Test")
    .exec(
      http("Deposit Request") // Уникальное имя запроса
        .post("/wallet") // Путь к вашему методу POST
        .body(StringBody(jsonPayload)) // Тело запроса
        .check(status.is(200)) // Проверка, что ответ статус 200
    )

  // Настройки нагрузки
  setUp(
    scn.inject(
      atOnceUsers(100), // Запустить 100 пользователей одновременно
      rampUsers(500) during (10.seconds) // Увеличить до 500 пользователей за 10 секунд
    )
  ).protocols(httpProtocol)
}