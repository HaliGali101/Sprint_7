package scooter.testData;

import scooter.pojo.Courier;
import scooter.pojo.CourierLogin;

public class CourierLoginData {

    public CourierLogin getCourierData(Courier courier, String conf) {
        switch(conf) {
            case "allData":
                return new CourierLogin(courier.getLogin(), courier.getPassword());
            case "withoutLogin":
                return new CourierLogin("", courier.getPassword());
            case "withoutPassword":
                return new CourierLogin(courier.getLogin(), "");
            case "incorrectLogin":
                return new CourierLogin(courier.getLogin() + "1", courier.getPassword());
            case "incorrectPassword":
                return new CourierLogin(courier.getLogin(), courier.getPassword() + "1");
            default:
                throw new IllegalArgumentException("Не известная конфигурация генерации данных: " + conf);
        }
    }
}
