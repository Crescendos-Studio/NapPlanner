package com.example.naplanner.model;

public class UserModel {
    private String mail;
    private String username;
    private Boolean isStudent = false;

    public UserModel() {
    }

    public UserModel(String mail, String username, Boolean isStudent) {
        this.mail = mail;
        this.username = username;
        this.isStudent = isStudent;
    }

    public Boolean getStudent() {
        return isStudent;
    }

    public void setStudent(Boolean student) {
        isStudent = student;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
