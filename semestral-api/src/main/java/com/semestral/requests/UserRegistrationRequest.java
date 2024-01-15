package com.semestral.requests;

import lombok.Getter;
@Getter
public class UserRegistrationRequest {

    private String name;
    private String password;
    private String newNameDemand;
    private String newPasswordDemand;


    public void setName(String name) {
        this.name = name;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public void setNewNameDemand(String newNameDemand) {
        this.newNameDemand = newNameDemand;
    }

    public void setNewPasswordDemand(String newPasswordDemand) {
        this.newPasswordDemand = newPasswordDemand;
    }

}
