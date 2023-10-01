package tests;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.BaseTestCase;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;;
import lib.Assertions;

public class UserGetTest extends BaseTestCase {

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    @Test
    public void testGetUserDataNotAuth() {
        Response repsonseUserData = RestAssured
                .get("https://playground.learnqa.ru/api/user/2")
                .andReturn();

        Assertions.assertJsonHasKey(repsonseUserData, "username");
        Assertions.assertJsonHasNotKey(repsonseUserData, "firstName");
        Assertions.assertJsonHasNotKey(repsonseUserData, "lastName");
        Assertions.assertJsonHasNotKey(repsonseUserData, "email");
    }

    @Test
    public void testGetUserDetailsAuthAsSameUser() {
        Map<String,String> authData= new HashMap<>();
        authData.put("email","vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        String header = this.getHeader(responseGetAuth,"x-csrf-token");
        String cookie = this.getCookie(responseGetAuth,"auth_sid");


        Response responseUserData= RestAssured
                .given()
                .header("x-csrf-token",header)
                .cookie("auth_sid",cookie)
                .get("https://playground.learnqa.ru/api/user/2")
                .andReturn();

        String [] expectedFields = {"username","firstName","lastName","email"};
        Assertions.assertJsonHasFields(responseUserData,expectedFields);
    }

    @Test
    public void testGetUserDetailsAuthAsOtherUser() {
        Map<String,String> authData= new HashMap<>();
        authData.put("email","vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login",authData);

        String header = this.getHeader(responseGetAuth,"x-csrf-token");
        String cookie = this.getCookie(responseGetAuth,"auth_sid");

        Response responseUserData = apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/1",header,cookie);

        Assertions.assertJsonHasKey(responseUserData, "username");
        Assertions.assertJsonHasNotKey(responseUserData, "firstName");
        Assertions.assertJsonHasNotKey(responseUserData, "lastName");
        Assertions.assertJsonHasNotKey(responseUserData, "email");
    }

}