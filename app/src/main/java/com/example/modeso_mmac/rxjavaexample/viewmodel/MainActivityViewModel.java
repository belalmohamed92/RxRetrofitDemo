package com.example.modeso_mmac.rxjavaexample.viewmodel;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.modeso_mmac.rxjavaexample.api.SearchUsersResponse;
import com.example.modeso_mmac.rxjavaexample.errorhandling.ApiErrorResponse;
import com.example.modeso_mmac.rxjavaexample.base.BaseViewModel;
import com.example.modeso_mmac.rxjavaexample.listeners.ListChangeListener;
import com.example.modeso_mmac.rxjavaexample.services.ObservableLayer;
import com.example.modeso_mmac.rxjavaexample.errorhandling.RetrofitException;


import rx.Observable;

/**
 * Created by Belal Mohamed on 7/19/16.
 * www.modeso.ch
 */
public class MainActivityViewModel extends BaseViewModel<SearchUsersResponse> {

    private static final String TAG = MainActivityViewModel.class.getName();

    private Observable<CharSequence> mSearchEditTextObservable;
    private SearchUsersResponse mSearchUserResponse;
    private ListChangeListener mListChangeListener;


    public MainActivityViewModel(@NonNull Observable<CharSequence> searchEditTextObservable, ListChangeListener listChangeListener) {
        mSearchEditTextObservable = searchEditTextObservable;
        mSearchUserResponse = new SearchUsersResponse();
        mListChangeListener = listChangeListener;
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
    public void onNext(SearchUsersResponse searchUsersResponse) {
        mSearchUserResponse.setUsers(searchUsersResponse.getUsers());
        mListChangeListener.updateAdapter();
    }

    public SearchUsersResponse getSearchUserResponse() {
        return mSearchUserResponse;
    }
}
