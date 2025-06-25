package scooter.testData;

import org.apache.commons.lang3.RandomStringUtils;
import scooter.pojo.Courier;

public class CourierData {

    private static String login;
    private static String password;
    private static String firstName;

    public CourierData() {
        login = "Testoviy" + RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        firstName = "Тестович" + RandomStringUtils.randomAlphabetic(10);
    }

    public Courier getCourierData(String conf) {
        switch(conf) {
            case "courierAllData":
                return new Courier(login, password, firstName);
            case "courierWithoutLogin":
                return new Courier("", password, firstName);
            case "courierWithoutPassword":
                return new Courier(login, "", firstName);
            case "courierWithoutFirstName":
                return new Courier(login, password, "");
            default:
                throw new IllegalArgumentException("Не известная конфигурация генерации данных о курьере: " + conf);
        }
    }

}
