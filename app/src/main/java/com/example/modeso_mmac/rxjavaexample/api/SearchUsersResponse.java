package com.example.modeso_mmac.rxjavaexample.api;

import com.example.modeso_mmac.rxjavaexample.datamodel.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Belal Mohamed on 7/20/16.
 * www.modeso.ch
 */
public class SearchUsersResponse {

    @SerializedName("users")
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
