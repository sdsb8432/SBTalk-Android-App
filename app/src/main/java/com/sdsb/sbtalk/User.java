package com.sdsb.sbtalk;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by thstj on 2017-01-11.
 */

@IgnoreExtraProperties
public class User {

    private String uid;
    private String email;
    private String name;

    public User() {

    }

    public User(String uid, String email, String name) {
        this.email = email;
        this.name = name;
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
