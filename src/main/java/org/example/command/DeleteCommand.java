package org.example.command;

import lombok.extern.java.Log;
import org.example.model.Contacts;
import org.example.repository.WorkingWithContactsInterface;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.function.Consumer;

@Log
@Component
public class DeleteCommand implements Consumer<String> {
    private final WorkingWithContactsInterface working;

    public DeleteCommand(WorkingWithContactsInterface working) {
        this.working = working;
    }
    @Override
    public void accept(String s) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nВведите email удаляемого контакта: ");
        String email = scanner.nextLine();

        Contacts contacts = new Contacts();
        contacts.setEmail(email);
        working.DeleteContact(email);
    }
}
