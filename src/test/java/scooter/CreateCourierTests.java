package scooter;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import scooter.pojo.Courier;
import scooter.pojo.CourierLogin;
import scooter.testData.CourierData;

import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTests extends SetUp {

    private Courier courier;
    boolean isCreated;
    private String courierId;

    @Test
    @DisplayName("Создание курьера.Успешное создание курьера")
    @Description("Создать курьера передав все параметры")
    public void createCourier() {

        courier = new CourierData().getCourierData("courierAllData");

        postCreateCourier(courier)
                .statusCode(201)
                .assertThat().body("ok", equalTo(true));

        isCreated = true;
    }

    @Test
    @DisplayName("Создание курьера.Создание курьера без логина")
    @Description("Создать курьера без логина")
    public void createCourierWithoutLogin() {

        courier = new CourierData().getCourierData("courierWithoutLogin");

        postCreateCourier(courier)
                .statusCode(400)
                .assertThat().body("code", equalTo(400))
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));

        isCreated = false;
    }

    @Test
    @DisplayName("Создание курьера.Создание курьера без пароля")
    @Description("Создать курьера без пароля")
    public void createCourierWithoutPassword() {

        courier = new CourierData().getCourierData("courierWithoutPassword");

        postCreateCourier(courier)
                .statusCode(400)
                .assertThat().body("code", equalTo(400))
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));

        isCreated = false;
    }

    @Test
    @DisplayName("Создание курьера.Создание курьера без имени")
    @Description("Создать курьера без имени")
    public void createCourierWithoutFirstName() {

        courier = new CourierData().getCourierData("courierWithoutFirstName");

        postCreateCourier(courier)
                .statusCode(201)
                .assertThat().body("ok", equalTo(true));

        isCreated = true;
    }

    @Test()
    @DisplayName("Создание курьера.Дубликат")
    @Description("Создать второго курьера с одинаковыми атрибутами")
    public void createCourierDuplicate() {

        courier = new CourierData().getCourierData("courierWithoutFirstName");

        postCreateCourier(courier)
                .statusCode(201)
                .assertThat().body("ok", equalTo(true));

        new CourierMethods().postCreateCourier(courier)
                .statusCode(409)
                .assertThat().body("message", equalTo("Этот логин уже используется"));

        isCreated = true;
    }

    @After
    @DisplayName("Постусловие")
    public void cleanUp() {
        if (isCreated) {
            CourierLogin courierLogin = new CourierLogin(courier.getLogin(), courier.getPassword());

            courierId = getCourierId(courierLogin)
                    .statusCode(200)
                    .extract().path("id").toString();

            deleteCourier(courierId).statusCode(200);
        }

    }

    @Step("Создать курьера")
    private ValidatableResponse postCreateCourier(Courier courier) {
        return new CourierMethods().postCreateCourier(courier);
    }

    @Step("Получить id курьера")
    private ValidatableResponse getCourierId(CourierLogin courierLogin) {
        return CourierMethods.postCourierLogin(courierLogin);
    }

    @Step("Удалить курьера")
    private ValidatableResponse deleteCourier(String id) {

        return CourierMethods.deleteCourier(id);
    }

}
