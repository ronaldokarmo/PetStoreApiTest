package trainee;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.path.xml.XmlPath.from;
import static org.hamcrest.Matchers.hasItems;

public class StoreTraineeTest {

    @Before
    public void SetUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port=3001;
    }

    @Test
    public void consultarLivrosPorPreco()  {

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
    public void retornarTituloDosLivrosComValorInferioraDezReais()  {

        String response = given()
                .log().all()
                .contentType("application/json")
                .when().get("/store").asString();
        List<String> bookTitles = from(response).getList("store.book.findAll { it.price < 10 }.title");

        System.out.println("Livros: ".concat(String.valueOf(bookTitles)));
    }
}