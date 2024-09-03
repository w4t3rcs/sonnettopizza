package org.sonnetto.user;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceApplicationTests {
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
    void testPostUserEndpoint() {
        final String requestBody = """
                {
                    "name": "j0hny",
                    "password": "akeg57gjmao",
                    "email": "j0hny@gmail.com",
                    "role": "DEFAULT"
                }
                """;

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/v1.0/users")
                .then()
                .statusCode(Matchers.oneOf(200, 201))
                .body("id", Matchers.notNullValue())
                .body("name", Matchers.is("j0hny"))
                .body("email",  Matchers.is("j0hny@gmail.com"))
                .body("role", Matchers.is("DEFAULT"))
                .log();
    }

    @Test
    @Order(2)
    void testGetUserEndpoint() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1.0/users/1")
                .then()
                .statusCode(Matchers.is(200))
                .body("name", Matchers.is("j0hny"))
                .body("email",  Matchers.is("j0hny@gmail.com"))
                .body("role", Matchers.is("DEFAULT"))
                .log();
    }

    @Test
    @Order(3)
    void testPutUserEndpoint() {
        final String requestBody = """
                {
                    "name": "g1cae",
                    "role": "ADMIN"
                }
                """;

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("/api/v1.0/users/1")
                .then()
                .statusCode(Matchers.is(200))
                .body("id", Matchers.notNullValue())
                .body("name", Matchers.is("g1cae"))
                .body("email",  Matchers.is("j0hny@gmail.com"))
                .body("role", Matchers.is("ADMIN"))
                .log();
    }

    @Test
    @Order(4)
    void testDeleteUserEndpoint() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/v1.0/users/1")
                .then()
                .statusCode(Matchers.is(200))
                .log();
    }
}