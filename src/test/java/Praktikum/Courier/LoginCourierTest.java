package Praktikum.Courier;

import Praktikum.Requests.CourierRequest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static Praktikum.Consants.CourierConstants.*;

public class LoginCourierTest {
    CourierRequest courierRequest = new CourierRequest();
    Courier courier = new Courier (COURIER_LOGIN + RandomStringUtils.randomAlphanumeric(3, 5), COURIER_PASSWORD);

    @Before
    public void CourierSetUp(){
        courierRequest.setUpUri();
    }

    @Test
    @DisplayName("Авторизация курьера с логином и паролем")
    @Description("Курьер может авторизоваться, для авторизации нужно передать все обязательные поля.\n" +
            "Позитивная проверка")
    public void checkCourierAuthorization(){
        courierRequest.setCourier(courier);
        courierRequest.createCourier();
        courierRequest.logInCourierAndCheckIdAndStatusCod();
    }

    @Test
    @DisplayName("Авторизация курьера без логина, с паролем")
    @Description("Курьер не может авторизоваться, если не передать обязательное поле логин.\n" +
            "Негативная проверка")
    public void checkCourierAuthorizationWithoutLogin(){
        courierRequest.setCourier(new Courier(EMPTY_COURIER_LOGIN, COURIER_PASSWORD));
        courierRequest.checkMessageAndStatusCodWithRequestWithoutRequiredField();
    }

    @Test
    @DisplayName("Авторизация курьера с логином, без пароля")
    @Description("Курьер не может авторизоваться, если не передать обязательное поле пароль.\n" +
            "Негативная проверка")
    public void checkCourierAuthorizationWithoutPassword(){
        courierRequest.setCourier(new Courier(COURIER_LOGIN + RandomStringUtils.randomAlphanumeric(3, 5), EMPTY_COURIER_PASSWORD));
        courierRequest.checkMessageAndStatusCodWithRequestWithoutRequiredField();
    }

    @Test
    @DisplayName("Авторизация не существующего курьера")
    @Description("Если авторизоваться с несуществующей парой логин-пароль, запрос возвращает ошибку.\n" +
            "Негативная проверка")
    public void checkAuthorizationNonExistingCourier(){
        courierRequest.setCourier(courier);
        courierRequest.checkMessageAndStatusCodForAutorizationNonExistingCourier();
    }

    @Test
    @DisplayName("Авторизация курьера с неверным логином")
    @Description("Если авторизоваться с несуществующей парой логин-пароль, запрос возвращает ошибку.\n" +
            "Негативная проверка")
    public void checkCourierAuthorizationWithIncorrectLogin() {
        courierRequest.setCourier(courier);
        courierRequest.createCourier();
        courierRequest.setCourier(new Courier(RandomStringUtils.randomAlphanumeric(3, 5), COURIER_PASSWORD));
        courierRequest.checkMessageAndStatusCodForAutorizationWithIncorrectField();

    }

    @Test
    @DisplayName("Авторизация курьера с неверным паролем")
    @Description("Если авторизоваться с несуществующей парой логин-пароль, запрос возвращает ошибку.\n" +
            "Негативная проверка")
    public void checkCourierAuthorizationWithIncorrectPassword() {
        courierRequest.setCourier(courier);
        courierRequest.createCourier();
        courierRequest.setCourier(new Courier(COURIER_LOGIN + RandomStringUtils.randomAlphanumeric(3, 5), RandomStringUtils.randomAlphanumeric(4, 5)));
        courierRequest.checkMessageAndStatusCodForAutorizationWithIncorrectField();
    }

    @After
    public void courierRemoval(){
        courierRequest.deleteCourier();
    }
}
