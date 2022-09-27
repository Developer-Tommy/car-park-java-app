package sk.stuba.fei.uim.vsa.pr1b.ui.command;

import sk.stuba.fei.uim.vsa.pr1b.services.CarParkService;
import sk.stuba.fei.uim.vsa.pr1b.entities.*;
import sk.stuba.fei.uim.vsa.pr1b.ui.KeyboardInput;
import sk.stuba.fei.uim.vsa.pr1b.ui.MenuOptions;

import java.util.*;

public class CreateCommand extends MenuOptions implements Command {

    private final CarParkService service;

    public CreateCommand() {
        this.service = new CarParkService();
    }

    @Override
    public void execute() {
        System.out.println(MENU);
        String input = KeyboardInput.readString("").trim();
        switch (input) {
            case "1":
                createCar();
                return;
            case "2":
                createCarPark();
                return;
            case "3":
                createCarParkFloor();
                return;
            case "4":
                createCarType();
                return;
            case "5":
                createParkingSpot();
                return;
            case "6":
                createReservation();
                return;
            case "7":
                createUser();
                return;
            default:
                printNoCommand(input);
        }

    }

    public void createCar() {
        System.out.println("To create a new CAR, enter this parameters separated by a space:");
        System.out.println("<userId> <brand> <model> <colour> <registrationPlate> <carTypeId(OPTIONAL{Default=Gasoline})>");
        List<String> args = getArgs();
        if (args.size() == 5) {
            Car carGasoline = (Car) service.createCar(Long.parseLong(args.get(0)), args.get(1), args.get(2), args.get(3), args.get(4));
            if (carGasoline != null)
                System.out.printf("Car created: %s", carGasoline +"\n");
            else
                System.out.println("Something went wrong while creating Car!");
            return;
        }
        if (args.size() == 6) {
            Car carWithType = (Car) service.createCar(Long.parseLong(args.get(0)), args.get(1), args.get(2), args.get(3), args.get(4), Long.parseLong(args.get(5)));
            if (carWithType != null)
                System.out.printf("Car created: %s", carWithType +"\n");
            else
                System.out.println("Something went wrong while creating Car!");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    public void createCarType() {
        System.out.println("To create a new CAR TYPE, enter this parameter:");
        System.out.println("<name>");
        List<String> args = getArgs();
        if (args.size() == 1) {
            CarType carType = (CarType) service.createCarType(args.get(0));
            if (carType != null)
                System.out.printf("Car Type created: %s", carType +"\n");
            else
                System.out.println("Something went wrong while creating Car Type!");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    public void createCarPark() {
        System.out.println("To create a new CAR PARK, enter this parameters separated by a space.");
        System.out.println("<name> <address> <pricePerHour>");
        List<String> args = getArgs();
        if (args.size() == 3) {
            CarPark carPark = (CarPark) service.createCarPark(args.get(0), args.get(1), Integer.parseInt(args.get(2)));
            if (carPark != null)
                System.out.printf("Car Park created: %s", carPark +"\n");
            else
                System.out.println("Something went wrong while creating Car Park!");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    public void createCarParkFloor() {
        System.out.println("To create a new CAR PARK FLOOR, enter this parameters separated by a space.");
        System.out.println("<carParkId> <floorIdentifier>");
        List<String> args = getArgs();
        if (args.size() == 2) {
            CarParkFloor carParkFloor = (CarParkFloor) service.createCarParkFloor(Long.parseLong(args.get(0)), args.get(1));
            if (carParkFloor != null)
                System.out.printf("Car Park Floor created: %s", carParkFloor +"\n");
            else
                System.out.println("Something went wrong while creating Car Park Floor!");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    public void createParkingSpot() {
        System.out.println("To create a new PARKING SPOT, enter this parameters separated by a space.");
        System.out.println("<carParkId> <floorIdentifier> <spotIdentifier> <carTypeId(OPTIONAL{Default=Gasoline})>");
        List<String> args = getArgs();
        if (args.size() == 3) {
            ParkingSpot parkingSpot = (ParkingSpot) service.createParkingSpot(Long.parseLong(args.get(0)), args.get(1), args.get(2));
            if (parkingSpot != null)
                System.out.printf("Parking Spot created: %s", parkingSpot +"\n");
            else
                System.out.println("Something went wrong while creating Parking Spot!");
            return;
        }
        if (args.size() == 4) {
            ParkingSpot parkingSpot = (ParkingSpot) service.createParkingSpot(Long.parseLong(args.get(0)), args.get(1), args.get(2), Long.parseLong(args.get(3)));
            if (parkingSpot != null)
                System.out.printf("Parking Spot created: %s", parkingSpot +"\n");
            else
                System.out.println("Something went wrong while creating Parking Spot!");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    public void createReservation() {
        System.out.println("To create a new RESERVATION, enter this parameters separated by a space.");
        System.out.println("<parkingSpotId> <carId> ");
        List<String> args = getArgs();
        if (args.size() == 2) {
            Reservation reservation = (Reservation) service.createReservation(Long.parseLong(args.get(0)), Long.parseLong(args.get(1)));
            if (reservation != null)
                System.out.printf("Reservation created: %s", reservation +"\n");
            else
                System.out.println("Something went wrong while creating Reservation!");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    public void createUser() {
        System.out.println("To create a new USER, enter this parameters separated by a space.");
        System.out.println("<firstname> <lastname> <email>");
        List<String> args = getArgs();
        if (args.size() == 3) {
            User user = (User) service.createUser(args.get(0), args.get(1), args.get(2));
            if (user != null)
                System.out.printf("User created: %s", user +"\n");
            else
                System.out.println("Something went wrong while creating User!");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    private void printNoCommand(String params) {
        System.out.println("Unrecognised parameters! Parameters '" + String.join(" ", params) + "' has not been recognised!");
    }

    public List<String> getArgs(){
        String input = null;
        while (input == null) {
            input = KeyboardInput.readString("").trim();
        }
        return new ArrayList<>(Arrays.asList(input.split(" ")));
    }
}
