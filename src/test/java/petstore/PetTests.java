package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class PetTests {
    String uri = "https://petstore.swagger.io/v2";

    public String getJson(String json) throws IOException {
        return new String(Files.readAllBytes(Paths.get(json)));
    }

    @Test(priority = 1)
    public void incluirPet() throws IOException {
        String bodyJson = getJson("db/pet1.json");

        given()
                .contentType("application/json")
                .body(bodyJson)
        .when()
                .post(uri.concat("/pet"))
        .then()
                .log().all()
                .statusCode(200)
                .body("category.name", is("Dogs"))
                .body("name", is("Nainai"))
                .body("tags.name", contains("adopted"))
                .body("status", is("available"));
    }

    @Test(priority = 2)
    public void consultarPet() {
        String petId = "1984020712";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri.concat("/pet/").concat(petId))
        .then()
                .log().all()
                .statusCode(200)
                .body("category.name", is("Dogs"))
                .body("name", is("Nainai"))
                .body("status", is("available"));;
    }
}