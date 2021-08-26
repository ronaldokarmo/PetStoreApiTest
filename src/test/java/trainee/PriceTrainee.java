package trainee;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.is;

public class PriceTrainee {

    @BeforeClass
    public static void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3001;
    }

    @Test
    public void GetLottoPrice() {
        expect()
                .log().all()
                .body("price", is(12.12f))
                .given()
                .log().all()
                .when()
                .get("/price");

    }
}
