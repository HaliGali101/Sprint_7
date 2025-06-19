package scooter;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import scooter.pojo.Courier;
import scooter.pojo.CourierLogin;
import scooter.testData.CourierData;
import scooter.testData.CourierLoginData;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTests extends SetUp {

    private Courier courier;

    @Test
    @DisplayName("Авторизация. Успешная авторизация")
    @Description("Успешная авторизация при актуальных параметрах")
    public void courierLogin() {
        postCourierLogin(courier, "allData")
                .statusCode(200)
                .assertThat().body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация. Не передан логин")
    @Description("Авторизоваться при непереданном логине")
    public void courierLoginWithoutLogin() {
        postCourierLogin(courier, "withoutLogin")
                .statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация. Не передан пароль")
    @Description("Авторизоваться при непереданном пароле")
    public void courierLoginWithoutPassword() {
        postCourierLogin(courier, "withoutPassword")
                .statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация. Некорректный логин")
    @Description("Авторизоваться при некорректном логине")
    public void courierLoginWithIncorrectLogin() {
        postCourierLogin(courier, "incorrectLogin")
                .statusCode(404)
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация. Некорректный пароль")
    @Description("Авторизоваться при некорректном пароле")
    public void courierLoginWithIncorrectPassword() {
        postCourierLogin(courier, "incorrectPassword")
                .statusCode(404)
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Before
    @Description("Предусловия.Создать курьера")
    public void setUp() {
        postCreateCourier("courierAllData").statusCode(201);
    }

    @After
    @Description("Постусловия.Удаление курьера")
    public void cleanUp() {
        deleteCourier().statusCode(200);
    }

    @Step("Создать курьера")
    private ValidatableResponse postCreateCourier(String courierConfig) {
        courier = new CourierData().getCourierData(courierConfig);
        return new CourierMethods().postCreateCourier(courier);
    }

    @Step("Авторизовать курьера")
    private ValidatableResponse postCourierLogin(Courier courier, String courierLoginConfig) {
        CourierLogin courierLogin = new CourierLoginData().getCourierData(courier, courierLoginConfig);
        return CourierMethods.postCourierLogin(courierLogin);
    }

    @Step("Удалить курьера")
    private ValidatableResponse deleteCourier() {
        ValidatableResponse courierLoginResponse = postCourierLogin(courier, "allData");
        String courierId = courierLoginResponse.extract().path("id").toString();
        return CourierMethods.deleteCourier(courierId);
    }

}
