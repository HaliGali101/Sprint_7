package scooter;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import scooter.pojo.Orders;
import scooter.testData.OrdersData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class OrderTests extends SetUp {

    private String track;

    private final ArrayList<String> colorsArray;
    private final int statusCode;

    public OrderTests(ArrayList<String> colorsArray, int statusCode) {
        this.colorsArray = colorsArray;
        this.statusCode = statusCode;
    }


    @Parameterized.Parameters
    public static Object[][] setUp() {
        return new Object[][] {
                {new ArrayList<>(List.of("BLACK")), 201},
                {new ArrayList<>(List.of("GRAY")), 201},
                {new ArrayList<>(Arrays.asList("BLACK", "GRAY")), 201},
                {new ArrayList<>(List.of()),201}
        };
    }

    @Test
    @DisplayName("Создание заказа.Чёрный цвет")
    @Description("Создать заказ с чёрным цветом самоката")
    public void createOrderWithBlackColor() {
        ValidatableResponse response = createOrder(colorsArray)
                .statusCode(statusCode)
                .assertThat().body("track", notNullValue());

        track = response.extract().path("track").toString();
    }

    @After
    @Description("Постусловие.Завершить заказ")
    public void cleanUp() {
        finishOrder();
    }

    @Step("Создать заказ")
    private ValidatableResponse createOrder(ArrayList<String> colorConfig) {
        Orders order = OrdersData.getOrderData(colorConfig);
        return OrdersMethods.postCreateOrders(order);
    }

    @Step("Завершить заказ")
    private void finishOrder() {
        String orderId = OrdersMethods.getOrderInfo(track).statusCode(200).extract().path("order.id").toString();
        OrdersMethods.finishOrder(orderId);
    }

}
