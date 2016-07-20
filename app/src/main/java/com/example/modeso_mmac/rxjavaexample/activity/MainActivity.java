package com.example.modeso_mmac.rxjavaexample.activity;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.example.modeso_mmac.rxjavaexample.R;
import com.example.modeso_mmac.rxjavaexample.databinding.MainActivityBinding;
import com.example.modeso_mmac.rxjavaexample.viewmodel.MainActivityViewModel;
import com.jakewharton.rxbinding.widget.RxTextView;


/**
 * Created by Belal Mohamed on 7/19/16.
 * www.modeso.ch
 */
public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel mViewModel;

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.main_activity);

        EditText searchEditText = (EditText) findViewById(R.id.search_edit_text);
        if (searchEditText != null) {
            mViewModel = new MainActivityViewModel(RxTextView.textChanges(searchEditText));
            binding.setMainActivityViewModel(mViewModel);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mViewModel != null) {
            mViewModel.onResume();
        } else {
            Log.d(TAG, "OnResume Couldn't Resume the ViewModel: ViewModel is null!");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mViewModel != null) {
            mViewModel.onPause();
        } else {
            Log.d(TAG, "OnPause Couldn't Pause the ViewModel: ViewModel is null!");
        }
    }
}
