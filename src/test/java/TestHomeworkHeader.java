import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class TestHomeworkHeader {
    @Test
    public void testHomeworkHeader(){
        String header = RestAssured
                .get("https://playground.learnqa.ru/api/homework_header")
                .getHeader("x-secret-homework-header");
        assertEquals("Some secret value", header, "Unexpected header value");
    }
}