package trainee;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;

import static org.junit.Assert.*;

public class WeatherToolQaTest {

    @Test
    public void GetWeatherDetails()
    {
        RestAssured.baseURI = "https://demoqa.com/utilities/weather/city";
        RequestSpecification httpRequest = RestAssured.given().log().all();
        Response response = httpRequest.request(Method.GET, "/Hyderabad");

        String responseBody = response.getBody().asString();
        System.out.println("Response Body is =>  " + responseBody);
    }

    @Test
    public void GetWeatherDetailsTwo()
    {
        RestAssured.baseURI = "https://demoqa.com/utilities/weather/city";
        RequestSpecification httpRequest = RestAssured.given().log().all();
        Response response = httpRequest.get("/Hyderabad");

        System.out.println("Response Body is =>  " + response.asString());
    }

    @Test
    public void GetWeatherDetailsTree()
    {
        RestAssured.baseURI = "https://demoqa.com/utilities/weather/city";
        RequestSpecification httpRequest = RestAssured.given().log().all();
        Response response = httpRequest.get("/Hyderabad");

        int statusCode = response.getStatusCode();
        assertEquals(statusCode, 200);
    }

    @Test
    public void GetWeatherDetailsInvalidCity()
    {
        RestAssured.baseURI = "https://demoqa.com/utilities/weather/city";
        RequestSpecification httpRequest = RestAssured.given().log().all();
        Response response = httpRequest.get("/");

        int statusCode = response.getStatusCode();
        assertEquals(statusCode, 200);
    }

    @Test
    public void GetWeatherStatusLine()
    {
        RestAssured.baseURI = "https://demoqa.com/utilities/weather/city";
        RequestSpecification httpRequest = RestAssured.given().log().all();
        Response response = httpRequest.get("/Hyderabad");

        String statusLine = response.getStatusLine();
        assertEquals(statusLine , "HTTP/1.1 200 OK");
    }

    @Test
    public void GetWeatherHeaders()
    {
        RestAssured.baseURI = "https://demoqa.com/utilities/weather/city";
        RequestSpecification httpRequest = RestAssured.given().log().all();
        Response response = httpRequest.get("/Hyderabad");

        String contentType = response.header("Content-Type");
        System.out.println("Content-Type value: " + contentType);

        String serverType =  response.header("Server");
        System.out.println("Server value: " + serverType);

        String acceptLanguage = response.header("Content-Encoding");
        System.out.println("Content-Encoding: " + acceptLanguage);
    }

    @Test
    public void IteratingOverHeaders()
    {
        RestAssured.baseURI = "https://demoqa.com/utilities/weather/city";
        RequestSpecification httpRequest = RestAssured.given().log().all();
        Response response = httpRequest.get("/Hyderabad");

        Headers allHeaders = response.headers();

        for(Header header : allHeaders)
        {
            System.out.println("Key: " + header.getName() + " Value: " + header.getValue());
        }
    }

    @Test
    public void GetWeatherHeadersFour()
    {
        RestAssured.baseURI = "https://demoqa.com/utilities/weather/city";
        RequestSpecification httpRequest = RestAssured.given().log().all();
        Response response = httpRequest.get("/Hyderabad");

        String serverType =  response.header("Server");
        assertEquals(serverType, "nginx/1.17.10 (Ubuntu)");
    }

    @Test
    public void WeatherMessageBody()
    {
        RestAssured.baseURI = "https://demoqa.com/utilities/weather/city";
        RequestSpecification httpRequest = RestAssured.given().log().all();
        Response response = httpRequest.get("/Hyderabad");
        ResponseBody body = response.getBody();

        System.out.println("Response Body is: " + body.asString());
    }

    @Test
    public void WeatherMessageBodyTwo()
    {
        RestAssured.baseURI = "https://demoqa.com/utilities/weather/city";
        RequestSpecification httpRequest = RestAssured.given().log().all();
        Response response = httpRequest.get("/Hyderabad");
        ResponseBody body = response.getBody();

        String bodyAsString = body.asString();
        assertTrue(bodyAsString.contains("Hyderabad"));
    }

    @Test
    public void WeatherMessageBodyTree()
    {
        RestAssured.baseURI = "https://demoqa.com/utilities/weather/city";
        RequestSpecification httpRequest = RestAssured.given().log().all();
        Response response = httpRequest.get("/Hyderabad");

        ResponseBody body = response.getBody();

        String bodyAsString = body.asString();
        assertTrue(bodyAsString.toLowerCase().contains("hyderabad"));
    }

    @Test
    public void VerifyCityInJsonResponse()
    {
        RestAssured.baseURI = "https://demoqa.com/utilities/weather/city";
        RequestSpecification httpRequest = RestAssured.given().log().all();
        Response response = httpRequest.get("/Hyderabad");

        JsonPath jsonPathEvaluator = response.jsonPath();

        String city = jsonPathEvaluator.get("City");

        System.out.println("City received from Response " + city);
        assertEquals(city, "Hyderabad");

    }

    @Test
    public void DisplayAllNodesInWeatherAPI()
    {
        RestAssured.baseURI = "https://demoqa.com/utilities/weather/city";
        RequestSpecification httpRequest = RestAssured.given().log().all();
        Response response = httpRequest.get("/Hyderabad");

        JsonPath jsonPathEvaluator = response.jsonPath();

        System.out.println("City received from Response " + jsonPathEvaluator.get("City"));
        System.out.println("Temperature received from Response " + jsonPathEvaluator.get("Temperature"));
        System.out.println("Humidity received from Response " + jsonPathEvaluator.get("Humidity"));
        System.out.println("Weather description received from Response " + jsonPathEvaluator.get("Weather"));
        System.out.println("City received from Response " + jsonPathEvaluator.get("WindSpeed"));
        System.out.println("City received from Response " + jsonPathEvaluator.get("WindDirectionDegree"));
    }
}