package scooter.testData;

import com.github.javafaker.Faker;
import scooter.pojo.Orders;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class OrdersData {

    public static Orders getOrderData(ArrayList<String> colorsArray) {
        Faker faker = new Faker();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String address = faker.address().fullAddress();
        int metroStation = faker.number().numberBetween(0, 20);
        String phone = faker.phoneNumber().phoneNumber();
        int rentTime = faker.number().numberBetween(0, 24);
        String deliveryDate = getDate(faker);
        String comment = faker.lorem().sentence();

        return new Orders(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, colorsArray);
    }

    private static String getDate(Faker faker) {
        Date futureDate = faker.date().future(365, TimeUnit.DAYS);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(futureDate);
    }

}
