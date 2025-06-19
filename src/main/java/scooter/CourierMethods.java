package scooter;

import io.restassured.response.ValidatableResponse;
import scooter.pojo.Courier;
import scooter.pojo.CourierLogin;

import static scooter.SetUp.baseSpec;

public class CourierMethods {

    public ValidatableResponse postCreateCourier(Courier courier) {
        return baseSpec
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .log().all()
                .post("/api/v1/courier")
                .then()
                .log().all();
    }

    public static ValidatableResponse postCourierLogin(CourierLogin courierLogin) {
        return baseSpec
                .header("Content-type", "application/json")
                .body(courierLogin)
                .when()
                .log().all()
                .post("/api/v1/courier/login")
                .then()
                .log().all();
    }

    public static ValidatableResponse deleteCourier(String courierId) {
        return baseSpec
                .header("Content-type", "application/json")
                .when()
                .log().all()
                .delete("/api/v1/courier/" + courierId)
                .then()
                .log().all();
    }
}
