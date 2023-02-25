package petstore;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Data;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

public class UserTest {

    private String userId;
    private Data json;

    @BeforeMethod
    public void SetUp() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
        json = new Data();
    }

    @Test
    public void petStore1PostUser() throws IOException {
        String bodyJson = json.getJson("db/user1.json");

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
    public void petStore2GetUserByUserName() {
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
                .body("message", containsString("logged in user session:"));
    }

    @Test
    public void petStore3GetUserById() {
        given()
                .log().all()
                .contentType("application/json")
                .when()
                .get("/user/".concat(userId))
                .then()
                .log().all()
                .statusCode(404)
                .body("code", is(1))
                .body("type", is("error"))
                .body("message", is("User not found"));
//                .body("id", is(userId))
//                .body("username", is("rcarmo"))
//                .body("firstName", is("Ronaldo"))
//                .body("email", is("ronaldokarmo@gmail.com"));
    }

    @Test
    public void petStore4DeleteUser() {
        given()
                .log().all()
                .contentType("application/json")
                .when()
                .delete("/user/".concat(userId))
                .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is(userId));
    }

    @Test
    public void petStore4DeleteUserNotFound() {
        given()
                .log().all()
                .contentType("application/json")
                .when()
                .delete("/user/".concat(userId))
                .then()
                .log().all()
                .statusCode(404);
    }
}