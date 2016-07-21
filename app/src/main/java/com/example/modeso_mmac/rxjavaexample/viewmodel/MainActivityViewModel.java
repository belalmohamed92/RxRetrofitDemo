package com.example.modeso_mmac.rxjavaexample.viewmodel;

import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.modeso_mmac.rxjavaexample.BR;
import com.example.modeso_mmac.rxjavaexample.errorhandling.ApiErrorResponse;
import com.example.modeso_mmac.rxjavaexample.base.BaseViewModel;
import com.example.modeso_mmac.rxjavaexample.datamodel.User;
import com.example.modeso_mmac.rxjavaexample.services.ObservableLayer;
import com.example.modeso_mmac.rxjavaexample.errorhandling.RetrofitException;

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

        Log.d(TAG, "onError Called");

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
