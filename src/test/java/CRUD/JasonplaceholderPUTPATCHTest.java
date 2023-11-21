package CRUD;

import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JasonplaceholderPUTPATCHTest {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com/users/1";
    private static Faker faker;
    private String fakeUsername;
    private String fakeEmail;
    private String fakePhone;
    private String fakeName;
    private String fakeWWW;

    //Uruchomi się tylko 1 raz przed wszsytkimi testami
    @BeforeAll
    public static void  beforeAll(){
        faker = new Faker();
    }

    //Uruchomi się zakażdym razem przed każdym testem
    @BeforeEach
    public void beforeEach(){
        fakeUsername = faker.name().username();
        fakeEmail = faker.internet().emailAddress();
        fakePhone = faker.phoneNumber().phoneNumber();
        fakeName = faker.name().name();
        fakeWWW = faker.internet().url();
    }

    @Test
    public void jsonplaceholderPUTTest(){

        JSONObject user = new JSONObject();
        user.put("name", fakeName);
        user.put("username", fakeUsername);
        user.put("email", fakeEmail);
        user.put("phone", fakePhone);
        user.put("website", fakeWWW);

        JSONObject geo = new JSONObject();
        geo.put("lat", "-38.2386");
        geo.put("lng", "57.2232");

        JSONObject address = new JSONObject();
        address.put("street", "sezamkowa");
        address.put("suite", "Suite 856");
        address.put("city", "Piździszewo");
        address.put("zipcode", "05-500");
        address.put("geo", geo);

        JSONObject company = new JSONObject();
        company.put("name", "Trevor Philips Company");
        company.put("catchPhrase", "Zdecentralizowana dziubpla by masow");
        company.put("bs", "end to end tests");

        user.put("address", address);
        user.put("company", company);

        //System.out.println(user.toString());

        Response response = given()
                .contentType("application/json")
                .body(user.toString())
                .when()
                .put(BASE_URL)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertEquals(fakeName, json.get("name"));
        assertEquals(fakeUsername, json.get("username"));
        assertEquals(fakeEmail, json.get("email"));

        System.out.println(response.asString());

    }

    @Test
    public void jsonplaceholderPATCHTest (){


        JSONObject userDetails = new JSONObject();
        userDetails.put("email", fakeEmail);
        userDetails.put("name", "Lukasz Gajda");

        Response response = given()
                .contentType("application/json")
                .body(userDetails.toString())
                .when()
                .patch(BASE_URL)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertEquals(fakeEmail, json.get("email"));
        assertEquals("Lukasz Gajda", json.get("name"));

        System.out.println(response.asString());

    }
}