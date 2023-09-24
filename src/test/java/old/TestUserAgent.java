package old;

import io.restassured.path.json.JsonPath;
import io.restassured.RestAssured;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class TestUserAgent {
    final String userAgent1="Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30";
    final String userAgent2="Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1";
    final String userAgent3="Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)";
    final String userAgent4="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0";
    final String userAgent5="Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1";

    @ParameterizedTest
    @ValueSource(strings={userAgent1,userAgent2,userAgent3,userAgent4,userAgent5})
    public void testUserAgent(String userAgent){
        Map<String,String> params = new HashMap<>();
        params.put("user-agent",userAgent);

        JsonPath response = RestAssured
                .given()
                .headers(params)
                .get("https://playground.learnqa.ru/ajax/api/user_agent_check")
                .jsonPath();

        String platform= response.getString("platform");
        String browser= response.getString("browser");
        String device= response.getString("device");

        String platformExpected= "";
        String browserExpected= "";
        String deviceExpected= "";

        switch(userAgent)
        {
            case(userAgent1):
                platformExpected="Mobile";
                browserExpected="No";
                deviceExpected="Android";
                break;

            case(userAgent2):
                platformExpected="Mobile";
                browserExpected="Chrome";
                deviceExpected="iOS";
                break;

            case(userAgent3):
                platformExpected="Googlebot";
                browserExpected="Unknown";
                deviceExpected="Unknown";
                break;

            case(userAgent4):
                platformExpected="Web";
                browserExpected="Chrome";
                deviceExpected="No";
                break;

            case(userAgent5):
                platformExpected="Mobile";
                browserExpected="No";
                deviceExpected="iPhone";
                break;
        }

        assertEquals(platformExpected,platform,userAgent+": Unexpected platform value");
        assertEquals(browserExpected,browser,userAgent+": Unexpected browser value");
        assertEquals(deviceExpected,device,userAgent+": Unexpected device value");
    }
}