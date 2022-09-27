package sk.stuba.fei.uim.vsa.pr1b.ui;

import sk.stuba.fei.uim.vsa.pr1b.ui.command.*;

import java.text.ParseException;
import java.util.*;

public class StartApplication extends MenuOptions {

    private final Map<String, Command> commands;

    public StartApplication() {
        this.commands = new HashMap<>();
        commands.put("c", new CreateCommand());
        commands.put("r", new ReadCommand());
        commands.put("u", new UpdateCommand());
        commands.put("d", new DeleteCommand());
        commands.put("end", new EndCommand());
    }

    public void start() throws ParseException {
        while (true) {
            System.out.println(CRUD_MENU);
            String input = KeyboardInput.readString("").trim();
            if ("q".equals(input))
                return;
            if (isCommand(input)) executeCommand(input);
            else
                System.out.println("Input '" + input + "' was not recognised as a known command!");
        }
    }

    private String getCommand(String input) {
        String command;
        if (!input.contains(" ")) {
            command = input.trim();
        } else {
            command = input.substring(0, input.indexOf(' '));
        }
        return command;
    }

    private boolean isCommand(String input) {
        return commands.containsKey(getCommand(input));
    }

    private void executeCommand(String input) throws ParseException {
        String command = getCommand(input);
        commands.get(command).execute();
    }
}
