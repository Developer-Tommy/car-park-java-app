package sk.stuba.fei.uim.vsa.pr1b.ui.command;

import sk.stuba.fei.uim.vsa.pr1b.services.CarParkService;
import sk.stuba.fei.uim.vsa.pr1b.entities.*;
import sk.stuba.fei.uim.vsa.pr1b.ui.KeyboardInput;
import sk.stuba.fei.uim.vsa.pr1b.ui.MenuOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UpdateCommand extends MenuOptions implements Command {

    private final CarParkService service;

    public UpdateCommand() {
        this.service = new CarParkService();
    }

    @Override
    public void execute() throws ParseException {
        System.out.println(UPDATE_MENU);
        String input = KeyboardInput.readString("").trim();
        switch (input) {
            case "1":
                updateCar();
                return;
            case "2":
                updateCarPark();
                return;
            case "3":
                updateCarParkFloor();
                return;
            case "4":
                updateParkingSpot();
                return;
            case "5":
                updateReservation();
                return;
            case "6":
                updateUser();
                return;
            default:
                printNoCommand(input);
        }

    }

    public void updateCar() {
        System.out.println("Enter your <userId> :");
        List<String> userArg = getArg();
        User user = (User) service.getUser(Long.parseLong(userArg.get(0)));
        if (user == null) {
            System.out.println("No user found.");
            return;
        }
        List<Object> carList = service.getCars(user.getId());
        if ((carList.isEmpty())){
            System.out.println("No cars found.");
            return;
        }
        System.out.println("List of your cars:");
        System.out.printf("%s", carList + "\n");
        System.out.println("To update your Car enter this parameters separated by a space: ");
        System.out.println("<carId> <brand> <model> <colour> <vehicleRegistrationPlate>");
        List<String> arg = getArg();
        if (arg.size() == 5) {
            Car car = new Car();
            car.setId(Long.parseLong(arg.get(0)));
            car.setUser(user);
            car.setBrand(arg.get(1));
            car.setModel(arg.get(2));
            car.setColour(arg.get(3));
            car.setVehicleRegistrationPlate(arg.get(4));
            Car updateCar = (Car) service.updateCar(car);
            if (updateCar != null) {
                System.out.printf("Car updated: %s", updateCar +"\n");
            }
            else
                System.out.println("Something went wrong while updating Car!");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    public void updateCarPark() {
        List<Object> carParkList = service.getCarParks();
        if (carParkList.isEmpty()) {
            System.out.println("No Car Parks found");
            return;
        }
        System.out.println("List of Car Parks: ");
        System.out.printf("%s", carParkList + "\n");
        System.out.println("To update your Car Park enter this parameters separated by a space: ");
        System.out.println("<carParkId> <name> <address> <pricePerHour>");
        List<String> arg = getArg();
        if (arg.size() == 4) {
            CarPark carPark = new CarPark();
            carPark.setId(Long.parseLong(arg.get(0)));
            carPark.setName(arg.get(1));
            carPark.setAddress(arg.get(2));
            carPark.setPricePerHour(Integer.parseInt(arg.get(3)));
            CarPark updateCarPark = (CarPark) service.updateCarPark(carPark);
            if (updateCarPark != null) {
                System.out.printf("Car Park updated: %s", updateCarPark +"\n");
            }
            else
                System.out.println("Something went wrong while updating Car Park!");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    public void updateCarParkFloor() {
        System.out.println("Car Park Floor is not editable.");
    }

    public void updateParkingSpot() {
        System.out.println("Enter <carParkId> <floorIdentifier> separated by a space: ");
        List<String> floorArg = getArg();
        List<Object> parkingSpotList = service.getParkingSpots(Long.parseLong(floorArg.get(0)), floorArg.get(1));
        if (parkingSpotList.isEmpty()) {
            System.out.println("No parking spots found");
            return;
        }
        System.out.println("List of Parking spots: ");
        System.out.printf("%s", parkingSpotList + "\n");
        System.out.println("To update your Parking Spot enter this parameters separated by a space: ");
        System.out.println("<spotId> <spotIdentifier>");
        List<String> arg = getArg();
        if (arg.size() == 2) {
            ParkingSpot parkingSpot = new ParkingSpot();
            parkingSpot.setId(Long.parseLong(arg.get(0)));
            parkingSpot.setCarParkFloor((CarParkFloor) service.getCarParkFloor(Long.parseLong(floorArg.get(0)), floorArg.get(1)));
            parkingSpot.setSpotIdentifier(arg.get(1));
            ParkingSpot updateParkingSpot = (ParkingSpot) service.updateParkingSpot(parkingSpot);
            if (updateParkingSpot != null) {
                System.out.printf("Parking Spot updated: %s", updateParkingSpot +"\n");
            }
            else
                System.out.println("Something went wrong while updating Parking Spot!");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    public void updateReservation() throws ParseException {
        System.out.println("Enter your <userId> :");
        List<String> userArg = getArg();
        List<Object> myReservations = service.getMyReservations(Long.parseLong(userArg.get(0)));
        if (myReservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }
        System.out.println("List of your reservations: ");
        System.out.printf("%s", myReservations + "\n");
        System.out.println("To update your Reservation enter this parameters separated by a space: ");
        System.out.println("<reservationId> <parkingSpotId> <carId> <startTime(Format{DD-MM-YYYY HH:MM:SS})>");
        List<String> arg = getArg();
        if (arg.size() == 5) {
            Reservation reservation = new Reservation();
            Date date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse((arg.get(3) + " " + arg.get(4)));
            reservation.setId(Long.parseLong(arg.get(0)));
            reservation.setStartTime(date);
            reservation.setParkingSpot((ParkingSpot) service.getParkingSpot(Long.parseLong(arg.get(1))));
            reservation.setCar((Car) service.getCar(Long.parseLong(arg.get(2))));
            Reservation updateReservation = (Reservation) service.updateReservation(reservation);
            if (updateReservation != null) {
                System.out.printf("Reservation updated: %s", updateReservation +"\n");
            }
            else
                System.out.println("Something went wrong while updating Reservation!");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    public void updateUser() {
        List<Object> userList = service.getUsers();
        if (userList.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
        System.out.println("List of users: ");
        System.out.printf("%s", userList + "\n");
        System.out.println("To update your User enter this parameters separated by a space: ");
        System.out.println("<userId> <firstname> <lastname> <email>");
        List<String> arg = getArg();
        if (arg.size() == 4) {
            User user = new User();
            user.setId(Long.parseLong(arg.get(0)));
            user.setFirstname(arg.get(1));
            user.setLastname(arg.get(2));
            user.setEmail(arg.get(3));
            User updateUser = (User) service.updateUser(user);
            if (updateUser != null) {
                System.out.printf("User updated: %s", updateUser +"\n");
            }
            else
                System.out.println("Something went wrong while updating User!");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    private void printNoCommand(String params) {
        System.out.println("Unrecognised parameters! Parameters '" + String.join(" ", params) + "' has not been recognised!");
    }

    public List<String> getArg(){
        String input = null;
        while (input == null) {
            input = KeyboardInput.readString("").trim();
        }
        return new ArrayList<>(Arrays.asList(input.split(" ")));
    }
}
