package org.example.command;

import org.example.repository.WorkingWithContactsInterface;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.function.Consumer;

@Component
public class AddCommand implements Consumer<String> {
    private final WorkingWithContactsInterface working;

    public AddCommand(WorkingWithContactsInterface working) {
        this.working = working;
    }

    @Override
    public void accept(String command) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nВведите контакт например: Иванов Иван Иванович; +7(909) 999-99-00; someEmail@example.example\n:");
        String line = scanner.nextLine();

        working.AddContact(working.parseLine(line));
    }
}
