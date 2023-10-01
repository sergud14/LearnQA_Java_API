package tests;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;;
import lib.Assertions;

public class UserEditTest extends BaseTestCase {

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    @Test
    public void testEditJustCreatedTest() {
        //Generate user

        Map<String,String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth= RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();

        String userId=responseCreateAuth.getString("id");

        //Login
        Map<String,String> authData= new HashMap<>();
        authData.put("email",userData.get("email"));
        authData.put("password",userData.get("password"));

        Response responseGetAuth= RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        //Edit
        String newName= "Changed name";
        Map<String,String> editData= new HashMap<>();
        editData.put("firstName",newName);

        Response responseEditUser= RestAssured
                .given()
                .header("x-csrf-token",this.getHeader(responseGetAuth,"x-csrf-token"))
                .cookie("auth_sid",this.getCookie(responseGetAuth,"auth_sid"))
                .body(editData)
                .put("https://playground.learnqa.ru/api/user/"+userId)
                .andReturn();

        //Get
        Response responseUserData= RestAssured
                .given()
                .header("x-csrf-token",this.getHeader(responseGetAuth,"x-csrf-token"))
                .cookie("auth_sid",this.getCookie(responseGetAuth,"auth_sid"))
                .get("https://playground.learnqa.ru/api/user/"+userId)
                .andReturn();

        Assertions.assertJsonByName(responseUserData,"firstName",newName);

    }


    @Test
    public void testEditJustCreatedTestWithoutAuth() {
        //Generate user
        Map<String,String> userData = DataGenerator.getRegistrationData();
        JsonPath responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/",userData).jsonPath();
        String userId=responseCreateAuth.getString("id");

        //Edit
        String newName= "Changed name";
        String newLastName= "Changed lastName";
        String newEmail= DataGenerator.getRandomEmail();
        String newUsername= "Changed username";
        Map<String,String> editData= new HashMap<>();
        editData.put("firstName",newName);
        editData.put("lastName",newLastName);
        editData.put("email",newEmail);
        editData.put("username",newUsername);
        Response responseEditUser= apiCoreRequests
                .makePutRequestWithoutAuth("https://playground.learnqa.ru/api/user/"+userId,editData);

        //Login
        Response responseGetAuth= apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login",userData);
        String token=this.getHeader(responseGetAuth,"x-csrf-token");
        String cookie=this.getCookie(responseGetAuth,"auth_sid");

        //Get
        Response responseUserData= apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/"+userId,token,cookie);

        Assertions.assertJsonByName(responseUserData,"firstName",userData.get("firstName"));
        Assertions.assertJsonByName(responseUserData,"lastName",userData.get("lastName"));
        Assertions.assertJsonByName(responseUserData,"email",userData.get("email"));
        Assertions.assertJsonByName(responseUserData,"username",userData.get("username"));
    }

    @Test
    public void testEditJustCreatedTestWithAnotherUserAuth() {
        //Generate User1
        Map<String,String> userData = DataGenerator.getRegistrationData();
        JsonPath responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/",userData).jsonPath();
        String userId=responseCreateAuth.getString("id");

        //Login with User2
        Map<String,String> userData2 = new HashMap<>();
        userData2.put("email","vinkotov@example.com");
        userData2.put("password","1234");
        Response responseGetAuth= apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login",userData2);
        String token2=this.getHeader(responseGetAuth,"x-csrf-token");
        String cookie2=this.getCookie(responseGetAuth,"auth_sid");

        //Edit User1 via User2
        String newName= "Changed name";
        String newLastName= "Changed lastName";
        String newEmail= DataGenerator.getRandomEmail();
        String newUsername= "Changed username";
        Map<String,String> editData= new HashMap<>();
        editData.put("firstName",newName);
        editData.put("lastName",newLastName);
        editData.put("email",newEmail);
        editData.put("username",newUsername);
        Response responseEditUser= apiCoreRequests
                .makePutRequest("https://playground.learnqa.ru/api/user/"+userId,editData,token2,cookie2);

        //Login with user1
        Response responseGetAuthUser1= apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login",userData);
        String token=this.getHeader(responseGetAuthUser1,"x-csrf-token");
        String cookie=this.getCookie(responseGetAuthUser1,"auth_sid");

        //Get
        Response responseUserData= apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/"+userId,token,cookie);

        Assertions.assertJsonByName(responseUserData,"firstName",userData.get("firstName"));
        Assertions.assertJsonByName(responseUserData,"lastName",userData.get("lastName"));
        Assertions.assertJsonByName(responseUserData,"email",userData.get("email"));
        Assertions.assertJsonByName(responseUserData,"username",userData.get("username"));
    }

    @Test
    public void testEditJustCreatedTestWithWrongEmail() {
        //Generate user
        Map<String,String> userData = DataGenerator.getRegistrationData();
        JsonPath responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/",userData).jsonPath();
        String userId=responseCreateAuth.getString("id");

        //Login
        Map<String,String> authData= new HashMap<>();
        authData.put("email",userData.get("email"));
        authData.put("password",userData.get("password"));

        Response responseGetAuth= RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        //Edit
        Map<String,String> editData= new HashMap<>();
        editData.put("email",DataGenerator.getRandomIncorrectEmail());

        Response responseEditUser= RestAssured
                .given()
                .header("x-csrf-token",this.getHeader(responseGetAuth,"x-csrf-token"))
                .cookie("auth_sid",this.getCookie(responseGetAuth,"auth_sid"))
                .body(editData)
                .put("https://playground.learnqa.ru/api/user/"+userId)
                .andReturn();

        //Get
        Response responseUserData= RestAssured
                .given()
                .header("x-csrf-token",this.getHeader(responseGetAuth,"x-csrf-token"))
                .cookie("auth_sid",this.getCookie(responseGetAuth,"auth_sid"))
                .get("https://playground.learnqa.ru/api/user/"+userId)
                .andReturn();

        Assertions.assertJsonByName(responseUserData,"email",userData.get("email"));
    }

    @Test
    public void testEditJustCreatedTestWithTooShortName() {
        //Generate user
        Map<String,String> userData = DataGenerator.getRegistrationData();
        JsonPath responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/",userData).jsonPath();
        String userId=responseCreateAuth.getString("id");

        //Login
        Map<String,String> authData= new HashMap<>();
        authData.put("email",userData.get("email"));
        authData.put("password",userData.get("password"));

        Response responseGetAuth= RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        //Edit
        String newName= "q";
        Map<String,String> editData= new HashMap<>();
        editData.put("firstName",newName);

        Response responseEditUser= RestAssured
                .given()
                .header("x-csrf-token",this.getHeader(responseGetAuth,"x-csrf-token"))
                .cookie("auth_sid",this.getCookie(responseGetAuth,"auth_sid"))
                .body(editData)
                .put("https://playground.learnqa.ru/api/user/"+userId)
                .andReturn();

        //Get
        Response responseUserData= RestAssured
                .given()
                .header("x-csrf-token",this.getHeader(responseGetAuth,"x-csrf-token"))
                .cookie("auth_sid",this.getCookie(responseGetAuth,"auth_sid"))
                .get("https://playground.learnqa.ru/api/user/"+userId)
                .andReturn();

        Assertions.assertJsonByName(responseUserData,"firstName",userData.get("firstName"));
    }

}