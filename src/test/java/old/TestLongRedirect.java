package old;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
public class TestLongRedirect{
    @Test
    public void testLongRedirection(){

        int statusCode;
        int redirectCount=0;
        String url="https://playground.learnqa.ru/api/long_redirect";

        do{
            Response response = RestAssured
                    .given()
                    .redirects()
                    .follow(false)
                    .when()
                    .get(url)
                    .andReturn();

            url = response.getHeader("Location");
            statusCode = response.getStatusCode();

            if(statusCode==301) {
                redirectCount++;
                System.out.println(url);
            }
        }
        while(statusCode!=200);
        System.out.println("Кол-во редиректов:"+ redirectCount);
    }
}