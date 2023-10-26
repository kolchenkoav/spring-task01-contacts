package org.example.repository;

import lombok.extern.java.Log;
import org.example.model.Contacts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.example.service.DataInit.listOfContacts;

@Log
@Component
public class WorkingWithContacts implements WorkingWithContactsInterface {
    @Value("${app.saveFileName}")
    private String saveFileName;

    @Override
    public List<Contacts> getFromFile(String fileName) {
        int qLines = 0;
        int qLinesError = 0;
        List<Contacts> contactsList= new ArrayList<>();
        Path path = null;
        try {
            path = Paths.get(getClass().getClassLoader()
                    .getResource(fileName).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        Stream<String> lines = null;
        try {
            lines = Files.lines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String line: lines.toList()) {
            Contacts contacts = parseLine(line);
            if (contacts != null) {
                contactsList.add(contacts);
                qLines++;
            } else {
                qLinesError++;
            }
        }

        lines.close();
        if (qLinesError != 0) {
            log.warning("Всего строк в файле: " + qLines + "\n Ошибок : " + qLinesError);
        } else {
            log.info(setGreenMsg(String.format("Загружено %d зап. из файла", qLines)));

        }
        return contactsList;
    }

    @Override
    public Contacts parseLine(String line) {
        Pattern pattern = Pattern.compile(";");
        String[] strings = pattern.split(line,3);
        if (strings.length != 3) {
            log.warning("Строка не содержит необходимые данные");
            return null;
        }

        // валидация
        String fullName;
        if (isValidValue(Pattern.compile("(.*\\D)"), strings[0])) {
            fullName = strings[0].trim();
        } else {
            log.warning("Ошибка: ФИО " + strings[0]);
            return null;
        }

        String phoneNumber;
        if (isValidValue(Pattern.compile("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$"), strings[1])) {
            phoneNumber = strings[1].trim();
        } else {
            log.warning("Ошибка: номер телефона " + strings[1]);
            return null;
        }

        String email;
        if (isValidValue(Pattern.compile("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,7}"), strings[2])) {
            email = strings[2].trim();
        } else {
            log.warning("Ошибка: email " + strings[2]);
            return null;
        }

        Contacts contacts = new Contacts();
        contacts.setFullName(fullName);
        contacts.setPhoneNumber(phoneNumber);
        contacts.setEmail(email);
        return contacts;
    }

    private boolean isValidValue(Pattern pattern, String value) {
        return pattern.matcher(value.trim()).matches();
    }

    @Override
    public void SaveToFile() {
        System.out.println();
        StringBuilder stringBuilder = new StringBuilder();
        if (saveFileName == null) {
            log.warning("Имя файла для сохранения не определено");
            return;
        }
        Path path = Paths.get(saveFileName);
        listOfContacts.forEach((k, contacts)-> {
            stringBuilder.append(contacts).append("\n");
        });

        try {
            Files.write(path, stringBuilder.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info(setGreenMsg("Контакты сохранены в файл: " + saveFileName));
    }

    @Override
    public void AddContact(Contacts contacts) {
        if (contacts == null) {
            return;
        }
        if (listOfContacts.containsKey(contacts.getEmail())) {
            log.warning("Контакт уже существует с таким email:" + contacts.getEmail());
            return;
        }
        listOfContacts.put(contacts.getEmail(), contacts);
        log.info(setGreenMsg("Контакт добавлен в список"));
    }

    private String setGreenMsg(String message) {
        return "\u001B[32m" + message + "\u001B[0m";
    }

    @Override
    public void DeleteContact(String email) {
        var result = listOfContacts.remove(email);
        if (result != null) {
            log.info(setGreenMsg("Контакт "+result.toString()+" удалён"));
        } else {
            log.warning(email + " не найден в списке");
        }
    }
}
