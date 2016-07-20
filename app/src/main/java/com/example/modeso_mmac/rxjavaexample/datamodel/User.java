package com.example.modeso_mmac.rxjavaexample.datamodel;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Belal Mohamed on 7/19/16.
 * www.modeso.ch
 */
public class User {
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
}
