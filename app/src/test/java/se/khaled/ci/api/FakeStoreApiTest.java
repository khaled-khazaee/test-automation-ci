package se.khaled.ci.api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@DisabledIfEnvironmentVariable(named = "CI", matches = "true")
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

  @Test
  void getProducts_shouldReturnExpectedNumberOfProducts() {
    RestAssured.baseURI = "https://fakestoreapi.com";

    given()
      .when()
        .get("/products")
      .then()
        .statusCode(200)
        .body("$", hasSize(greaterThanOrEqualTo(1)));
  }

  @Test
  void getSingleProduct_shouldContainExpectedFields() {
    RestAssured.baseURI = "https://fakestoreapi.com";

    given()
      .when()
        .get("/products/1")
      .then()
        .statusCode(200)
        .body("id", equalTo(1))
        .body("title", not(isEmptyOrNullString()))
        .body("price", greaterThan(0f))
        .body("category", not(isEmptyOrNullString()));
  }
}
