package petstore;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Data;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class Store {

    private int storeId;
    private Data json;

    @BeforeMethod
    public void SetUp() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
        json = new Data();
    }

    @Test(priority = 6)
    public void incluirStore() throws IOException {
        String bodyJson = json.getJson("db/store1.json");

        storeId = given()
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

    @Test(priority = 7)
    public void consultarStore() {
        given()
                .log().all()
                .contentType("application/json")
        .when()
                .get("/store/order/".concat(String.valueOf(storeId)))
        .then()
                .log().all()
                .statusCode(200)
                .body("id", is(storeId))
                .body("petId", is(1984020712))
                .body("quantity", is(5))
                .body("status", is("placed"));
    }

    @Test(priority = 8)
    public void  excluirStore() {
        given()
                .log().all()
                .contentType("application/json")
        .when()
                .delete("/store/order/".concat(String.valueOf(storeId)))
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is ("unknown"))
                .body("message", is(String.valueOf(storeId)));
    }
}