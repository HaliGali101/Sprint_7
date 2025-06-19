package scooter;

import io.restassured.response.ValidatableResponse;
import scooter.pojo.Orders;

import static scooter.SetUp.baseSpec;

public class OrdersMethods {

    public static ValidatableResponse postCreateOrders(Orders order) {
        return baseSpec
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .log().all()
                .post("/api/v1/orders")
                .then()
                .log().all();
    }

    public static ValidatableResponse getOrderInfo(String track) {
        return baseSpec
                .header("Content-type", "application/json")
                .when()
                .queryParams("t", track)
                .log().all()
                .get("/api/v1/orders/track")
                .then()
                .log().all();
    }

    public static ValidatableResponse finishOrder(String id) {
        return baseSpec
                .header("Content-type", "application/json")
                .when()
                .log().all()
                .put("/api/v1/orders/finish/" + id)
                .then()
                .log().all();
    }

    public static void putOrdersAccept(String orderId, String courierId) {
        baseSpec
                .header("Content-type", "application/json")
                .when()
                .queryParams("courierId", courierId)
                .log().all()
                .put("/api/v1/orders/accept/" + orderId)
                .then()
                .log().all();
    }

    public static ValidatableResponse getOrderList(String courierId) {
        return baseSpec
                .header("Content-type", "application/json")
                .when()
                .queryParams("courierId", courierId)
                .log().all()
                .get("/api/v1/orders")
                .then()
                .log().all();
    }
}
