package com.example.modeso_mmac.rxjavaexample.services;

import com.example.modeso_mmac.rxjavaexample.api.SearchUsersResponse;
import com.example.modeso_mmac.rxjavaexample.datamodel.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Belal Mohamed on 7/20/16.
 * www.modeso.ch
 */
public abstract class CoreSubscriptionLayer {

    /**
     * This Method is Responsible for return a ready To subscribe Observable for searching list of
     * getUsers.
     *
     * @param bindObservable The observable binding of the search editText.
     * @return Observable.
     */
    public static Subscription[] searchUsers(Observable<CharSequence> bindObservable, Observer<List<User>> observer, Realm realm) {
        List<Subscription> subscriptions = new ArrayList<>();
        UserClient userClient = RetrofitServiceGenerator.createService(UserClient.class);

        Observable<String> basObservable = bindObservable
                .map(s -> s != null && s.length() > 0 ? s.toString() : "_")
                .debounce(500, TimeUnit.MILLISECONDS);


        Observable<List<User>> apiObservable = basObservable
                .flatMap(userClient::getUsers)
                .observeOn(AndroidSchedulers.mainThread())
                .map(SearchUsersResponse::getUsers)
                .doOnNext(s -> realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(s)));


        Observable<List<User>> usersObservable =
                basObservable.observeOn(AndroidSchedulers.mainThread())
                        .flatMap(s -> UserRealmDataService.searchLocalUsers(realm, s));

        Observable<List<User>> compObservable = Observable.merge(apiObservable, usersObservable);

        Subscription subscription = compObservable
                .cache()
                .subscribe(observer);
        subscriptions.add(subscription);

        return subscriptions.toArray(new Subscription[subscriptions.size()]);
    }

}
