package petstore;

import io.restassured.RestAssured;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import utils.Data;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.is;

public class StoreTest {
    private Data json;

    @Before
    public void SetUp() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
        json = new Data();
    }

    @Test
    public void petStorePostStore() throws IOException {
        String bodyJson = json.getJson("dataJson/store1.json");

        int storeId = given()
                .log().all()
                .contentType("application/json")
                .body(bodyJson)
                .when()
                .post("/store/order")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(10203040))
                .body("petId", is(1984020712))
                .body("quantity", is(5))
                .body("status", is("placed"))
                .extract()
                .jsonPath().getInt("id");

        System.out.println("storeId: ".concat(String.valueOf(storeId)));
    }

    @Test
    public void petStoreGetSoterByOrder() {
        given()
                .log().all()
                .contentType("application/json")
                .when()
                .get("/store/order/".concat(String.valueOf(10203040)))
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(10203040))
                .body("petId", is(1984020712))
                .body("quantity", is(5))
                .body("status", is("placed"))
                .body(matchesJsonSchemaInClasspath("./schema/petStoreGetStoreByOder.json"));
    }

    @Test
    public void petStoreGetSoterByOrderNotFound() {
        given()
                .log().all()
                .contentType("application/json")
                .when()
                .get("/store/order/".concat(String.valueOf(102030)))
                .then()
                .log().all()
                .statusCode(404)
                .body("code", CoreMatchers.is(1))
                .body("type", CoreMatchers.is("error"))
                .body("message", CoreMatchers.is("Order not found"))
                .body(matchesJsonSchemaInClasspath("schema/petStoreNotFound.json"));
    }

    @Test
    public void petStoreDeleteStore() {
        given()
                .log().all()
                .contentType("application/json")
                .when()
                .delete("/store/order/".concat(String.valueOf(10203040)))
                .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is(String.valueOf(10203040)));
    }
}