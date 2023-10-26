package org.example.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Contacts {
    private String fullName;
    private String phoneNumber;
    private String email;

    @Override
    public String toString() {
        return fullName + ";" + phoneNumber + ";" + email;
    }
}
