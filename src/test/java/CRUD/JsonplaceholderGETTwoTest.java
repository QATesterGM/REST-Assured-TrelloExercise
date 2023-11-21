package CRUD;

import groovy.transform.Final;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonplaceholderGETTwoTest {

    // GIVEN - konfiguracja
    // WHEN - wysyłanie requestu
    // THEN - asercje


    private final String BASE_URL = "https://jsonplaceholder.typicode.com";  //utworzenie zmiennej dla adresu strony
    private final String USERS = "users";


    // LISTA PÓL

    @Test
    public void jsonplaceholderReadAllUsers(){

        Response response = given()
                .when()
                .get(BASE_URL + "/" + USERS)
                .then()
                .statusCode(200)
                .extract()
                .response();


        JsonPath json = response.jsonPath();
        List<String> names = json.getList("name"); // pobranie całej listy wartości z jednego klucza
        assertEquals(10, names.size()); // asercja sprawdzajaca ilość pól


    }


    // POJEDYNCZY KLUCZ

    @Test
    public void jasonplaceholderReadOneUser(){
        Response response = given()
                .when()
                .get(BASE_URL + "/" + USERS + "/1")
                .then()
                .statusCode(200)
                .extract()
                .response();


        JsonPath json = response.jsonPath();

        assertEquals("Leanne Graham", json.get("name"));
        assertEquals("Bret", json.get("username"));
        assertEquals("Sincere@april.biz", json.get("email"));
        assertEquals("Kulas Light", json.get("address.street"));  //zagnieżdzony piszemu Jpatha po kropce

    }

    // PATH VARIABLES

    @Test
    public void jasonplaceholderReadOneUserWithVariables(){

        Response response = given()
                .pathParam("userId", 2)
                .when()
                .get(BASE_URL + "/" + USERS + "/{userId}");

        assertEquals(200, response.statusCode());

        JsonPath json = response.jsonPath();

//        assertEquals("Leanne Graham", json.get("name"));
//        assertEquals("Bret", json.get("username"));
//        assertEquals("Sincere@april.biz", json.get("email"));
//        assertEquals("Kulas Light", json.get("address.street"));

        String name = json.get("name");
        System.out.println(name);


        System.out.println(response.asString());
    }

    // QUERY PARAMS

    @Test
    public void jasonplaceholderReadOneUserWithQuerryParams(){

        Response response = given()
                .queryParam("username", "Bret")
                .when()
                .get(BASE_URL + "/" + USERS);

        JsonPath json = response.jsonPath();

        assertEquals("Leanne Graham", json.getList("name").get(0)); // tu należy poprawić, ze chcemy liste imion
        assertEquals("Bret", json.getList("username").get(0));
        assertEquals("Sincere@april.biz", json.getList("email").get(0));
        assertEquals("Kulas Light", json.getList("address.street").get(0));

        System.out.println(response.asString());

    }
}