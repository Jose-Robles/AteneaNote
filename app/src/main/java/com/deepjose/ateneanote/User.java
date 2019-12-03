package com.deepjose.ateneanote;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String surname;
    private String email;
    private String currentUID;
    private String lastUID;

    public User(String name, String surname, String email, String currentUID) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.currentUID = currentUID;
        this.lastUID = currentUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrentUID() {
        return currentUID;
    }

    public void setCurrentUID(String currentUID) {
        this.currentUID = currentUID;
    }

    public String getLastUID() {
        return lastUID;
    }

    public void setLastUID(String lastUID) {
        this.lastUID = lastUID;
    }
}
