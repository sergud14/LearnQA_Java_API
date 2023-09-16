import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
public class TestRedirect {
    @Test
    public void testRedirection(){

        Response response  = RestAssured
                .given()
                .redirects()
                .follow(false)
                .expect().statusCode(301)
                .when()
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();

        String redirectUrl = response.getHeader("Location");
        System.out.println(redirectUrl);
    }
}