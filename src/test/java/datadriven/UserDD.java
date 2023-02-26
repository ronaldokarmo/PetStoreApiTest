package datadriven;

import com.opencsv.exceptions.CsvException;
import com.tngtech.java.junit.dataprovider.DataProvider;
import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;
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
    public Iterator<Object[]> provider() throws IOException, CsvException {
        List<String[]> testCases = new ArrayList<>();
        String[] testCase = null;
        testCases = csv.getCsv("dataJson/users.csv");
        return null;
    }

    @BeforeClass
    public void SetUp() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
        csv = new Data();
    }

    @Test
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