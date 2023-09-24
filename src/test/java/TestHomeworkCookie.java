import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class TestHomeworkCookie {
    @Test
    public void testHomeworkCookie(){
    Response response = RestAssured
            .given()
            .get("https://playground.learnqa.ru/api/homework_cookie")
            .andReturn();
    String responseCookie = response.getCookie("HomeWork");
    assertEquals("hw_value", responseCookie, "Unexpected cookie value");
    }
}
