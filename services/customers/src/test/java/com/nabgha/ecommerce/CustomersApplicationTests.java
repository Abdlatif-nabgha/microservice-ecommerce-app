package com.nabgha.ecommerce;

import com.nabgha.ecommerce.customers.Address;
import com.nabgha.ecommerce.customers.CustomerRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomersApplicationTests {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void contextLoads() {
    }

    @Test
    void shouldCreateAndFetchCustomerSuccessfully() {
        CustomerRequest request = new CustomerRequest(
                "John",
                "Doe",
                "john.doe@example.com",
                new Address("Main St", "123", "12345")
        );

        // 1. Create a customer
        String customerId = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/v1/customers")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("firstName", equalTo("John"))
                .body("lastName", equalTo("Doe"))
                .body("email", equalTo("john.doe@example.com"))
                .extract()
                .path("id");

        // 2. Fetch the created customer by ID
        given()
                .when()
                .get("/api/v1/customers/" + customerId)
                .then()
                .statusCode(200)
                .body("id", equalTo(customerId))
                .body("firstName", equalTo("John"))
                .body("email", equalTo("john.doe@example.com"))
                .body("address.street", equalTo("Main St"));
    }

    @Test
    void shouldUpdateCustomerSuccessfully() {
        CustomerRequest request = new CustomerRequest("Alice", "Smith", "alice@example.com", new Address("St", "1", "123"));
        String customerId = given().contentType(ContentType.JSON).body(request).post("/api/v1/customers").then().statusCode(201).extract().path("id");

        CustomerRequest updateRequest = new CustomerRequest("Alice", "Jones", "alice.jones@example.com", new Address("St", "1", "123"));
        given().contentType(ContentType.JSON).body(updateRequest)
                .put("/api/v1/customers/" + customerId)
                .then().statusCode(200)
                .body("lastName", equalTo("Jones"))
                .body("email", equalTo("alice.jones@example.com"));

        given().get("/api/v1/customers/" + customerId)
                .then().statusCode(200).body("lastName", equalTo("Jones"));
    }

    @Test
    void shouldFindAllCustomers() {
        CustomerRequest request = new CustomerRequest("Bob", "Builder", "bob@example.com", new Address("Build St", "2", "222"));
        given().contentType(ContentType.JSON).body(request).post("/api/v1/customers").then().statusCode(201);

        given().get("/api/v1/customers")
                .then().statusCode(200).body("size()", greaterThanOrEqualTo(1));
    }

    @Test
    void shouldReturnTrueWhenCustomerExists() {
        CustomerRequest request = new CustomerRequest("Charlie", "Chaplin", "charlie@example.com", new Address("Chap St", "3", "333"));
        String customerId = given().contentType(ContentType.JSON).body(request).post("/api/v1/customers").then().statusCode(201).extract().path("id");

        given().get("/api/v1/customers/exists/" + customerId)
                .then().statusCode(200).body(equalTo("true"));
    }

    @Test
    void shouldDeleteCustomerSuccessfully() {
        CustomerRequest request = new CustomerRequest("Dave", "Grohl", "dave@example.com", new Address("Rock St", "4", "444"));
        String customerId = given().contentType(ContentType.JSON).body(request).post("/api/v1/customers").then().statusCode(201).extract().path("id");

        given().delete("/api/v1/customers/" + customerId)
                .then().statusCode(202);

        given().get("/api/v1/customers/exists/" + customerId)
                .then().statusCode(200).body(equalTo("false"));
    }
}
