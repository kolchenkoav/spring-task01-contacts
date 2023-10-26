package org.example.command;

import org.example.repository.WorkingWithContactsInterface;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class SaveCommand implements Consumer<String> {
    private final WorkingWithContactsInterface working;

    public SaveCommand(WorkingWithContactsInterface working) {
        this.working = working;
    }

    @Override
    public void accept(String s) {
        working.SaveToFile();
    }
}
