package scooter;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import scooter.pojo.Orders;
import scooter.testData.ColorArrayData;
import scooter.testData.OrdersData;

import static org.hamcrest.Matchers.*;

public class OrderTests extends SetUp {

    private String track;

    @Test
    @DisplayName("Создание заказа.Чёрный цвет")
    @Description("Создать заказ с чёрным цветом самоката")
    public void createOrderWithBlackColor() {
        ValidatableResponse response = createOrder("black")
                .statusCode(201)
                .assertThat().body("track", notNullValue());

        track = response.extract().path("track").toString();
    }

    @Test
    @DisplayName("Создание заказа.Серый цвет")
    @Description("Создать заказ с серым цветом самоката")
    public void createOrderWithGrayColor() {
        ValidatableResponse response = createOrder("gray")
                .statusCode(201)
                .assertThat().body("track", notNullValue());

        track = response.extract().path("track").toString();
    }

    @Test
    @DisplayName("Создание заказа.Любой цвет")
    @Description("Создать заказ с чёрным или серым цветом самоката")
    public void createOrderWithAllColors() {
        ValidatableResponse response = createOrder("all")
                .statusCode(201)
                .assertThat().body("track", notNullValue());

        track = response.extract().path("track").toString();
    }

    @Test
    @DisplayName("Создание заказа.Без цвета")
    @Description("Создать заказ без передачи цвета самоката")
    public void createOrderWithoutColors() {
        ValidatableResponse response = createOrder("null")
                .statusCode(201)
                .assertThat().body("track", notNullValue());

        track = response.extract().path("track").toString();
    }

    @After
    @Description("Постусловие.Завершить заказ")
    public void cleanUp() {
        finishOrder();
    }

    @Step("Создать заказ")
    private ValidatableResponse createOrder(String colorConfig) {
        Orders order = OrdersData.getOrderData(new ColorArrayData().getColorData(colorConfig));
        return OrdersMethods.postCreateOrders(order);
    }

    @Step("Завершить заказ")
    private void finishOrder() {
        String orderId = OrdersMethods.getOrderInfo(track).statusCode(200).extract().path("order.id").toString();
        OrdersMethods.finishOrder(orderId);
    }

}
