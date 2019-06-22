package net.noor.carapplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nsamir on 6/15/2019.
 */
public class Car implements Serializable {

    private int id;
    private String name;
    private String image;

    private String logo;
    private String carClass;
    private String productionYears;

    private int fromYear;
    private int toYear;

    public Car(int id, String name, String image, String logo, String carClass,
               String productionYears, int fromYear, int toYear) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.logo = logo;
        this.carClass = carClass;
        this.productionYears = productionYears;
        this.fromYear = fromYear;
        this.toYear = toYear;
    }


    public static List<Car> getCarList() {

        List<Car> cars = new ArrayList<>();
        cars.add(new Car(1, "Chevorlet 1", "", "", "Sport Car", "1998-1999",
                2000, 2002));

        cars.add(new Car(2, "Chevorlet 2", "", "", "Sport Car", "1998-1999",
                2000, 2002));

        cars.add(new Car(3, "Chevorlet 3", "", "", "Sport Car", "1998-1999",
                2000, 2002));

        cars.add(new Car(4, "Chevorlet 4", "", "", "Sport Car", "1998-1999",
                2000, 2002));

        cars.add(new Car(5, "Chevorlet", "", "", "Sport Car", "1998-1999",
                2000, 2002));

        cars.add(new Car(6, "Chevorlet", "", "", "Sport Car", "1998-1999",
                2000, 2002));

        cars.add(new Car(7, "Chevorlet", "", "", "Sport Car", "1998-1999",
                2000, 2002));

        cars.add(new Car(8, "Chevorlet", "", "", "Sport Car", "1998-1999",
                2000, 2002));

        cars.add(new Car(9, "Chevorlet", "", "", "Sport Car", "1998-1999",
                2000, 2002));

        cars.add(new Car(10, "Chevorlet", "", "", "Sport Car", "1998-1999",
                2000, 2002));

        cars.add(new Car(11, "Chevorlet", "", "", "Sport Car", "1998-1999",
                2000, 2002));

        cars.add(new Car(12, "Chevorlet", "", "", "Sport Car", "1998-1999",
                2000, 2002));

        cars.add(new Car(13, "Chevorlet", "", "", "Sport Car", "1998-1999",
                2000, 2002));

        cars.add(new Car(14, "Chevorlet", "", "", "Sport Car", "1998-1999",
                2000, 2002));

        cars.add(new Car(15, "Chevorlet", "", "", "Sport Car", "1998-1999",
                2000, 2002));

        cars.add(new Car(16, "Chevorlet", "", "", "Sport Car", "1998-1999",
                2000, 2002));

        cars.add(new Car(17, "Chevorlet", "", "", "Sport Car", "1998-1999",
                2000, 2002));

        cars.add(new Car(18, "Chevorlet", "", "", "Sport Car", "1998-1999",
                2000, 2002));

        cars.add(new Car(19, "Chevorlet", "", "", "Sport Car", "1998-1999",
                2000, 2002));

        return cars;

    }

    public int getId() {
        return id;
    }
}
