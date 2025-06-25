package scooter;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import scooter.pojo.Courier;
import scooter.steps.CourierSteps;
import scooter.testData.CourierData;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTests extends SetUp {

    private Courier courier;

    @Test
    @DisplayName("Авторизация. Успешная авторизация")
    @Description("Успешная авторизация при актуальных параметрах")
    public void courierLogin() {
        CourierSteps.postCourierLogin(courier, "allData")
                .statusCode(200)
                .assertThat().body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация. Не передан логин")
    @Description("Авторизоваться при непереданном логине")
    public void courierLoginWithoutLogin() {
        CourierSteps.postCourierLogin(courier, "withoutLogin")
                .statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация. Не передан пароль")
    @Description("Авторизоваться при непереданном пароле")
    public void courierLoginWithoutPassword() {
        CourierSteps.postCourierLogin(courier, "withoutPassword")
                .statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация. Некорректный логин")
    @Description("Авторизоваться при некорректном логине")
    public void courierLoginWithIncorrectLogin() {
        CourierSteps.postCourierLogin(courier, "incorrectLogin")
                .statusCode(404)
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация. Некорректный пароль")
    @Description("Авторизоваться при некорректном пароле")
    public void courierLoginWithIncorrectPassword() {
        CourierSteps.postCourierLogin(courier, "incorrectPassword")
                .statusCode(404)
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Before
    @Description("Предусловия.Создать курьера")
    public void setUp() {
        courier = new CourierData().getCourierData("courierAllData");
        CourierSteps.postCreateCourier(courier).statusCode(201);
    }

    @After
    @Description("Постусловия.Удаление курьера")
    public void cleanUp() {
        ValidatableResponse courierLoginResponse = CourierSteps.postCourierLogin(courier, "allData");
        String courierId = courierLoginResponse.extract().path("id").toString();
        CourierSteps.deleteCourier(courierId).statusCode(200);
    }

}
