package scooter;

import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import scooter.pojo.Orders;
import scooter.testData.OrdersData;

import java.util.ArrayList;

import static org.hamcrest.Matchers.*;

public class OrderTests extends SetUp {

    private final String blackColor = "BLACK";
    private final String grayColor = "GRAY";
    private String track;

    @Test
    @Description("Создание заказа.Чёрный цвет")
    public void createOrderWithBlackColor() {
        ArrayList<String> scooterColor = new ArrayList<>();
        scooterColor.add(blackColor);

        Orders order = OrdersData.getOrderData(scooterColor);

        ValidatableResponse response = OrdersMethods.postCreateOrders(order)
                .statusCode(201)
                .assertThat().body("track", notNullValue());

        track = response.extract().path("track").toString();
    }

    @Test
    @Description("Создание заказа.Серый цвет")
    public void createOrderWithGrayColor() {
        ArrayList<String> scooterColor = new ArrayList<>();
        scooterColor.add(grayColor);

        Orders order = OrdersData.getOrderData(scooterColor);

        ValidatableResponse response = OrdersMethods.postCreateOrders(order)
                .statusCode(201)
                .assertThat().body("track", notNullValue());

        track = response.extract().path("track").toString();
    }

    @Test
    @Description("Создание заказа.Все цвета")
    public void createOrderWithAllColors() {
        ArrayList<String> scooterColor = new ArrayList<>();
        scooterColor.add(blackColor);
        scooterColor.add(grayColor);

        Orders order = OrdersData.getOrderData(scooterColor);

        ValidatableResponse response = OrdersMethods.postCreateOrders(order)
                .statusCode(201)
                .assertThat().body("track", notNullValue());

        track = response.extract().path("track").toString();
    }

    @Test
    @Description("Создание заказа.Без цветов")
    public void createOrderWithoutColors() {
        ArrayList<String> scooterColor = new ArrayList<>();

        Orders order = OrdersData.getOrderData(scooterColor);

        ValidatableResponse response = OrdersMethods.postCreateOrders(order)
                .statusCode(201)
                .assertThat().body("track", notNullValue());

        track = response.extract().path("track").toString();
    }

    @After
    @Description("Создание заказа.Постусловие для теста.Завершить заказ")
    public void cleanUp() {
        String orderId = OrdersMethods.getOrderInfo(track).extract().path("order.id").toString();
        OrdersMethods.finishOrder(orderId);
    }

}
