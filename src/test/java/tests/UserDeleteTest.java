package tests;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

public class UserDeleteTest extends BaseTestCase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    @Test
    public void testDeleteUserWithId2Test() {
        //Login
        Map<String,String> userData = new HashMap<>();
        userData.put("email","vinkotov@example.com");
        userData.put("password","1234");
        Response responseGetAuth= apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login",userData);
        String token=this.getHeader(responseGetAuth,"x-csrf-token");
        String cookie=this.getCookie(responseGetAuth,"auth_sid");
        int userId= this.getIntFromJson(responseGetAuth,"user_id");

        //Delete
        Response responseDeleteUser= apiCoreRequests
                .makeDeleteRequest("https://playground.learnqa.ru/api/user/"+userId,token,cookie);

        Assertions.assertHtmlBodyElementTextEquals(responseDeleteUser,"Please, do not delete test users with ID 1, 2, 3, 4 or 5.");
    }

    @Test
    public void testDeleteCreatedUserSuccessfully() {
        //Generate User1
        Map<String,String> userData1 = DataGenerator.getRegistrationData();
        JsonPath responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/",userData1).jsonPath();
        //String idUser=responseCreateAuth.getString("id");

        //Login
        Map<String,String> userData = new HashMap<>();
        userData.put("email",userData1.get("email"));
        userData.put("password",userData1.get("password"));
        Response responseGetAuth= apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login",userData);
        String token=this.getHeader(responseGetAuth,"x-csrf-token");
        String cookie=this.getCookie(responseGetAuth,"auth_sid");
        int userId= this.getIntFromJson(responseGetAuth,"user_id");

        //Delete
        Response responseDeleteUser= apiCoreRequests
                .makeDeleteRequest("https://playground.learnqa.ru/api/user/"+userId,token,cookie);

        //Get user
        Response responseGetUser= apiCoreRequests
                .makeGetRequestWithoutTokenAndCookie("https://playground.learnqa.ru/api/user/"+userId);

        Assertions.assertResponseCodeEquals(responseGetUser,404);
        Assertions.assertHtmlBodyElementTextEquals(responseGetUser,"User not found");
    }

    @Test
    public void testDeleteCreatedUserNegative() {
        //Generate User1
        Map<String,String> userData1 = DataGenerator.getRegistrationData();
        JsonPath responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/",userData1).jsonPath();
        int userId = Integer.parseInt(responseCreateAuth.getString("id"));
        //System.out.println(userId);

        //Login
        Map<String,String> userData2 = new HashMap<>();
        userData2.put("email","vinkotov@example.com");
        userData2.put("password","1234");
        Response responseGetAuth= apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login",userData2);
        String token2=this.getHeader(responseGetAuth,"x-csrf-token");
        String cookie2=this.getCookie(responseGetAuth,"auth_sid");

        //Delete
        Response responseDeleteUser= apiCoreRequests
                .makeDeleteRequest("https://playground.learnqa.ru/api/user/"+userId,token2,cookie2);

        //Get user
        Response responseGetUser= apiCoreRequests
                .makeGetRequestWithoutTokenAndCookie("https://playground.learnqa.ru/api/user/"+userId);

        Assertions.assertJsonByName(responseGetUser,"username",userData1.get("username"));;
    }




}
