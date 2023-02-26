package trainee;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

public class LottoTraineeTest {
    @Before
    public void SetUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3000;
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
        JsonPath jsonPath = new JsonPath(get("/lotto").asString()).setRootPath("lotto");
        System.out.println(jsonPath.prettyPrint());
        int lottoId = jsonPath.getInt("lottoId");
        List<Object> wnumbers = jsonPath.getList("winning-numbers");

        assertEquals(5, lottoId);
        assertEquals(2, wnumbers.get(0));
        assertEquals(7, wnumbers.size());
    }
}