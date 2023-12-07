package com.semestral.requests;

public class UserRegistrationRequest {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String name;
    private String password;

    public String getNewNameDemand() {
        return newNameDemand;
    }

    public void setNewNameDemand(String newNameDemand) {
        this.newNameDemand = newNameDemand;
    }

    public String getNewPasswordDemand() {
        return newPasswordDemand;
    }

    public void setNewPasswordDemand(String newPasswordDemand) {
        this.newPasswordDemand = newPasswordDemand;
    }

    private String newNameDemand;

    private String newPasswordDemand;
}
