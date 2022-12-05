package E2E;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MoveCardBetweenListsTest {

    private static final String BASE_URL = "https://api.trello.com/1";
    private static final String BOARDS = "boards";
    private static final String LISTS = "lists";
    private static final String CARDS = "cards";

    private static final String KEY = "974d97384cad318292f34fb96f2e36d2";
    private static final String TOKEN = "f29d435f5d83e368bc93427af23743d23676aaf56865679bcae73ad57832e86b";

    private static String boardId;
    private static String firstListId;
    private static String secondListId;
    private static String cardId;

    @Test
    @Order(1)
    public void createNewBoard() {

        Response response = given()
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "My e2e board")
                .queryParam("defaultLists", false)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL + "/" + BOARDS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo("My e2e board");

        boardId = json.getString("id");
    }

    @Test
    @Order(2)
    public void createFirstList(){

        Response response = given()
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("idBoard", boardId)
                .queryParam("name", "First list")
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL + "/" + LISTS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo("First list");

        firstListId = json.getString("id");
    }

    @Test
    @Order(3)
    public void createSecondList(){

        Response response = given()
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("idBoard", boardId)
                .queryParam("name", "Second list")
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL + "/" + LISTS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo("Second list");

        secondListId = json.getString("id");
    }

    @Test
    @Order(4)
    public void addCardToFirstList(){

        Response response = given()
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("idList", firstListId)
                .queryParam("name", "My e2e Card")
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL + "/" + CARDS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo("My e2e Card");

        cardId = json.getString("id");
    }

}
