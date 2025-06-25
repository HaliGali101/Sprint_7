package scooter.steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import scooter.CourierMethods;
import scooter.pojo.Courier;
import scooter.pojo.CourierLogin;
import scooter.testData.CourierLoginData;

public class CourierSteps {

    @Step("Создать курьера")
    public static ValidatableResponse postCreateCourier(Courier courier) {
        return new CourierMethods().postCreateCourier(courier);
    }

    @Step("Удалить курьера")
    public static ValidatableResponse deleteCourier(String courierId) {
        return CourierMethods.deleteCourier(courierId).statusCode(200);
    }

    @Step("Авторизовать курьера")
    public static ValidatableResponse postCourierLogin(Courier courier, String courierLoginConfig) {
        CourierLogin courierLogin = new CourierLoginData().getCourierData(courier, courierLoginConfig);
        return CourierMethods.postCourierLogin(courierLogin);
    }
}
