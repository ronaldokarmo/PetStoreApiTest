package trainee;


import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LottoTrainee {

    @BeforeClass
    public static void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3001;
    }

    @Test
    public void GetLottoId() {
        given()
                .log().all()
                .contentType("application/json")
        .when()
                .get("/lotto")
        .then()
                .log().all()
                .body("lotto.lottoId", equalTo(5));
    }

    @Test
    public void GetLottoIdExpect() {
        expect()
                .log().all()
                .body("lotto.lottoId", equalTo(5))
        .given()
                .log().all()
        .when()
                .get("/lotto");
    }

    @Test
    public void GetLottoWinnersId() {
        expect()
                .log().all()
                .body("lotto.winners.winnerId", hasItems(23, 54))
        .given()
                .log().all()
        .when()
                .get("/lotto");
    }
}