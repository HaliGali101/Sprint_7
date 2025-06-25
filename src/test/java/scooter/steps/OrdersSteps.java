package scooter.steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import scooter.OrdersMethods;
import scooter.pojo.Orders;
import scooter.testData.OrdersData;

import java.util.ArrayList;

public class OrdersSteps {

    @Step("Создать заказ")
    public static ValidatableResponse postCreateOrder(ArrayList<String> colorsArray) {
        Orders order = OrdersData.getOrderData(colorsArray);
        return OrdersMethods.postCreateOrders(order);
    }

    @Step("Получить id заказа")
    public static ValidatableResponse getOrderInfo(String track) {
        return OrdersMethods.getOrderInfo(track);
    }

    @Step("Принять заказ")
    public static void orderAccept(String orderId, String courierId) {
        OrdersMethods.putOrdersAccept(orderId, courierId);
    }

    @Step("Получить лист заказов")
    public static ValidatableResponse getOrdersList(String courierId) {
        return OrdersMethods.getOrderList(courierId);
    }

    @Step("Завершить заказ")
    public static ValidatableResponse putFinishOrder(String orderId) {
        return OrdersMethods.finishOrder(orderId);
    }

    @Step("Отменить заказ")
    public static ValidatableResponse putOrdersCancel(String track) {
        return OrdersMethods.putOrdersCancel(track);
    }
}
