package com.example.modeso_mmac.rxjavaexample.services;


import com.example.modeso_mmac.rxjavaexample.api.SearchUsersResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Belal Mohamed on 7/19/16.
 * www.modeso.ch
 */
public interface UserClient {

    @GET("users")
    Observable<SearchUsersResponse> getUsers(@Query("search") String searchQuery);
}
