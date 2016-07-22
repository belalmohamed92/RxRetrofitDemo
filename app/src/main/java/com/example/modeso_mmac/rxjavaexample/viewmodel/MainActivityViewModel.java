package com.example.modeso_mmac.rxjavaexample.viewmodel;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.modeso_mmac.rxjavaexample.datamodel.User;
import com.example.modeso_mmac.rxjavaexample.errorhandling.ApiErrorResponse;
import com.example.modeso_mmac.rxjavaexample.base.BaseViewModel;
import com.example.modeso_mmac.rxjavaexample.listeners.ListChangeListener;
import com.example.modeso_mmac.rxjavaexample.services.CoreSubscriptionLayer;
import com.example.modeso_mmac.rxjavaexample.errorhandling.RetrofitException;


import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import rx.Observable;

/**
 * Created by Belal Mohamed on 7/19/16.
 * www.modeso.ch
 */
public class MainActivityViewModel extends BaseViewModel<List<User>> {

    private static final String TAG = MainActivityViewModel.class.getName();

    private Observable<CharSequence> mSearchEditTextObservable;
    private ListChangeListener mListChangeListener;
    private List<User> mUsers;
    private Realm mRealm;


    public MainActivityViewModel(@NonNull Observable<CharSequence> searchEditTextObservable, ListChangeListener listChangeListener) {
        mSearchEditTextObservable = searchEditTextObservable;
        mUsers = new ArrayList<>();
        mListChangeListener = listChangeListener;
    }

    @Override
    public void onResume() {
        super.onResume();
        mRealm = Realm.getDefaultInstance();
        bind(mSearchEditTextObservable);
    }

    @Override
    public void onPause() {
        super.onPause();
        mRealm.close();
    }


    private void bind(Observable<CharSequence> searchEditTextBindingObservable) {
        if (!mRealm.isClosed()) {
            addSubscription(CoreSubscriptionLayer.searchUsers(searchEditTextBindingObservable, this, mRealm));
        }
    }

    @Override
    public void onCompleted() {
        Log.d(TAG, "onCompleted");
    }

    @Override
    public void onError(Throwable e) {

        Log.d(TAG, "onError Called");
        e.printStackTrace();
        if (e instanceof RetrofitException) {
            String errorMessage = "";
            RetrofitException retrofitException = (RetrofitException) e;
            switch (retrofitException.getKind()) {
                case HTTP:
                    ApiErrorResponse apiErrorResponse = retrofitException.getErrorBodyAs(ApiErrorResponse.class);
                    if (apiErrorResponse != null) {
                        errorMessage = apiErrorResponse.getMessage();
                    } else {
                        errorMessage = "Unknown Error!";
                    }
                    break;
                case NETWORK:
                    errorMessage = "Connection problem";
                    break;
                case UNEXPECTED:
                    errorMessage = "Unknown Error!";
                    break;
            }

            Log.d(TAG, errorMessage);

        }
    }

    @Override
    public void onNext(List<User> users) {
        mUsers.clear();
        mUsers.addAll(users);
        mListChangeListener.updateAdapter();
        Log.d("TESTCALLBACK", "onNext Is Called");
    }

    public List<User> getUsers() {
        return mUsers;
    }
}
