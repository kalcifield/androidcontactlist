package com.learn2crack.recyclerswipeview.model;

public class User {
    private  int id;
    private String name;
    private int phoneNumber;

    public User(int id, String name, int phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}