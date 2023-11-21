package CRUD;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonplaceholderPOSTTest {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com";

    @Test
    public void jsonplaceholderCreateNewUser(){

        JSONObject user = new JSONObject();
        user.put("name", "Lukasz");
        user.put("username", "Gajda");
        user.put("email", "akademiaqa@gmail.com");
        user.put("phone", "50147513");
        user.put("website", "www.nieźle.pl");

        JSONObject address = new JSONObject();
        address.put("street", "sezamkowa");
        address.put("suite", "Suite 856");
        address.put("city", "Piździszewo");
        address.put("zipcode", "05-500");

        JSONObject geo = new JSONObject();
        geo.put("lat", "-38.2386");
        geo.put("lng", "57.2232");

        JSONObject company = new JSONObject();
        company.put("name", "Trevor Philips Company");
        company.put("catchPhrase", "Zdecentralizowana dziubpla by masow");
        company.put("bs","backend tests");

        address.put("geo", geo);
        user.put("address", address);
        user.put("company", company);

        System.out.println(user.toString());


//        String jsonBody = "{\n" +
//                "    \"name\": \"Lukasz\",\n" +
//                "    \"username\": \"Gajda\",\n" +
//                "    \"email\": \"akademiaqa@gmail.com\",\n" +
//                "    \"address\": {\n" +
//                "        \"street\": \"sezamkowa\",\n" +
//                "        \"suite\": \"Suite 856\",\n" +
//                "        \"city\": \"Piździszewo\",\n" +
//                "        \"zipcode\": \"05-500\",\n" +
//                "        \"geo\": {\n" +
//                "            \"lat\": \"-38.2386\",\n" +
//                "            \"lng\": \"57.2232\" \n" +
//                "        }\n" +
//                "    },\n" +
//                "    \"phone\": \"50147513\",\n" +
//                "    \"website\": \"nieźle.pl\",\n" +
//                "    \"company\": {\n" +
//                "        \"name\": \"Trevor Philips Company\",\n" +
//                "        \"catchPhrase\": \"Zdecentralizowana dziubpla by masow\",\n" +
//                "        \"bs\": \"backend tests\"\n" +
//                "    }\n" +
//                "}";

        Response response = given()
                .contentType("application/json")
                .body(user.toString())
                .when()
                .post(BASE_URL + "/users")
                .then()
                .statusCode(201)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals("Lukasz", json.get("name"));
        assertEquals("Gajda", json.get("username"));
        assertEquals("akademiaqa@gmail.com", json.get("email"));
        assertEquals("-38.2386", json.get("address.geo.lat"));

        System.out.println(response.asString());
    }
}