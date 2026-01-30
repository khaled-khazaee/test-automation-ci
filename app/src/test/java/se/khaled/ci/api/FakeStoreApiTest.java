package se.khaled.ci.api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class FakeStoreApiTest {

  @Test
  void getProductsReturns200() {
    RestAssured.baseURI = "https://fakestoreapi.com";

    given()
      .when()
        .get("/products")
      .then()
        .statusCode(200);
  }
}
