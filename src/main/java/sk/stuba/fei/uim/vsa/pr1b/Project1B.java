package sk.stuba.fei.uim.vsa.pr1b;

import sk.stuba.fei.uim.vsa.pr1b.entities.CarPark;
import sk.stuba.fei.uim.vsa.pr1b.services.CarParkService;
import sk.stuba.fei.uim.vsa.pr1b.ui.KeyboardInput;
import sk.stuba.fei.uim.vsa.pr1b.ui.StartApplication;

import java.text.ParseException;

public class Project1B {

    public static void main(String[] args) throws ParseException {
        KeyboardInput.PROMPT_DELIMETER = ">";
        StartApplication startApplication = new StartApplication();
        startApplication.start();

//        CarParkService service = new CarParkService();
//        Object cp = service.createCarPark("Bory", "Ba", 2);
//        Object cp2 = service.createCarPark("Bory2", "Ba2", 2);
//
//        System.out.println(service.getCarPark("Bory"));
//        System.out.println(service.getCarParks());
    }
}
