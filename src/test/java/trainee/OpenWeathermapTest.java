package trainee;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OpenWeathermapTest {

    @Test
    public void queryParameter() {

        RestAssured.baseURI ="https://samples.openweathermap.org/data/2.5/";
        RequestSpecification request = RestAssured.given().log().all();

        Response response = request.queryParam("q", "London,UK")
                .queryParam("appid", "2b1fd2d7f77ccf1b7de9b441571b39b8")
                .get("/weather");

        String jsonString = response.asString();
        System.out.println(response.getStatusCode());
        assertTrue(jsonString.contains("London"));
    }

    @Test
    public void RegistrationSuccessful() throws JSONException {
        //RestAssured.baseURI ="http://demoqa.com/customerr";
        RestAssured.baseURI ="http://localhost:3000/customer";
        RequestSpecification request = RestAssured.given().log().all().contentType("application/json");

        JSONObject requestParams = new JSONObject();
        requestParams.put("FirstName", "Test");
        requestParams.put("LastName", "Singh");
        requestParams.put("UserName", "sdimpleuser2dd2011");
        requestParams.put("Password", "password1");
        requestParams.put("Email",  "sample2ee26d9@gmail.com");

        request.body(requestParams.toString());
        Response response = request.post("/register");

        int statusCode = response.getStatusCode();
        assertEquals(statusCode, 201);
        String successCode = response.jsonPath().get("SuccessCode");
        assertEquals("OPERATION_SUCCESS", successCode);
        assertEquals("Operation completed successfully", response.jsonPath().get("Message"));

        Headers allHeaders = response.headers();
        for(Header header : allHeaders)
        {
            System.out.println("Key: " + header.getName() + " Value: " + header.getValue());
        }

        System.out.println("Response body: " + response.body().asString());
    }

    @Test
    public void RegistrationSuccessfulTwo() throws JSONException {
        RestAssured.baseURI ="http://demoqa.com/customer";
        RequestSpecification request = RestAssured.given().log().all().contentType("application/json");

        JSONObject requestParams = new JSONObject();
        requestParams.put("FirstName", "Virender"); // Cast
        requestParams.put("LastName", "Singh");
        requestParams.put("UserName", "sdimpleuser2dd2011");
        requestParams.put("Password", "password1");
        requestParams.put("Email",  "sample2ee26d9@gmail.com");

        request.body(requestParams.toString());
        Response response = request.get("/register");

        int statusCode = response.getStatusCode();
        System.out.println("The status code recieved: " + statusCode);

        System.out.println("Response body: " + response.body().asString());
    }
}
