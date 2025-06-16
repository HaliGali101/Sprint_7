package scooter;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
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
    @Description("Авторизация.Успешная авторизация")
    public void courierLogin() {
        Allure.step("Успешная авторизация в системе", () -> {
            postCourierLogin(courier, "allData")
                    .statusCode(200)
                    .assertThat().body("id", notNullValue());
        });
    }

    @Test
    @Description("Авторизация.Не передан логин")
    public void courierLoginWithoutLogin() {
        Allure.step("Авторизация.Не передан логин", () -> {
            postCourierLogin(courier, "withoutLogin")
                    .statusCode(400)
                    .assertThat().body("message", equalTo("Недостаточно данных для входа"));
        });
    }

    @Test
    @Description("Авторизация.Не передан пароль")
    public void courierLoginWithoutPassword() {
        Allure.step("Авторизация.Не передан пароль", () -> {
            postCourierLogin(courier, "withoutPassword")
                    .statusCode(400)
                    .assertThat().body("message", equalTo("Недостаточно данных для входа"));
        });
    }

    @Test
    @Description("Авторизация.Некорректный логин")
    public void courierLoginWithIncorrectLogin() {
        Allure.step("Авторизация.Некорректный логин", () -> {
            postCourierLogin(courier, "incorrectLogin")
                    .statusCode(404)
                    .assertThat().body("message", equalTo("Учетная запись не найдена"));
        });
    }

    @Test
    @Description("Авторизация.Некорректный пароль")
    public void courierLoginWithIncorrectPassword() {
        Allure.step("Авторизация.Некорректный пароль", () -> {
            postCourierLogin(courier, "incorrectPassword")
                    .statusCode(404)
                    .assertThat().body("message", equalTo("Учетная запись не найдена"));
        });
    }

    @Before
    @Description("Авторизация.Предусловия для теста.Создать курьера")
    public void setUp() {
        Allure.step("Предусловие теста", () -> {
            postCreateCourier("courierAllData").statusCode(201);
        });
    }

    @After
    @Description("Авторизация.Постусловия для теста.Удаление курьера")
    public void cleanUp() {
        Allure.step("Постусловие. Удаление курьера", () -> {
            deleteCourier().statusCode(200);
        });
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
