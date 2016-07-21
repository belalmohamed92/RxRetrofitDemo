package com.example.modeso_mmac.rxjavaexample.base;

import android.support.annotation.NonNull;

import rx.Observer;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Belal Mohamed on 7/20/16.
 * www.modeso.ch
 */
public abstract class BaseViewModel<T> implements Observer<T> {

    private CompositeSubscription mCompositeSubscription;

    /**
     * This method should be called in the onPause of the Fragment or Activity.
     */
    public void onPause() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    /**
     * This method should b called in the onResume of the Fragment or Activity.
     */
    public void onResume() {
        mCompositeSubscription = new CompositeSubscription();
    }

    /**
     * This method is Responsible for adding the subscription to the CompositeSubscription for later
     * release.
     *
     * @param subscriptions Type Subscription , the Subscription(s) needs to be added to the CompositeSubscription.
     * @throws IllegalStateException When the CompositeSubscription is null (The ViewModel is not resumed)
     */
    public void addSubscription(@NonNull Subscription... subscriptions) throws IllegalStateException {
        if (mCompositeSubscription != null && !mCompositeSubscription.isUnsubscribed()) {
            for (Subscription subscription : subscriptions) {
                mCompositeSubscription.add(subscription);
            }
        } else {
            throw new IllegalStateException("Make sure to Resume the ViewModel before using it's public methods");
        }
    }

}
