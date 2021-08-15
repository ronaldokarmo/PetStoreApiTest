package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class Pet {
    String uri = "https://petstore.swagger.io/v2/pet";

    public String getJson(String json) throws IOException {
        return new String(Files.readAllBytes(Paths.get(json)));
    }

    @Test
    public void incluirPet() throws IOException {
        String bodyJson = getJson("db/pet1.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(bodyJson)
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(200);
    }
}