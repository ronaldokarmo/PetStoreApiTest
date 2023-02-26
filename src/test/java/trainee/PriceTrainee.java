package trainee;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

import static io.restassured.RestAssured.expect;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.config.RestAssuredConfig.newConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class PriceTrainee {

    @Before
    public static void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3001;
    }

    @Test
    public void GetLottoForPriceOneForm() {
        expect()
        .given()
                .log().all()
        .when()
                .get("/price")
        .then()
                .log().all()
                .body("price", is(12.12f));
    }

    @Test
    public void GetLottoForPriceTwoForm() {
        expect().given()
                .log().all()
                .config(newConfig().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
        .when()
                .get("/price")
        .then()
                .log().all()
                .body("price", is(new BigDecimal("12.12")));
    }

    @Test
    public void GetLottoForPriceTreeForm() {
        expect()
                .given()
                .log().all()
        .when()
                .get("/price")
        .then()
                .log().all()
                .body("price", equalTo(12.12));
    }
}