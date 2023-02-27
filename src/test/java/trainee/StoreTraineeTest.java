package trainee;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;

public class StoreTraineeTest {

    @Before
    public void SetUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3000;
    }

    @Test
    public void consultarLivrosPorPreco() {

        given()
                .log().all()
                .contentType("application/json")
                .when()
                .get("/store")
                .then()
                .log().all()
                .statusCode(200)
                .body("store.book.findAll { it.price < 12.99 }.title", hasItems("Sayings of the Century", "Moby Dick", "Sword of Honour"));
    }

    @Test
    public void retornarTituloDosLivrosComValorInferioraDezReais() {

        given()
                .log().all()
                .contentType("application/json")
                .when().get("/store")
                .then().log().all()
                .statusCode(200)
                .body("store.book.findAll { it.price < 10 }.title", hasItems("Sayings of the Century", "Moby Dick"));

    }
}