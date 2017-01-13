package com.sdsb.sbtalk;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by thstj on 2017-01-12.
 */

@IgnoreExtraProperties
public class Chat {
    private String userUID;
    private String name;
    private String message;

    public Chat() {
    }

    public Chat(String userUID, String message, String name) {
        this.userUID = userUID;
        this.message = message;
        this.name = name;
    }

    public String getUserUID() {
        return userUID;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }
}
