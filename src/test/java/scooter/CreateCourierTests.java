package scooter;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Test;
import scooter.pojo.Courier;
import scooter.pojo.CourierLogin;
import scooter.testData.CourierData;

import static org.hamcrest.Matchers.equalTo;
import static scooter.CourierMethods.*;

public class CreateCourierTests extends SetUp {

    private Courier courier;
    boolean isCreated;
    private String courierId;

    @Test
    @Description("Создание курьера.Успешное создание курьера")
    public void createCourier() {

        courier = new CourierData().getCourierData("courierAllData");

        Allure.step("Создать курьера", () -> {
            new CourierMethods().postCreateCourier(courier)
                    .statusCode(201)
                    .assertThat().body("ok", equalTo(true));
        });

        isCreated = true;
    }

    @Test
    @Description("Создание курьера.Не передан логин")
    public void createCourierWithoutLogin() {

        courier = new CourierData().getCourierData("courierWithoutLogin");

        Allure.step("Создать курьера без логина", () -> {
            new CourierMethods().postCreateCourier(courier)
                    .statusCode(400)
                    .assertThat().body("code", equalTo(400))
                    .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
        });

        isCreated = false;
    }

    @Test
    @Description("Создание курьера.Не передан пароль")
    public void createCourierWithoutPassword() {

        courier = new CourierData().getCourierData("courierWithoutPassword");

        Allure.step("Создать курьера без пароля", () -> {
            new CourierMethods().postCreateCourier(courier)
                    .statusCode(400)
                    .assertThat().body("code", equalTo(400))
                    .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
        });

        isCreated = false;
    }

    @Test
    @Description("Создание курьера.Не передано имя")
    public void createCourierWithoutFirstName() {

        courier = new CourierData().getCourierData("courierWithoutFirstName");

        Allure.step("Создать курьера без имени", () -> {
            new CourierMethods().postCreateCourier(courier)
                    .statusCode(201)
                    .assertThat().body("ok", equalTo(true));
        });

        isCreated = true;
    }

    @Test()
    @Description("Создание курьера.Дубликат курьера")
    public void createCourierDuplicate() {

        courier = new CourierData().getCourierData("courierWithoutFirstName");

        Allure.step("Создать курьера", () -> {
            new CourierMethods().postCreateCourier(courier)
                    .statusCode(201)
                    .assertThat().body("ok", equalTo(true));
        });

        Allure.step("Создать дубликат курьера", () -> {
            new CourierMethods().postCreateCourier(courier)
                    .statusCode(409)
                    .assertThat().body("message", equalTo("Этот логин уже используется"));
        });

        isCreated = true;
    }

    @After
    @Description("Постусловие.Удаление курьера")
    public void cleanUp() {
        Allure.step("Постусловие.Удалить курьера", () -> {
            if (isCreated) {
                CourierLogin getCourier = new CourierLogin(courier.getLogin(), courier.getPassword());

                Allure.step("Получить id курьера", () -> {
                    courierId = postCourierLogin(getCourier).extract().path("id").toString();
                });

                Allure.step("Удалить курьера", () -> {
                    deleteCourier(courierId).statusCode(200);
                });
            }
        });
    }

}
