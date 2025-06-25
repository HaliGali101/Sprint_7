package scooter;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import scooter.steps.OrdersSteps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class OrderTests extends SetUp {

    private String track;

    private final ArrayList<String> colorsArray;
    private final int statusCode;

    public OrderTests(ArrayList<String> colorsArray, int statusCode) {
        this.colorsArray = colorsArray;
        this.statusCode = statusCode;
    }

    @Parameterized.Parameters
    public static Object[][] setUp() {
        return new Object[][] {
                {new ArrayList<>(List.of("BLACK")), 201},
                {new ArrayList<>(List.of("GRAY")), 201},
                {new ArrayList<>(Arrays.asList("BLACK", "GRAY")), 201},
                {new ArrayList<>(List.of()),201}
        };
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Создать заказ с цветом самоката")
    public void createOrderParametersTest() {
        ValidatableResponse response = OrdersSteps.postCreateOrder(colorsArray)
                .statusCode(statusCode)
                .assertThat().body("track", notNullValue());

        track = response.extract().path("track").toString();
    }

    @After
    @Description("Постусловие.Завершить заказ")
    public void cleanUp() {
        OrdersSteps.putOrdersCancel(track).statusCode(200);
    }

}
