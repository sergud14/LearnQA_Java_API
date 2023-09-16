
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
public class TestRedirect {
    @Test
    public void testRedirection(){
        Response response  = RestAssured
                .given()
                .redirects()
                .follow(true)
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();

        String redirectUrl = response.getHeader("X-Host");
        System.out.println(redirectUrl);
    }
}