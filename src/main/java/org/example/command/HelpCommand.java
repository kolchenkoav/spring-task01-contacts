package org.example.command;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class HelpCommand implements Consumer<String> {

    @Override
    public void accept(String s) {
        System.out.println("============================================\n" +
                "Консольное приложение \"«Контакты»\"\n" +
                "список вводимых команд:\n" +
                "    list, add, delete, save, help or ?, exit\n" +
                "Параметры запрашиваются ПОСЛЕ ввода комманды\n" +
                "============================================\n" +
                "list:\tВыводит все имеющиеся контакты пользователя в формате \n" +
                "\t\t«Ф. И. О. | Номер телефона | Адрес электронной почты».\t\n" +
                "\n" +
                "add: \tДобавляет новый контакт в список контактов. \n" +
                "\t\tФормат ввода для обработки данных: \n" +
                "\t\tИванов Иван Иванович; +7(909)999-99-00; someEmail@example.example\n" +
                "\t\t\n" +
                "delete:\tУдаляет контакт по email.\t\n" +
                "\n" +
                "save:\tСохраняет имеющиеся контакты в файл\n" +
                "\n" +
                "? или help: Помощь");
    }
}
