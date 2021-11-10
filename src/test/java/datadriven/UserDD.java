package datadriven;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class UserDD {

    private String userId;
    private Data csv;

    @DataProvider
    public Iterator<Object[]> provider() throws IOException {
        List<String[]> testCases = new ArrayList<>();
        String[] testCase = null;
        testCases = csv.getCsv("db/users.csv");
        return null;
    }

    @BeforeMethod
    public void SetUp() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
        csv = new Data();
    }

    @Test(priority = 9)
    public void incluirUser() throws IOException {

        userId = given()
                .log().all()
                .contentType("application/json")
                .body("")
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
}