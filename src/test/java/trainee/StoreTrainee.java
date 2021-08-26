package trainee;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.path.xml.XmlPath.from;
import static org.hamcrest.Matchers.hasItems;

public class StoreTrainee {

    @BeforeClass
    public static void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port=3001;
    }

    @Test(priority = 1)
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

    @Test(priority = 2)
    public void retornarTituloDosLivrosComValorInferioraDezReais()  {

        String response = given()
                .log().all()
                .contentType("application/json")
                .when().get("/store").asString();
        List<String> bookTitles = from(response).getList("store.book.findAll { it.price < 10 }.title");

        System.out.println("Livros: ".concat(String.valueOf(bookTitles)));
    }
}
