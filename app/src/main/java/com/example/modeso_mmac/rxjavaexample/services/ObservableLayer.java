package com.example.modeso_mmac.rxjavaexample.services;

import com.example.modeso_mmac.rxjavaexample.api.SearchUsersResponse;
import com.example.modeso_mmac.rxjavaexample.datamodel.User;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Belal Mohamed on 7/20/16.
 * www.modeso.ch
 */
public abstract class ObservableLayer {

    /**
     * This Method is Responsible for return a ready To subscribe Observable for searching list of
     * getUsers.
     *
     * @param bindObservable The observable binding of the search editText.
     * @return Observable.
     */
    public static Subscription searchUsers(Observable<CharSequence> bindObservable, Observer<List<User>> observer) {
        UserClient userClient = RetrofitServiceGenerator.createService(UserClient.class);

        return bindObservable
                .subscribeOn(AndroidSchedulers.mainThread())
                .map(s -> s != null && s.length() > 0 ? s.toString() : "_")
                .observeOn(Schedulers.io())
                .flatMap(userClient::getUsers)
                .debounce(300, TimeUnit.MILLISECONDS)
                .map(SearchUsersResponse::getUsers)
                .cache()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


}
