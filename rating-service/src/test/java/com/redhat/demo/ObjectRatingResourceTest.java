package com.redhat.demo;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ObjectRatingResourceTest {

    @Test
    public void testObjectRatingEndpoint() {
        given()
          .when().get("/rating")
          .then()
             .statusCode(200);
    }

}