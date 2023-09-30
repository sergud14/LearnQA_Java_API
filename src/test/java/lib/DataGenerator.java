package lib;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.BaseTestCase;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;;
import lib.Assertions;
import java.text.SimpleDateFormat;

public class DataGenerator {
    public static String getRandomEmail() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        return "learnqa"+ timestamp+ "@example.com";
    }

    public static String getRandomIncorrectEmail() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        return "learnqa"+ timestamp+ "example.com";
    }

    public static Map<String,String> getRegistrationData() {
        Map<String,String> data= new HashMap<>();
        data.put("email",DataGenerator.getRandomEmail());
        data.put("password","123");
        data.put("username","learnqa");
        data.put("firstName","learnqa");
        data.put("lastName","learnqa");
        return data;
    }

    public static Map<String,String> getRegistrationDataWithIncorrectEmail() {
        Map<String,String> data= new HashMap<>();
        data.put("email",DataGenerator.getRandomIncorrectEmail());
        data.put("password","123");
        data.put("username","learnqa");
        data.put("firstName","learnqa");
        data.put("lastName","learnqa");
        return data;
    }

    public static Map<String,String> getRegistrationDataWithEmptyEmail() {
        Map<String,String> data= new HashMap<>();
        data.put("email","");
        data.put("password","123");
        data.put("username","learnqa");
        data.put("firstName","learnqa");
        data.put("lastName","learnqa");
        return data;
    }

    public static Map<String,String> getRegistrationDataWithEmptyPassword() {
        Map<String,String> data= new HashMap<>();
        data.put("email",DataGenerator.getRandomEmail());
        data.put("password","");
        data.put("username","learnqa");
        data.put("firstName","learnqa");
        data.put("lastName","learnqa");
        return data;
    }

    public static Map<String,String> getRegistrationDataWithEmptyUsername() {
        Map<String,String> data= new HashMap<>();
        data.put("email",DataGenerator.getRandomEmail());
        data.put("password","123");
        data.put("username","");
        data.put("firstName","learnqa");
        data.put("lastName","learnqa");
        return data;
    }

    public static Map<String,String> getRegistrationDataWithEmptyFirstName() {
        Map<String,String> data= new HashMap<>();
        data.put("email",DataGenerator.getRandomEmail());
        data.put("password","123");
        data.put("username","learnqa");
        data.put("firstName","");
        data.put("lastName","learnqa");
        return data;
    }

    public static Map<String,String> getRegistrationDataWithEmptyLastName() {
        Map<String,String> data= new HashMap<>();
        data.put("email",DataGenerator.getRandomEmail());
        data.put("password","123");
        data.put("username","learnqa");
        data.put("firstName","learnqa");
        data.put("lastName","");
        return data;
    }

    public static Map<String,String> getRegistrationData(Map<String,String> nonDefaultValues) {
        Map<String,String> defaultValues= DataGenerator.getRegistrationData();
        Map<String,String> userData= new HashMap<>();
        String[] keys = {"email", "password","username","firstName","lastName"};
        for(String key:keys)
        {
            if(nonDefaultValues.containsKey(key))
            {
               userData.put(key,nonDefaultValues.get(key));
            }else
            {
                userData.put(key,defaultValues.get(key));
            }
        }
        return userData;
    }
}
