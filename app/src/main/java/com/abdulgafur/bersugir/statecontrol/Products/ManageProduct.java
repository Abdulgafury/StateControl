package com.abdulgafur.bersugir.statecontrol.Products;


public class ManageProduct {
    private int id;
    private String name;
    private String status;
    private String state;
    private String button1;
    private String button2;
    private String token;
    private String uid;

    public ManageProduct(int id, String name, String status, String state, String button1, String button2, String token, String uid){
        this.id = id;
        this.name = name;
        this.status = status;
        this.state = state;
        this.button1 = button1;
        this.button2 = button2;
        this.uid = uid;
        this.token = token;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public void setId(int id) {
        this.id = id;
    }

    public String getButton1() {
        return button1;
    }

    public void setButton1(String button1) {
        this.button1 = button1;
    }

    public String getButton2() {
        return button2;
    }

    public void setButton2(String button2) {
        this.button2 = button2;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
