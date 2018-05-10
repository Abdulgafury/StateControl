package com.abdulgafur.bersugir.statecontrol;



public class User {


    private String token;

    User(String token) {
        this.token = token;

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
