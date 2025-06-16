package scooter;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;

public class SetUp {
    protected static RequestSpecification baseSpec;

    @Before
    public void baseSetUpSpec() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";

        baseSpec = RestAssured.given()
                .filter(new AllureRestAssured()
                        .setRequestTemplate("http-request.ftl")
                        .setResponseTemplate("http-response.ftl"));
    }
}
