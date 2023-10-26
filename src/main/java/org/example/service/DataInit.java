package org.example.service;

import org.example.command.*;
import org.example.model.Contacts;
import org.example.repository.WorkingWithContactsInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class DataInit {
    public static Map<String, Contacts> listOfContacts = new HashMap<>();
    @Value("${spring.profiles.active}")
    private String env;
    @Value("${app.initFileName}")
    private String initFileName;
    @Value("${app.saveFileName}")
    private String saveFileName;

    public static final Consumer<String> InvalidCommandAction =
            (command-> System.out.println("Неправильная команда. Введите help or ?"));

    private final WorkingWithContactsInterface working;
    public DataInit(WorkingWithContactsInterface working) {
        this.working = working;
    }

    public Map<String, Consumer<String>> getActionsByCommand() {
        Map<String, Consumer<String>> actionsByCommand = new HashMap<>();
        actionsByCommand.put("add", new AddCommand(working));
        actionsByCommand.put("list", new ListCommand());
        actionsByCommand.put("delete", new DeleteCommand(working));
        actionsByCommand.put("save", new SaveCommand(working));
        actionsByCommand.put("help", new HelpCommand());
        actionsByCommand.put("?", new HelpCommand());
        return actionsByCommand;
    }

    public void initContactsFromFile() {
        System.out.println();
        System.out.println("================================================");
        System.out.println("Приложение «Контакты»");
        System.out.println("Текущий профиль: " + env);
        System.out.println("Файл для инициализации контактов: " + initFileName);
        System.out.println("Файл для сохранения контактов: " + saveFileName);
        System.out.println("================================================");
        if (env.equals("init")) {
            List<Contacts> contactsList = working.getFromFile(initFileName);
            contactsList.forEach(c -> listOfContacts.put(c.getEmail(), c));
        }
    }
}
