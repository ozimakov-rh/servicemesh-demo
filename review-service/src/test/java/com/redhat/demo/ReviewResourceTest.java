package com.redhat.demo;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ReviewResourceTest {

    @Test
    public void testReviewEndpoint() {
        given()
                .when().get("/review")
                .then()
                .statusCode(200);
    }

}