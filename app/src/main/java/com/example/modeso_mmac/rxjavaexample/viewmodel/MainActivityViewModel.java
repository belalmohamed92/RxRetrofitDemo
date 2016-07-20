package com.example.modeso_mmac.rxjavaexample.viewmodel;

import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.modeso_mmac.rxjavaexample.BR;
import com.example.modeso_mmac.rxjavaexample.base.BaseViewModel;
import com.example.modeso_mmac.rxjavaexample.datamodel.User;
import com.example.modeso_mmac.rxjavaexample.services.ObservableLayer;

import java.util.List;

import rx.Observable;

/**
 * Created by Belal Mohamed on 7/19/16.
 * www.modeso.ch
 */
public class MainActivityViewModel extends BaseViewModel<List<User>> {

    private static final String TAG = MainActivityViewModel.class.getName();

    private Observable<CharSequence> mSearchEditTextObservable;

    @Bindable
    private User mUser;


    public MainActivityViewModel(@NonNull Observable<CharSequence> searchEditTextObservable) {
        mSearchEditTextObservable = searchEditTextObservable;
        mUser = new User();
    }

    @Override
    public void onResume() {
        super.onResume();
        bind(mSearchEditTextObservable);
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    private void bind(Observable<CharSequence> searchEditTextBindingObservable) {
        addSubscription(ObservableLayer.searchUsers(searchEditTextBindingObservable, this));
    }

    @Override
    public void onCompleted() {
        Log.d(TAG, "onCompleted");
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        Log.d(TAG, "onError");
    }

    @Override
    public void onNext(List<User> users) {
        Log.d(TAG, String.valueOf(users.size()));
        if (users.size() > 0) {
            mUser = users.get(0);
        } else {
            mUser = new User();
        }

        notifyPropertyChanged(BR.user);
    }

    public User getUser() {
        return mUser;
    }
}
