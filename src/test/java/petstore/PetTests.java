package petstore;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import utils.Data;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;

public class PetTests {
    private Data json;

    @Before
    public void SetUp() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
        json = new Data();
    }

    @Test
    public void petStorePostPet() throws IOException {
        String bodyJson = json.getJson("dataJson/pet1.json");

        int petId = given()
                .log().all()
                .contentType("application/json")
                .body(bodyJson)
                .when()
                .post("/pet")
                .then()
                .log().all()
                .statusCode(200)
                .body("category.name", is("Dogs"))
                .body("name", is("Nainai"))
                .body("tags.name", contains("breed"))
                .body("status", is("available"))
                .extract()
                .jsonPath().getInt("id");

        System.out.println("petId: ".concat(String.valueOf(petId)));
    }

    @Test
    public void petStorePutPet() throws IOException {
        String bodyJson = json.getJson("dataJson/pet2.json");

        given()
                .log().all()
                .contentType("application/json")
                .body(bodyJson)
                .when()
                .put("/pet")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Jazz"))
                .body("status", is("sold"));
    }

    @Test
    public void petStoreDeletePet() {
        given()
                .log().all()
                .contentType("application/json")
                .when()
                .delete("/pet/".concat(String.valueOf(1984020712)))
                .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is(String.valueOf(1984020712)));
    }

    @Test
    public void petStoreGetPetById() {
        given()
                .log().all()
                .when()
                .pathParams("petId", 1984020712)
                .get("/pet/{petId}")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(1984020712))
                .body("name", is("Jazz"))
                .body("status", is("sold"))
                .body(matchesJsonSchemaInClasspath("./schema/petStoreGetPetById.json"));
    }

    @Test
    public void petStoreGetPetByStatus() {
        String status = "available";

        given()
                .log().all()
                .contentType("application/json")
                .when()
                .get("/pet/findByStatus?status=".concat(status))
                .then()
                .log().all()
                .statusCode(200)
                .body("name[]", everyItem(equalTo("Jazz")))
                .body(matchesJsonSchemaInClasspath("./schema/petStoreGetPetByStatus.json"));
    }

    @Test
    public void petStoreGetPetByIdNotFound() {
        given()
                .log().all()
                .contentType("application/json")
                .when()
                .pathParam("petId", 0)
                .when()
                .get("/pet/{petId}")
                .then()
                .log().all()
                .statusCode(404)
                .body("code", is(1))
                .body("type", is("error"))
                .body("message", is("Pet not found"))
                .body(matchesJsonSchemaInClasspath("schema/petStoreNotFound.json"));
    }
}