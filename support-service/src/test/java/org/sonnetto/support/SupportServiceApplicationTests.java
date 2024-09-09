package org.sonnetto.support;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.sonnetto.support.stub.UserClientStub;
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
class SupportServiceApplicationTests {
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
    @Container
    @ServiceConnection
    static GenericContainer<?> ollamaContainer = new GenericContainer<>("ollama/ollama:0.3.6")
            .withExposedPorts(11434);
    @LocalServerPort
    private Integer port;

    @BeforeEach
    void prepare() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    @Order(1)
    void testPostSupportSessionEndpoint() {
        final String requestBody = """
                {
                    "senderId": 1,
                    "content": "What is SonnettoPizza?"
                }
                """;
        UserClientStub.stub(1L);
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/v1.0/support")
                .then()
                .statusCode(Matchers.oneOf(200, 201))
                .body("id", Matchers.notNullValue())
                .body("result", Matchers.notNullValue());
    }
}
