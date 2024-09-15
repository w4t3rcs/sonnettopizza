package org.sonnetto.product;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.sonnetto.product.stub.IngredientClientStub;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@AutoConfigureWireMock(port = 0)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {
    @Container
    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.0.10");
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
        IngredientClientStub.stub(1L);
    }

    @Test
    @Order(1)
    void testPostProductEndpoint() {
        final String requestBody = """
                {
                    "name": "Product",
                    "type": "OTHER",
                    "price": {
                        "value": 1.00,
                        "currency": "USD"
                    },
                    "ingredientIds": [1]
                }
                """;

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/v1.0/products")
                .then()
                .statusCode(Matchers.oneOf(200, 201))
                .body("name", Matchers.is("Product"))
                .body("type", Matchers.is("OTHER"))
                .body("price",  Matchers.notNullValue())
                .body("ingredientIds", Matchers.arrayContaining("1"));
    }

    @Test
    @Order(2)
    void testGetProductEndpoint() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1.0/products/1")
                .then()
                .statusCode(Matchers.is(200))
                .body("name", Matchers.is("Product"))
                .body("type", Matchers.is("OTHER"))
                .body("price",  Matchers.notNullValue())
                .body("ingredientIds", Matchers.arrayContaining("1"));
    }

    @Test
    @Order(3)
    void testGetConvertedProductEndpoint() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1.0/products/1?currency=PLN")
                .then()
                .statusCode(Matchers.is(200))
                .body("name", Matchers.is("Product"))
                .body("type", Matchers.is("OTHER"))
                .body("price",  Matchers.notNullValue())
                .body("ingredientIds", Matchers.arrayContaining("1"));
    }

    @Test
    @Order(4)
    void testPutProductEndpoint() {
        final String requestBody = """
                {
                    "name": "Pizza",
                    "type": "PIZZA",
                }
                """;

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("/api/v1.0/products")
                .then()
                .statusCode(Matchers.is(200))
                .body("name", Matchers.is("Pizza"))
                .body("type", Matchers.is("PIZZA"))
                .body("price",  Matchers.notNullValue())
                .body("ingredientIds", Matchers.arrayContaining("1"));
    }

    @Test
    @Order(5)
    void testDeleteProductEndpoint() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/v1.0/products/1")
                .then()
                .statusCode(Matchers.is(200));
    }
}
