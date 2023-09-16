import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;

public class TestParsingJson {
    @Test
    public void testJSONParsing(){
        JsonPath response = RestAssured
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();
        response.prettyPrint();
        String message = response.get("messages[1].message");
        System.out.println(message);
    }
}