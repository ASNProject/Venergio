package com.asnproject.venergio;

import com.google.firebase.database.IgnoreExtraProperties;
import java.io.Serializable;

public class Requests implements Serializable {

    private String username;
    private String email;
    private String password;

    public Requests(){

    }

    public Requests(String username, String email, String password ){
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString(){
        return " "+username+"\n"+
                " "+email+"\n"+
                " "+password;
    }

}
