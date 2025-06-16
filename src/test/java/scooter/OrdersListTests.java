package scooter;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import scooter.pojo.Courier;
import scooter.pojo.CourierLogin;
import scooter.pojo.Orders;
import scooter.testData.CourierData;
import scooter.testData.OrdersData;

import java.util.ArrayList;

import static org.hamcrest.Matchers.notNullValue;

public class OrdersListTests extends SetUp {

    private String courierId;
    private String orderId;
    private Courier courier;
    private String track;

    @Before
    @Description("Лист заказов.Предусловия для выполнения теста.Создать курьера.Авторизоваться.Создать заказ.Принять заказ")
    public void setUp() {
        postCreateCourier();

        postCourierLogin();

        postCreateOrder();

        getOrderInfo();

        orderAccept();
    }

    @Test
    @Description("Лист заказов.Получить лист заказов курьера")
    public void ordersListTest() {
        getOrdersList()
                .statusCode(200)
                .assertThat().body("orders", notNullValue());
    }

    @After
    @Description("Лист заказов.Постусловие для теста.Завершить заказ.Удалить курьера")
    public void cleanUp() {
        putFinishOrder();
        deleteCourier();
    }

    @Step
    @Description("Создать курьера")
    public void postCreateCourier() {
        courier = new CourierData().getCourierData("courierAllData");
        new CourierMethods().postCreateCourier(courier);
    }

    @Step
    @Description("Авторизовать курьера")
    public void postCourierLogin() {
        CourierLogin courierLogin = new CourierLogin(courier.getLogin(), courier.getPassword());
        courierId = CourierMethods.postCourierLogin(courierLogin).extract().path("id").toString();
    }

    @Step
    @Description("Создать заказ")
    public void postCreateOrder() {
        Orders order = OrdersData.getOrderData(new ArrayList<>());
        track = OrdersMethods.postCreateOrders(order).extract().path("track").toString();
    }

    @Step
    @Description("Получить id заказа")
    public void getOrderInfo() {
        orderId = OrdersMethods.getOrderInfo(track).extract().path("order.id").toString();
    }

    @Step
    @Description("Принять заказ")
    public void orderAccept() {
        OrdersMethods.putOrdersAccept(orderId, courierId);
    }

    @Step
    @Description("Получить лист заказов")
    public ValidatableResponse getOrdersList() {
        return OrdersMethods.getOrderList(courierId);
    }

    @Step
    @Description("Завершить заказ")
    public void putFinishOrder() {
        OrdersMethods.finishOrder(orderId).statusCode(200);
    }

    @Step
    @Description("Удалить курьера")
    public void deleteCourier() {
        CourierMethods.deleteCourier(courierId).statusCode(200);
    }
}
