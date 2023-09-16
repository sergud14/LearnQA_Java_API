import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import java.util.Objects;
public class TestToken{
    @Test
    public void testRedirection() throws InterruptedException {
        JsonPath response = RestAssured
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
//        response.prettyPrint();
        String token = response.get("token");
        int seconds = response.get("seconds");
        System.out.println("Токен: "+token);

        JsonPath responseWithToken = RestAssured
                .given()
                .queryParam("token",token)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
//        responseWithToken.prettyPrint();
        String status = responseWithToken.get("status");
        System.out.println("Статус ДО: " +status);

        if (Objects.equals(status, "Job is NOT ready")) {
            Thread.sleep((seconds + 1) * 1000);

            JsonPath responseWithToken2 = RestAssured
                    .given()
                    .queryParam("token",token)
                    .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                    .jsonPath();
            //responseWithToken2.prettyPrint();
            String status2 = responseWithToken2.get("status");
            String result = responseWithToken2.get("result");
            System.out.println("Статус ПОСЛЕ: " +status2);
            System.out.println("Результат: " +result);
        }
    }
}