package org.sonnetto.order;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@AutoConfigureWireMock(port = 0)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceApplicationTests {
    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:9")
            .withEnv("MYSQL_LOG_CONSOLE", "true")
            .withEnv("INNODB_REDO_LOG_CAPACITY", "20971520");
    @Container
    @ServiceConnection
    static KafkaContainer kafkaContainer = new KafkaContainer();
    @Container
    @ServiceConnection
    static GenericContainer<?> redisContainer = new GenericContainer<>("redis:7.2.5")
            .withExposedPorts(6379);
    @LocalServerPort
    private Integer port;

    @BeforeEach
    void prepare() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    @Order(1)
    void testPostOrderEndpoint() {
        final String requestBody = """
                {
                    "address": {
                        "country": "Poland",
                        "city": "Warsaw",
                        "street": "Nowoczesna",
                        "houseNumber": "3",
                        "postalCode": "70-777"
                    },
                    "status": "NOT_PAID",
                    "purchaseRequest": {
                        "productNames": ["Margherita", "Coca Cola"],
                        "currency": "USD"
                    },
                    "userId": 1
                }
                """;

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/v1.0/orders")
                .then()
                .statusCode(Matchers.oneOf(200, 201))
                .body("id", Matchers.notNullValue())
                .body("address", Matchers.notNullValue())
                .body("status", Matchers.is("NOT_PAID"))
                .body("purchaseResponse",  Matchers.notNullValue())
                .body("userId", Matchers.is("1"));
    }
}
