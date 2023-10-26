package org.example.repository;

import org.example.model.Contacts;

import java.util.List;

public interface WorkingWithContactsInterface {
    List<Contacts> getFromFile(String fileName);

    void SaveToFile();

    void AddContact(Contacts contacts);

    void DeleteContact(String email);

    Contacts parseLine(String line);
}
