package com.example.humungus.safaricare.models;

/**
 * Created by kadan on 3/14/18.
 */

public class SubscriptionModel {
    private String Email;
    private String Sacco;

    public SubscriptionModel(){}

    public SubscriptionModel(String email, String sacco) {
        Email = email;
        Sacco = sacco;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSacco() {
        return Sacco;
    }

    public void setSacco(String sacco) {
        Sacco = sacco;
    }
}
