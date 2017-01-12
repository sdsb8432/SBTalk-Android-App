package com.sdsb.sbtalk;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by thstj on 2017-01-12.
 */

@IgnoreExtraProperties
public class Room implements Serializable{

    private String uniqueID;

    public Room() {
    }

    public Room(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getUniqueID() {
        return uniqueID;
    }
}
