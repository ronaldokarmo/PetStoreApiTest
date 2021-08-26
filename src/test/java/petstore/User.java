package petstore;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

public class User {

    private String userId;

    @BeforeClass
    public static void init() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    public String getJson(String json) throws IOException {
        return new String(Files.readAllBytes(Paths.get(json)));
    }

    @Test(priority = 9)
    public void incluirUser() throws IOException {
        String bodyJson = getJson("db/user1.json");

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
                .body("type", is ("unknown"))
                .body("message", is("102030"))
        .extract()
                .path("message");

        System.out.println("usertId: ".concat(userId));
    }

    @Test(priority = 10)
    public void loginUser() {
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
                .body("type", is ("unknown"))
                .body("message", containsString("logged in user session:"));
    }

    @Test(priority = 11)
    public void consultarUser() {
        given()
                .log().all()
                .contentType("application/json")
        .when()
                .get("/user/".concat(userId))
        .then()
                .log().all()
                .statusCode(404)
                .body("code", is(1))
                .body("type", is ("error"))
                .body("message", is("User not found"));
                //.body("id", is(userId))
                //.body("username", is("rcarmo"))
                //.body("firstName", is("Ronaldo"))
               // .body("email", is("ronaldokarmo@gmail.com"));
    }

//    @Test(priority =12)
//    public void  excluirUser() {
//        given()
//                .log().all()
//                .contentType("application/json")
//        .when()
//                .delete("/user/".concat(userId))
//        .then()
//                .log().all()
//                .statusCode(200)
//                .body("code", is(200))
//                .body("type", is ("unknown"))
//                .body("message", is(userId));
//    }
}