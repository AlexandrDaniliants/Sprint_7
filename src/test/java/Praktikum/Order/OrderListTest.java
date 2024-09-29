package Praktikum.Order;

import Praktikum.Requests.OrderRequest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

public class OrderListTest {

    OrderRequest orderRequest = new OrderRequest();

    @Before
    public void OrderSetUp(){
        orderRequest.setUpUri();
    }

    @Test
    @DisplayName("Проверка получения списка заказов")
    @Description("В тело ответа возвращается список заказов\n" +
            "Позитивная проверка")
    public void checkGettingListOfOrders(){
        orderRequest.checkOrdersGetStatusCodAndBodyNotNull();
    }
}
