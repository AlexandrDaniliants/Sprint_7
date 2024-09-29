package Praktikum.Requests;

import Praktikum.Courier.Courier;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static Praktikum.Consants.CourierConstants.CREATE_COURIER_PATH;
import static Praktikum.Consants.CourierConstants.LOGIN_COURIER_PATH;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;


public class CourierRequest extends ApiUri {

    private Courier courier;

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    @Step("Отправить запрос на создание курьера")
    public Response createCourier(){
        Response response =
                given().log().all()
                        .contentType(ContentType.JSON)
                        .body(courier)
                        .when()
                        .post (CREATE_COURIER_PATH);
        return response;
    }

    @Step("Проверить код ответа после запроса на создание курьера")
    public Boolean assertCreatedResponse(Response response) {
        return response
                .then().log().all()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .extract()
                .path("ok");
    }

    @Step("Проверить код ответа после запроса на создание существующего курьера")
    public ValidatableResponse createDuplicateCourier() {
        return createCourier()
                .then().log().all()
                .assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(HttpURLConnection.HTTP_CONFLICT);
    }

    @Step("Отправить запрос на создание курьера с неполными данными и проверить статус код и сообщение в ответе")
    public ValidatableResponse checkStatusCodAndMessageWithRequestWithoutRequiredField() {
        return createCourier()
                .then()
                .log().all()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .and()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Step("Отправить запрос на авторизацию курьера")
    public Response loginCourier(){
        Response response =
                given().log().all()
                        .contentType(ContentType.JSON)
                        .body(courier)
                        .when()
                        .post (LOGIN_COURIER_PATH);
        return response;
    }

    @Step("Отправить запрос на авторизацию курьера, проверить что id не Null, код ответа 200 Ok")
    public ValidatableResponse logInCourierAndCheckIdAndStatusCod() {
        return loginCourier()
                .then()
                .assertThat()
                .body("id", notNullValue())
                .and()
                .statusCode(HttpURLConnection.HTTP_OK);
    }

    @Step("Отправить запрос на авторизацию курьера с неполными данными и проверить статус код и сообщение в ответе")
    public ValidatableResponse checkMessageAndStatusCodWithRequestWithoutRequiredField() {
        return loginCourier()
                .then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST);
    }

    @Step("Отправить запрос на авторизацию не существующего курьера и проверить статус код и сообщение в ответе")
    public ValidatableResponse checkMessageAndStatusCodForAutorizationNonExistingCourier() {
        return loginCourier()
                .then()
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND);
    }

   @Step("Отправить запрос на авторизацию курьера с несуществующей парой логин-пароль и проверить статус код и сообщение в ответе")
    public ValidatableResponse checkMessageAndStatusCodForAutorizationWithIncorrectField() {
        return loginCourier()
                .then()
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND);
    }

    @Step("Удалить курьера")
    public void deleteCourier(){
        Integer id =
                given().log().all()
                        .contentType(ContentType.JSON)
                        .body(courier)
                        .when()
                        .post (LOGIN_COURIER_PATH)
                        .then().log().all()
                        .extract().body().path("id");
        if (id != null) {
            given()
                    .delete (CREATE_COURIER_PATH + "/{id}", id.toString());}
    }
}
