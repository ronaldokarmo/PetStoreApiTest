package petstore;

import io.restassured.RestAssured;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import utils.Data;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

public class UserTest {

    private String userId;
    private Data json;

    @Before
    public void SetUp() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
        json = new Data();
    }

    @Test
    public void petStorePostUser() throws IOException {
        String bodyJson = json.getJson("dataJson/user1.json");

        userId = given()
                .log().all()
                .contentType("application/json")
                .body(bodyJson)
                .when()
                .post("/user")
                .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is("102030"))
                .extract()
                .path("message");

        System.out.println("usertId: ".concat(userId));
    }

    @Test
    public void petStoreGetUserByUserName() {
        given()
                .log().all()
                .contentType("application/json")
                .when()
                .pathParams("username", "rcarmo")
                .get("/user/{username}")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(102030))
                .body("username", is("rcarmo"))
                .body(matchesJsonSchemaInClasspath("schema/petStoreGetUserByName.json"));
    }

    @Test
    public void petStoreGetUserByUserNameNotFound() {
        given()
                .log().all()
                .contentType("application/json")
                .when()
                .pathParams("username", "xxxxx")
                .get("/user/{username}")
                .then()
                .log().all()
                .statusCode(404)
                .body("code", CoreMatchers.is(1))
                .body("type", CoreMatchers.is("error"))
                .body("message", CoreMatchers.is("User not found"))
                .body(matchesJsonSchemaInClasspath("schema/petStoreNotFound.json"));
    }

    @Test
    public void petStoreGetUserLogin() {
        String username = "rcarmo";
        String pwd = "pwd1234";

        given()
                .log().all()
                .contentType("application/json")
                .when()
                .get("/user/login?username=".concat(username).concat("&password=").concat(pwd))
                .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", containsString("logged in user session:"))
                .body(matchesJsonSchemaInClasspath("schema/petStoreNotFound.json"));
    }

    @Test
    public void petStoreGetUserLogout() {
        given()
                .log().all()
                .contentType("application/json")
                .when()
                .get("/user/logout")
                .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is("ok"))
                .body(matchesJsonSchemaInClasspath("schema/petStoreNotFound.json"));
    }

    @Test
    public void petStoreDeleteUser() {
        given()
                .log().all()
                .contentType("application/json")
                .when()
                .pathParams("username", "rcarmo")
                .delete("/user/{username}")
                .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is("rcarmo"))
                .body(matchesJsonSchemaInClasspath("schema/petStoreNotFound.json"));
    }
}