package org.example.service;

import org.example.command.*;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

@Component
public class MyCommandHandler {
    private final DataInit dataInit;

    public MyCommandHandler(DataInit dataInit,
                            AddCommand addCommand,
                            DeleteCommand deleteCommand,
                            HelpCommand helpCommand,
                            ListCommand listCommand,
                            SaveCommand saveCommand) {
        this.dataInit = dataInit;
    }

    public void run() {
        Map<String, Consumer<String>> actionsByCommand = dataInit.getActionsByCommand();

        dataInit.initContactsFromFile();
        Scanner scanner = new Scanner(System.in);
        System.out.println("list, add, delete, save, help or ?, exit");
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            if (command.equals("exit")) { break; }
            Consumer<String> action = actionsByCommand.getOrDefault(command, DataInit.InvalidCommandAction);
            action.accept(command);
            System.out.print("\nВведите команду :");
        }
    }
}
