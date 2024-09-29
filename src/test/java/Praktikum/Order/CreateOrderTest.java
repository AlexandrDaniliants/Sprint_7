package Praktikum.Order;

import Praktikum.Requests.OrderRequest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static Praktikum.Consants.OrderConstants.*;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    Order order;

    public CreateOrderTest(Order order) {
        this.order = order;
    }

    @Before
    public void setUp(){
        orderRequest.setUpUri();
    }

    @Parameterized.Parameters
    public static Object[][] testDataSet(){
        return new Object[][]{
                {new Order(CUSNOMER1_FIRST_NAME,CUSNOMER1_LAAST_NAME,CUSNOMER1_ADDRESS,CUSNOMER1_METROSTATION,CUSNOMER1_PHONE,CUSNOMER1_RENT_TIME,CUSNOMER1_DELIVERY_DATE,CUSNOMER1_COMMENT,new String[]{BLACK})},
                {new Order(CUSNOMER2_FIRST_NAME,CUSNOMER2_LAAST_NAME,CUSNOMER2_ADDRESS,CUSNOMER2_METROSTATION,CUSNOMER2_PHONE,CUSNOMER2_RENT_TIME,CUSNOMER2_DELIVERY_DATE,CUSNOMER2_COMMENT,new String[]{GREY})},
                {new Order(CUSNOMER3_FIRST_NAME,CUSNOMER3_LAAST_NAME,CUSNOMER3_ADDRESS,CUSNOMER3_METROSTATION,CUSNOMER3_PHONE,CUSNOMER3_RENT_TIME,CUSNOMER3_DELIVERY_DATE,CUSNOMER3_COMMENT,new String[]{BLACK,GREY})},
                {new Order(CUSNOMER4_FIRST_NAME,CUSNOMER4_LAAST_NAME,CUSNOMER4_ADDRESS,CUSNOMER4_METROSTATION,CUSNOMER4_PHONE,CUSNOMER4_RENT_TIME,CUSNOMER4_DELIVERY_DATE,CUSNOMER4_COMMENT,new String[]{})}
        };
    }

    OrderRequest orderRequest = new OrderRequest();

    @Test
    @DisplayName("Проверка создания заказа с разным цветом самоката")
    @Description("можно указать один из цветов — BLACK или GREY;\n" +
            "можно указать оба цвета;\n" +
            "можно совсем не указывать цвет;\n" +
            "тело ответа содержит track.")
    public void checkCreateOrder(){
        orderRequest.setOrder(order);
        orderRequest.checkCreateOrderStatusCodAndTrack();
    }
}
