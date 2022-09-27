package sk.stuba.fei.uim.vsa.pr1b.ui.command;

import sk.stuba.fei.uim.vsa.pr1b.services.CarParkService;
import sk.stuba.fei.uim.vsa.pr1b.entities.*;
import sk.stuba.fei.uim.vsa.pr1b.ui.KeyboardInput;
import sk.stuba.fei.uim.vsa.pr1b.ui.MenuOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReadCommand extends MenuOptions implements Command {

    private final CarParkService service;

    public ReadCommand() {
        this.service = new CarParkService();
    }

    @Override
    public void execute() throws ParseException {
        System.out.println(MENU);
        String input = KeyboardInput.readString("").trim();
        switch (input) {
            case "1":
                System.out.println("Choose how you want to find your Car:");
                System.out.println(
                        "   1       - By CAR ID\n" +
                        "   2       - By USER ID\n" +
                        "   3       - By CAR REGISTRATION PLATE\n");
                input = KeyboardInput.readString("").trim();
                switch (input) {
                    case "1":
                        getCarByCarId();
                        break;
                    case "2":
                        getCarByUserId();
                        break;
                    case "3":
                        getCarByVehicleRegistrationPlate();
                        break;
                }
                return;
            case "2":
                System.out.println("Choose how you want to find your Car Park:");
                System.out.println(
                        "   1       - By CAR PARK ID\n" +
                        "   2       - By CAR PARK NAME\n" +
                        "   3       - List all CAR PARKS\n");
                input = KeyboardInput.readString("").trim();
                switch (input) {
                    case "1":
                        getCarParkById();
                        break;
                    case "2":
                        getCarParkByCarParkName();
                        break;
                    case "3":
                        getAllCarParks();
                        break;
                }
                return;
            case "3":
                System.out.println("Choose how you want to find your Car Park Floor:");
                System.out.println(
                        "   1       - By CAR PARK ID\n" +
                        "   2       - By CAR PARK ID and FLOOR IDENTIFIER\n");
                input = KeyboardInput.readString("").trim();
                switch (input) {
                    case "1":
                        getCarParkFloorByCarParkId();
                        break;
                    case "2":
                        getCarParkFloorByCarParkIdAndFloorId();
                        break;
                }
                return;
            case "4":
                System.out.println("Choose how you want to find your Car Type:");
                System.out.println(
                        "   1       - By CAR TYPE ID\n" +
                        "   2       - By CAR TYPE NAME\n" +
                        "   3       - List all CAR TYPES\n");
                input = KeyboardInput.readString("").trim();
                switch (input) {
                    case "1":
                        getCarTypeByCarTypeId();
                        break;
                    case "2":
                        getCarTypeByCarTypeName();
                        break;
                    case "3":
                        getAllCarTypes();
                        break;
                }
                return;
            case "5":
                System.out.println("Choose how you want to find your Parking Spot:");
                System.out.println(
                        "   1       - By PARKING SPOT ID\n" +
                        "   2       - By CAR PARK ID\n" +
                        "   3       - By CAR PARK ID a FLOOR IDENTIFIER\n" +
                        "   4       - List all available spots by CAR PARK NAME\n" +
                        "   5       - List all occupied spots by CAR PARK NAME\n");
                input = KeyboardInput.readString("").trim();
                switch (input) {
                    case "1":
                        getParkingSpotById();
                        break;
                    case "2":
                        getParkingSpotByCarParkId();
                        break;
                    case "3":
                        getParkingSpotByCarParkIdAndFloorIdentifier();
                        break;
                    case "4":
                        getAvailableParkingSpotsByCarParkName();
                        break;
                    case "5":
                        getOccupiedParkingSpotsByCarParkName();
                        break;
                }
                return;
            case "6":
                System.out.println("Choose how you want to find your Reservation:");
                System.out.println(
                        "   1       - By PARKING SPOT ID a DATE\n" +
                        "   2       - By USER ID\n");
                input = KeyboardInput.readString("").trim();
                switch (input) {
                    case "1":
                        getReservationByParkingSpotIdAndDate();
                        break;
                    case "2":
                        getReservationByUserId();
                        break;
                }
                return;
            case "7":
                System.out.println("Choose how you want to find your User:");
                System.out.println(
                        "   1       - By USER ID\n" +
                        "   2       - By EMAIL\n" +
                        "   3       - List all USERS\n");
                input = KeyboardInput.readString("").trim();
                switch (input) {
                    case "1":
                        getUserById();
                        break;
                    case "2":
                        getUserByEmail();
                        break;
                    case "3":
                        getAllUsers();
                        break;
                }
                return;
            default:
                printNoCommand(input);
        }

    }

    private void getAllCarTypes() {
        List<Object> carTypeList = service.getCarTypes();
        if (!(carTypeList.isEmpty())) {
            System.out.println("List of all Car Types:");
            System.out.printf("%s", carTypeList + "\n");
        }
        else
            System.out.println("There is no Car Type.");
    }

    private void getCarTypeByCarTypeName() {
        System.out.println("Enter <carTypeName>: ");
        List<String> args = getArgs();
        if (args.size() == 1) {
            CarType carType = (CarType) service.getCarType(args.get(0));
            if (carType != null)
                System.out.printf("%s", carType +"\n");
            else
                System.out.println("Car Type can not be found.");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    private void getCarTypeByCarTypeId() {
        System.out.println("Enter <carTypeId>: ");
        List<String> args = getArgs();
        if (args.size() == 1) {
            CarType carType = (CarType) service.getCarType(Long.parseLong(args.get(0)));
            if (carType != null)
                System.out.printf("%s", carType +"\n");
            else
                System.out.println("Car Type can not be found.");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    private void getCarByCarId() {
        System.out.println("Enter <carId>: ");
        List<String> args = getArgs();
        if (args.size() == 1) {
            Car car = (Car) service.getCar(Long.parseLong(args.get(0)));
            if (car != null)
                System.out.printf("%s", car +"\n");
            else
                System.out.println("Car can not be found.");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    private void getCarByUserId() {
        System.out.println("Enter <userId>: ");
        List<String> args = getArgs();
        if (args.size() == 1) {
            List<Object> cars = service.getCars(Long.parseLong(args.get(0)));
            if (!(cars.isEmpty()))
                System.out.printf("%s", cars +"\n");
            else
                System.out.println("User has no car.");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    private void getCarByVehicleRegistrationPlate() {
        System.out.println("Enter <vehicleRegistrationPlate>: ");
        List<String> args = getArgs();
        if (args.size() == 1) {
            Car car = (Car) service.getCar(args.get(0));
            if (car != null)
                System.out.printf("%s", car +"\n");
            else
                System.out.println("Car can not be found.");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    private void getCarParkById() {
        System.out.println("Enter <carParkId>: ");
        List<String> args = getArgs();
        if (args.size() == 1) {
            CarPark carPark = (CarPark) service.getCarPark(Long.parseLong(args.get(0)));
            if (carPark != null)
                System.out.printf("%s", carPark +"\n");
            else
                System.out.println("Car Park can not be found.");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    private void getCarParkByCarParkName() {
        System.out.println("Enter <carParkName>: ");
        List<String> args = getArgs();
        if (args.size() == 1) {
            CarPark carPark = (CarPark) service.getCarPark(args.get(0));
            if (carPark != null)
                System.out.printf("%s", carPark +"\n");
            else
                System.out.println("Car Park can not be found.");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    private void getAllCarParks() {
        List<Object> carParkList = service.getCarParks();
        if (!(carParkList.isEmpty())) {
            System.out.println("List of all Car Parks: ");
            System.out.printf("%s", carParkList + "\n");
        }
        else
            System.out.println("There is no Car Park.");
    }

    private void getCarParkFloorByCarParkId() {
        System.out.println("Enter <carParkId>: ");
        List<String> args = getArgs();
        if (args.size() == 1) {
            List<Object> carParkFloors = service.getCarParkFloors(Long.parseLong(args.get(0)));
            if (!(carParkFloors.isEmpty()))
                System.out.printf("%s", carParkFloors +"\n");
            else
                System.out.println("Car Park has no floor.");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    private void getCarParkFloorByCarParkIdAndFloorId() {
        System.out.println("Enter <carParkId> <floorIdentifier> separated by space: ");
        List<String> args = getArgs();
        if (args.size() == 2) {
            CarParkFloor carParkFloor = (CarParkFloor) service.getCarParkFloor(Long.parseLong(args.get(0)), args.get(1));
            if (carParkFloor != null)
                System.out.printf("%s", carParkFloor +"\n");
            else
                System.out.println("Car Park Floor can not be found.");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    private void getParkingSpotById() {
        System.out.println("Enter <parkingSpotId>: ");
        List<String> args = getArgs();
        if (args.size() == 1) {
            ParkingSpot parkingSpot = (ParkingSpot) service.getParkingSpot(Long.parseLong(args.get(0)));
            if (parkingSpot != null)
                System.out.printf("%s", parkingSpot +"\n");
            else
                System.out.println("Parking Spot can not be found.");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    private void getParkingSpotByCarParkId() {
        System.out.println("Enter <carParkId>: ");
        List<String> args = getArgs();
        if (args.size() == 1) {
            Map<String, List<Object>> parkingSpots = service.getParkingSpots(Long.parseLong(args.get(0)));
            if (!(parkingSpots.isEmpty()))
                System.out.printf("%s", parkingSpots +"\n");
            else
                System.out.println("Car Park has no parking spots.");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    private void getParkingSpotByCarParkIdAndFloorIdentifier() {
        System.out.println("Enter <carParkId> <floorIdentifier> separated by space: ");
        List<String> args = getArgs();
        if (args.size() == 2) {
            List<Object> parkingSpots = service.getParkingSpots(Long.parseLong(args.get(0)), args.get(1));
            if (!(parkingSpots.isEmpty()))
                System.out.printf("%s", parkingSpots +"\n");
            else
                System.out.println("Floor has not parking spots in this Car Park.");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    private void getAvailableParkingSpotsByCarParkName() {
        System.out.println("Enter <carParkName>: ");
        List<String> args = getArgs();
        if (args.size() == 1) {
            Map<String, List<Object>> availableParkingSpots = service.getAvailableParkingSpots(args.get(0));
            if (!(availableParkingSpots.isEmpty()))
                System.out.printf("%s", availableParkingSpots +"\n");
            else
                System.out.println("No available parking spots found.");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    private void getOccupiedParkingSpotsByCarParkName() {
        System.out.println("Enter <carParkName>: ");
        List<String> args = getArgs();
        if (args.size() == 1) {
            Map<String, List<Object>> occupiedParkingSpots = service.getOccupiedParkingSpots(args.get(0));
            if (!(occupiedParkingSpots.isEmpty()))
                System.out.printf("%s", occupiedParkingSpots +"\n");
            else
                System.out.println("No occupied parking spots found.");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    private void getReservationByParkingSpotIdAndDate() throws ParseException {
        System.out.println("Enter <parkingSpotId> <date(Format{DD-MM-YYYY})> separated by space: ");
        List<String> args = getArgs();
        if (args.size() == 2) {
            Date date= new SimpleDateFormat("dd-MM-yyyy").parse(args.get(1));
            List<Object> reservations = service.getReservations(Long.parseLong(args.get(0)), date);
            if (!(reservations.isEmpty()))
                System.out.printf("%s", reservations +"\n");
            else
                System.out.println("No reservations for this date.");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    private void getReservationByUserId() {
        System.out.println("Enter <userId>: ");
        List<String> args = getArgs();
        if (args.size() == 1) {
            List<Object> reservations = service.getMyReservations(Long.parseLong(args.get(0)));
            if (!(reservations.isEmpty()))
                System.out.printf("%s", reservations +"\n");
            else
                System.out.println("User has no reservation.");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    private void getUserById() {
        System.out.println("Enter <userId>: ");
        List<String> args = getArgs();
        if (args.size() == 1) {
            User user = (User) service.getUser(Long.parseLong(args.get(0)));
            if (user != null)
                System.out.printf("%s", user +"\n");
            else
                System.out.println("User can not be found.");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    private void getUserByEmail() {
        System.out.println("Enter <email>: ");
        List<String> args = getArgs();
        if (args.size() == 1) {
            User user = (User) service.getUser(args.get(0));
            if (user != null)
                System.out.printf("%s", user +"\n");
            else
                System.out.println("User can not be found.");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    private void getAllUsers() {
        List<Object> userList = service.getUsers();
        if (!(userList.isEmpty())) {
            System.out.println("List of all Users: ");
            System.out.printf("%s", userList + "\n");
        }
        else
            System.out.println("There is no user.");
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
