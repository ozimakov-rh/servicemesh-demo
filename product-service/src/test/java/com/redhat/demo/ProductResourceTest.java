package com.redhat.demo;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ProductResourceTest {

    @Test
    public void testProductDetails() {
        given()
          .when().get("/product/1")
          .then()
             .statusCode(200)
                .body("id", is(1));
    }

}