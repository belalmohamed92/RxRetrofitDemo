package com.example.modeso_mmac.rxjavaexample.datamodel;


import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Belal Mohamed on 7/19/16.
 * www.modeso.ch
 */
public class User extends RealmObject {

    @PrimaryKey
    @SerializedName("userId")
    private String id;

    @SerializedName("display_name")
    private String name;

    @SerializedName("level")
    private int level;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
