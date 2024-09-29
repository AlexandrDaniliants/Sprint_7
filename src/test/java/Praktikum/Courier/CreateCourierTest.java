package Praktikum.Courier;

import Praktikum.Requests.CourierRequest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static Praktikum.Consants.CourierConstants.*;
import static org.junit.Assert.assertTrue;

public class CreateCourierTest {
    Courier courier = new Courier(COURIER_LOGIN + RandomStringUtils.randomAlphanumeric(3, 5), COURIER_PASSWORD, COURIER_FIRST_NAME);
    CourierRequest courierRequest = new CourierRequest();
    @Before
    public void CourierSetUp(){
        courierRequest.setUpUri();
    }

    @Test
    @DisplayName("Проверка создания курьера со всеми обязательными полями")
    @Description("Курьера можно создать.\n" +
            "Позитивная проверка")
    public void checkCreateCourier(){

        courierRequest.setCourier(courier);
        Response response = courierRequest.createCourier();
        Boolean checkRequest = courierRequest.assertCreatedResponse(response);
        assertTrue(checkRequest);
    }

    @Test
    @DisplayName("Проверка создания курьера без имени")
    @Description("Чтобы создать курьера, нужно передать в ручку только обязательные поля\n" +
            "Позитивная проверка")
    public void checkCreateCourierWithoutName() {
        courierRequest.setCourier(new Courier(COURIER_LOGIN + RandomStringUtils.randomAlphanumeric(3, 5), COURIER_PASSWORD, EMPTY_COURIER_FIRST_NAME ));
        Response response = courierRequest.createCourier();
        Boolean checkRequest = courierRequest.assertCreatedResponse(response);
        assertTrue(checkRequest);
    }

    @Test
    @DisplayName("Проверка создания дубликата курьера")
    @Description("Нельзя создать двух одинаковых курьеров.\n" +
            "Негативная проверка")
    public void checkCreateDuplicateCourier(){
        courierRequest.setCourier(courier);
        courierRequest.createCourier();
        courierRequest.createDuplicateCourier();
    }

    @Test
    @DisplayName("Проверка создания курьера без логина")
    @Description("Чтобы создать курьера, нужно передать в ручку все обязательные поля.\n" +
            "Негативная проверка")
    public void checkCreateCourierWithoutLogin(){
        courierRequest.setCourier(new Courier(EMPTY_COURIER_LOGIN, COURIER_PASSWORD, COURIER_FIRST_NAME));
        courierRequest.checkStatusCodAndMessageWithRequestWithoutRequiredField();
    }

    @Test
    @DisplayName("Проверка создания курьера без пароля")
    @Description("Чтобы создать курьера, нужно передать в ручку все обязательные поля.\n" +
            "Негативная проверка")
    public void checkCreateCourierWithoutPassword(){
        courierRequest.setCourier(new Courier(COURIER_LOGIN + RandomStringUtils.randomAlphanumeric(3, 5), EMPTY_COURIER_PASSWORD, COURIER_FIRST_NAME));
        courierRequest.checkStatusCodAndMessageWithRequestWithoutRequiredField();
    }

    @After
    public void courierRemoval(){
        courierRequest.deleteCourier();
    }
}
