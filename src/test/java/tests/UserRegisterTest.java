package tests;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.HashMap;
import java.util.Map;;
import lib.Assertions;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import java.util.Objects;
import lib.ApiCoreRequests;
import lib.DataGenerator;
public class UserRegisterTest extends BaseTestCase {

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();


    @Test
    public void testCreateUserWithExistingEmail() {

        String email = "vinkotov@example.com";
        Map<String,String> userData= new HashMap<>();
        userData.put("email", email);
        userData=DataGenerator.getRegistrationData(userData);

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseCodeEquals(responseCreateAuth,400);
        Assertions.assertResponseTextEquals(responseCreateAuth,"Users with email '" + email + "' already exists");
    }

    @Test
    public void testCreateUserSuccessfully() {
        String email = DataGenerator.getRandomEmail();
        Map<String,String> userData= DataGenerator.getRegistrationData();

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseCodeEquals(responseCreateAuth,200);
        Assertions.assertJsonHasKey(responseCreateAuth,"id");
    }


    @Test
    public void testCreateUserWithIncorrectEmail() {
        Map<String,String> userData= DataGenerator.getRegistrationDataWithIncorrectEmail();

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/",userData);

         Assertions.assertResponseCodeEquals(responseCreateAuth,400);
         Assertions.assertHtmlBodyElementTextEquals(responseCreateAuth,"Invalid email format");
    }


    @ParameterizedTest
    @ValueSource (strings={"username","firstName","lastName","email","password"})
    public void testCreateUserWithEmptyField(String condition) {

        Map<String, String> userData = DataGenerator.getRegistrationData();

        switch (condition) {
        case ("username"):
        userData.put("username","");
        break;

        case ("firstName"):
        userData.put("firstName","");
        break;

        case ("lastName"):
        userData.put("lastName","");
        break;

        case ("email"):
        userData.put("email","");
        break;

        case ("password"):
        userData.put("password","");
        break;
    }

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/",userData);

        Assertions.assertResponseCodeEquals(responseCreateAuth,400);
        Assertions.assertHtmlBodyElementTextEquals(responseCreateAuth,"The value of '"+condition+"' field is too short");
    }

    @Test
    public void testCreateUserWithTooShortUsername() {
        Map<String,String> userData= DataGenerator.getRegistrationData();
        userData.put("username","q");

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/",userData);

        Assertions.assertResponseCodeEquals(responseCreateAuth,400);
        Assertions.assertHtmlBodyElementTextEquals(responseCreateAuth,"The value of 'username' field is too short");
    }

    @Test
    public void testCreateUserWithTooLongUsername() {
        Map<String,String> userData= DataGenerator.getRegistrationData();
        userData.put("username","qwertyuiopadszdxfcghjkghgfbvczxczvbnmbfdsaffgtjrytrsesfdvbxgcfgxadfzvsvgfdfgcasdfasdfsdfghyfhjtyhgfgbdsgdsfgsdfgsdgfgssdfgdfgdfgdfgdgdfgdgdfgdgfdghjjkkjtertefsddfdsgjkjkukykyukukutikikiikkikikikikikikikiyjrdhsfdsfgsdghdfghdfghsdfgarttruhgfbgdsghjgmfjhkgfhgdfs");

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/",userData);

        Assertions.assertResponseCodeEquals(responseCreateAuth,400);
        Assertions.assertHtmlBodyElementTextEquals(responseCreateAuth,"The value of 'username' field is too long");
    }
}