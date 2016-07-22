package com.example.modeso_mmac.rxjavaexample.services;


import com.example.modeso_mmac.rxjavaexample.datamodel.User;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by Belal Mohamed on 7/22/16.
 * www.modeso.ch
 */
public abstract class UserRealmDataService {

    public static Observable<List<User>> searchLocalUsers(Realm realm, String searchQuery) throws IllegalStateException {
        if (!realm.isClosed()) {
            RealmResults<User> users = realm.where(User.class).beginsWith("name", searchQuery).findAll();
            List<User> users1 = realm.copyFromRealm(users);
            return Observable.just(users1);
//            return realm.where(User.class).findAll().asObservable();
        } else {
            throw new IllegalStateException("Realm Instance is closed check your ViewModel life cycle!");
        }
    }
}
