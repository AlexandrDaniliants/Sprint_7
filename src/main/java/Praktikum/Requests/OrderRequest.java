package Praktikum.Requests;

import Praktikum.Order.Order;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static Praktikum.Consants.OrderConstants.CREATE_ORDER_PATH;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderRequest extends ApiUri {
    private Order order;

    public void setOrder(Order order) {
        this.order = order;
    }

    @Step("Отправить запрос на создание заказа")
    public Response createOrderRequest(){
        Response response =
                given().log().all()
                        .contentType(ContentType.JSON)
                        .body(order)
                        .when()
                        .post (CREATE_ORDER_PATH);
        return response;
    }
    @Step("Отправить запрос на создание заказа, проверить в ответе статус-код 201 Created, track не Null")
    public ValidatableResponse checkCreateOrderStatusCodAndTrack() {
        return createOrderRequest()
                .then()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .and()
                .assertThat()
                .body("track", notNullValue());
    }

    @Step("Отправить запрос на получение списка заказов")
    public Response ordersGetRequest(){
        Response response =
                given().log().all()
                        .get (CREATE_ORDER_PATH);
        return  response;
    }

    @Step("Отправить запрос на получение списка заказов, проверить в ответе статус-код 200 Ок,\n" +
            "запрос на получение списка заказов")
    public ValidatableResponse checkOrdersGetStatusCodAndBodyNotNull() {
        return ordersGetRequest()
                .then().statusCode(HttpURLConnection.HTTP_OK)
                .and()
                .assertThat()
                .body("orders", notNullValue());
    }
}
