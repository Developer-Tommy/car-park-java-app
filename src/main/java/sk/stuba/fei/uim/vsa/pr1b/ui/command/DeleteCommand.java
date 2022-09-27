package sk.stuba.fei.uim.vsa.pr1b.ui.command;

import sk.stuba.fei.uim.vsa.pr1b.services.CarParkService;
import sk.stuba.fei.uim.vsa.pr1b.entities.*;
import sk.stuba.fei.uim.vsa.pr1b.ui.KeyboardInput;
import sk.stuba.fei.uim.vsa.pr1b.ui.MenuOptions;

import java.util.*;


public class DeleteCommand extends MenuOptions implements Command {

    private final CarParkService service;

    public DeleteCommand() {
        this.service = new CarParkService();
    }

    @Override
    public void execute() {
        System.out.println(DELETE_MENU);
        String input = KeyboardInput.readString("").trim();
        switch (input) {
            case "1":
                deleteCar();
                return;
            case "2":
                deleteCarPark();
                return;
            case "3":
                deleteCarParkFloor();
                return;
            case "4":
                deleteCarType();
                return;
            case "5":
                deleteParkingSpot();
                return;
            case "6":
                deleteUser();
                return;
            default:
                printNoCommand(input);
        }
    }

    private void deleteCar() {
        System.out.println("Enter your <UserId>: ");
        List<String> userArg = getArgs();
        User user = (User) service.getUser(Long.parseLong(userArg.get(0)));
        if (user == null) {
            System.out.println("There is no such User.");
            return;
        }
        List<Object> carsList = service.getCars(user.getId());
        if ((carsList.isEmpty())) {
            System.out.println("There is no Car.");
            return;
        }
        System.out.println("List of your cars:");
        System.out.printf("%s", carsList + "\n\n");
        System.out.println("To delete Car enter <CarId>: ");
        List<String> args = getArgs();
        if (args.size() == 1) {
            Car car = (Car) service.deleteCar(Long.parseLong(args.get(0)));
            if (car != null)
                System.out.printf("Car deleted: %s", car +"\n");
            else
                System.out.println("Something went wrong while deleting Car!");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    private void deleteCarPark() {
        List<Object> carParksList = service.getCarParks();
        if ((carParksList.isEmpty())) {
            System.out.println("There is no Car Park.");
            return;
        }
        System.out.println("List of Car Parks:");
        System.out.printf("%s", carParksList + "\n\n");
        System.out.println("To delete Car Park enter <CarParkId>: ");
        List<String> args = getArgs();
        if (args.size() == 1) {
            CarPark carPark = (CarPark) service.deleteCarPark(Long.parseLong(args.get(0)));
            if (carPark != null)
                System.out.printf("Car Park deleted: %s", carPark +"\n");
            else
                System.out.println("Something went wrong while deleting Car Park!");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    private void deleteCarType() {
        List<Object> carTypesList = service.getCarTypes();
        if ((carTypesList.isEmpty())) {
            System.out.println("There is no Car Type.");
            return;
        }
        System.out.println("List of Car Types:");
        System.out.printf("%s", carTypesList + "\n\n");
        System.out.println("To delete Car Type enter <CarTypeId>: ");
        List<String> args = getArgs();
        if (args.size() == 1) {
            CarType carType = (CarType) service.deleteCarType(Long.parseLong(args.get(0)));
            if (carType != null)
                System.out.printf("Car Type deleted: %s", carType +"\n");
            else
                System.out.println("Something went wrong while deleting Car Type!");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    private void deleteCarParkFloor() {
        System.out.println("Enter <CarParkId>: ");
        List<String> carParkArg = getArgs();
        List<Object> carParkFloorList = service.getCarParkFloors(Long.parseLong(carParkArg.get(0)));
        if ((carParkFloorList.isEmpty())) {
            System.out.println("There is no such Car ParK.");
            return;
        }
        System.out.println("List of floors:");
        System.out.printf("%s", carParkFloorList + "\n");
        System.out.println("To delete Car Park Floor enter <floorIdentifier>: ");
        List<String> args = getArgs();
        if (args.size() == 1) {
            CarParkFloor carParkFloor = (CarParkFloor) service.deleteCarParkFloor(Long.parseLong(carParkArg.get(0)), args.get(0));
            if (carParkFloor != null)
                System.out.printf("Car Park Floor deleted: %s", carParkFloor +"\n");
            else
                System.out.println("Something went wrong while deleting Car Park Floor!");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    private void deleteParkingSpot() {
        System.out.println("Enter <carParkId> <floorIdentifier> separated by a space: ");
        List<String> floorArg = getArgs();
        List<Object> parkingSpotsList = service.getParkingSpots(Long.parseLong(floorArg.get(0)), floorArg.get(1));
        if ((parkingSpotsList.isEmpty())) {
            System.out.println("There is no such Car Park/Floor.");
            return;
        }
        System.out.println("List of parking spots:");
        System.out.printf("%s", parkingSpotsList + "\n");
        System.out.println("To delete parking spot enter <parkingSpotId>: ");
        List<String> args = getArgs();
        if (args.size() == 1) {
            ParkingSpot parkingSpot = (ParkingSpot) service.deleteParkingSpot(Long.parseLong(args.get(0)));
            if (parkingSpot != null)
                System.out.printf("Parking Spot deleted: %s", parkingSpot +"\n");
            else
                System.out.println("Something went wrong while deleting Parking Spot!");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    private void deleteUser() {
        List<Object> usersList = service.getUsers();
        if ((usersList.isEmpty())) {
            System.out.println("There is no User.");
            return;
        }
        System.out.println("List of Users:");
        System.out.printf("%s", usersList + "\n");
        System.out.println("To delete user enter <userId>: ");
        List<String> args = getArgs();
        if (args.size() == 1) {
            User user = (User) service.deleteUser(Long.parseLong(args.get(0)));
            if (user != null)
                System.out.printf("User deleted: %s", user +"\n");
            else
                System.out.println("Something went wrong while deleting User!");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    private void printNoCommand(String params) {
        System.out.println("Unrecognised parameters! Parameters '" + String.join(" ", params) + "' has not been recognised!");
    }

    private List<String> getArgs(){
        String input = null;
        while (input == null) {
            input = KeyboardInput.readString("").trim();
        }
        return new ArrayList<>(Arrays.asList(input.split(" ")));
    }
}