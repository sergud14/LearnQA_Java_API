package old;

import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Test1 {
    @Test
    public void test1(){
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/get_text")
                .andReturn();
        response.prettyPrint();
    }
}
