package CRUD;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class JsonplaceholderGETTest {

    // GIVEN - konfiguracja
    // WHEN - wysyłanie requestu
    // THEN - asercje


    // LISTA PÓL

    @Test
    public void jsonplaceholderReadAllUsers(){

        Response response = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users");

        System.out.println(response.asString());

        Assertions.assertEquals(200, response.statusCode());

        JsonPath json = response.jsonPath();

        List<String> names = json.getList("name"); // pobranie całej listy wartości z jednego klucza


//        names.stream()
//                .forEach(System.out::println);


        Assertions.assertEquals(10, names.size()); // asercja sprawdzajaca ilość pól


    }


    // POJEDYNCZY KLUCZ

    @Test
    public void jasonplaceholderReadOneUser(){
        Response response = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users/1");

        Assertions.assertEquals(200, response.statusCode());

        JsonPath json = response.jsonPath();

        Assertions.assertEquals("Leanne Graham", json.get("name"));
        Assertions.assertEquals("Bret", json.get("username"));
        Assertions.assertEquals("Sincere@april.biz", json.get("email"));
        Assertions.assertEquals("Kulas Light", json.get("address.street"));  //zagnieżdzony piszemu Jpatha po kropce

        System.out.println(response.asString());

    }

    // PATH VARIABLES

    @Test
    public void jasonplaceholderReadOneUserWithVariables(){

        Response response = given()
                .pathParam("userId", 1)
                .when()
                .get("https://jsonplaceholder.typicode.com/users/{userId}");

        Assertions.assertEquals(200, response.statusCode());

        JsonPath json = response.jsonPath();

        Assertions.assertEquals("Leanne Graham", json.get("name"));
        Assertions.assertEquals("Bret", json.get("username"));
        Assertions.assertEquals("Sincere@april.biz", json.get("email"));
        Assertions.assertEquals("Kulas Light", json.get("address.street"));

        System.out.println(response.asString());
    }

    // QUERY PARAMS

    @Test
    public void jasonplaceholderReadOneUserWithQuerryParams(){

        Response response = given()
                .queryParam("username", "Bret")
                .when()
                .get("https://jsonplaceholder.typicode.com/users/");

        JsonPath json = response.jsonPath();

        Assertions.assertEquals("Leanne Graham", json.getList("name").get(0)); // tu należy poprawić, ze chcemy liste imion
        Assertions.assertEquals("Bret", json.getList("username").get(0));
        Assertions.assertEquals("Sincere@april.biz", json.getList("email").get(0));
        Assertions.assertEquals("Kulas Light", json.getList("address.street").get(0));

        System.out.println(response.asString());

    }
}