package org.example.command;

import org.example.service.DataInit;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class ListCommand implements Consumer<String> {
    @Override
    public void accept(String command) {
        System.out.println("\n<< Список контактов >>  Всего " + DataInit.listOfContacts.size() + " зап.");
        DataInit.listOfContacts.forEach((k, contacts) -> {
            System.out.println(contacts.getFullName() + " | " + contacts.getPhoneNumber() + " | " + contacts.getEmail());
        });
    }
}
