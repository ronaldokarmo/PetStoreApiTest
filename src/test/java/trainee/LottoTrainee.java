package trainee;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class LottoTrainee {

    @BeforeClass
    public static void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3001;
    }

    @Test
    public void GetLottoForIdOneForm() {
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
    public void GetLottoForIdTwoForm() {
        expect().given()
                .log().all()
        .when()
                .get("/lotto")
        .then()
                .log().all()
                .body("lotto.lottoId", is(5));
    }

    @Test
    public void GetLottoWinnersForId() {
        expect().given()
                .log().all()
        .when()
                .get("/lotto")
        .then()
                .log().all()
                .body("lotto.winners.winnerId", hasItems(23, 54));
    }

    @Test
    public void GetResponseBody() {
        InputStream stream = get("/lotto").asInputStream();
        byte[] byteArray = get("/lotto").asByteArray();
        String json = get("/lotto").asString();

        JsonPath jsonPath = new JsonPath(json).setRoot("lotto");
        int lottoId = jsonPath.getInt("lottoId");
        List<Integer> winnerIds = jsonPath.get("winners.winnderId");


        System.out.println("stream: ".concat(String.valueOf(stream)));
        System.out.println("byte: ".concat(String.valueOf(byteArray)));
        //System.out.println("String: ".concat(json));
        System.out.println(lottoId);
        System.out.println(winnerIds);
    }
}