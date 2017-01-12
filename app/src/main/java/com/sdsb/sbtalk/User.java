package com.sdsb.sbtalk;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by thstj on 2017-01-11.
 */

@IgnoreExtraProperties
public class User {

    public String uid;
    public String email;
    public String name;

    public User() {

    }

    public User(String uid, String email, String name) {
        this.email = email;
        this.name = name;
        this.uid = uid;
    }
}
