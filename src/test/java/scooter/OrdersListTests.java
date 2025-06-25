package scooter;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import scooter.pojo.Courier;
import scooter.steps.CourierSteps;
import scooter.steps.OrdersSteps;
import scooter.testData.CourierData;

import java.util.ArrayList;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class OrdersListTests extends SetUp {

    private String courierId;
    private String orderId;
    private final Courier courier;
    private String track;

    public OrdersListTests() {
        this.courier = new CourierData().getCourierData("courierAllData");
    }

    @Before
    @Description("Предусловия для выполнения теста.Создать курьера.Авторизоваться.Создать заказ.Принять заказ")
    public void setUp() {
        CourierSteps.postCreateCourier(courier);
        courierId = CourierSteps.postCourierLogin(courier, "allData")
                .extract().path("id").toString();
        track = OrdersSteps.postCreateOrder(new ArrayList<>()).extract().path("track").toString();
        orderId = OrdersSteps.getOrderInfo(track).extract().path("order.id").toString();
        OrdersSteps.orderAccept(orderId, courierId);
    }

    @Test
    @DisplayName("Получение листа заказов курьера")
    @Description("Получить лист заказов по id курьера")
    public void ordersListTest() {
        OrdersSteps.getOrdersList(courierId)
                .statusCode(200)
                .assertThat().body(matchesJsonSchemaInClasspath("schemas/orderListJsonSchema.json"));
    }

    @After
    @Description("Постусловие.Завершить заказ.Удалить курьера")
    public void cleanUp() {
        OrdersSteps.putFinishOrder(orderId).statusCode(200);
        CourierSteps.deleteCourier(courierId).statusCode(200);
    }

}
