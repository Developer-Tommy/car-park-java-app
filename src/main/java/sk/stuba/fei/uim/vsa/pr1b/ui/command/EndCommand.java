package sk.stuba.fei.uim.vsa.pr1b.ui.command;

import sk.stuba.fei.uim.vsa.pr1b.services.CarParkService;
import sk.stuba.fei.uim.vsa.pr1b.entities.*;
import sk.stuba.fei.uim.vsa.pr1b.ui.KeyboardInput;
import sk.stuba.fei.uim.vsa.pr1b.ui.MenuOptions;

import java.util.*;


public class EndCommand extends MenuOptions implements Command {

    private final CarParkService service;

    public EndCommand() {
        this.service = new CarParkService();
    }

    @Override
    public void execute() {
        endReservation();
    }

    private void endReservation() {
        System.out.println("Enter your <UserId>: ");
        List<String> userArg = getArgs();
        User user = (User) service.getUser(Long.parseLong(userArg.get(0)));
        if (user == null) {
            System.out.println("There is no such User.");
            return;
        }
        List<Object> myReservations = service.getMyReservations(user.getId());
        if ((myReservations.isEmpty())) {
            System.out.println("There is no Reservations for you.");
            return;
        }
        System.out.println("List of your reservations: ");
        System.out.printf("%s", myReservations + "\n\n");
        System.out.println("To end Reservation enter <reservationId>: ");
        List<String> args = getArgs();
        if (args.size() == 1) {
            Reservation reservation = (Reservation) service.endReservation(Long.parseLong(args.get(0)));
            if (reservation != null)
                System.out.printf("Reservation ended: %s", reservation +"\n");
            else
                System.out.println("Something went wrong while ending Reservation!");
            return;
        }
        System.out.println("Invalid number of parameters!");
    }

    private List<String> getArgs(){
        String input = null;
        while (input == null) {
            input = KeyboardInput.readString("").trim();
        }
        return new ArrayList<>(Arrays.asList(input.split(" ")));
    }
}