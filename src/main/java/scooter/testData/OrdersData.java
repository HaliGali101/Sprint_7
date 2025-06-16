package scooter.testData;

import scooter.pojo.Orders;

import java.util.ArrayList;

public class OrdersData {

    public static Orders getOrderData(ArrayList<String> colorsArray) {
        String firstName = "Тестовый";
        String lastName = "Тест";
        String address = "Москва, пр.Мира д.120";
        int metroStation = 9;
        String phone = "+7-911-111-11-11";
        int rentTime = 3;
        String deliveryDate = "2025-04-05";
        String comment = "Не звонить после 6!";

        return new Orders(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, colorsArray);
    }

}
