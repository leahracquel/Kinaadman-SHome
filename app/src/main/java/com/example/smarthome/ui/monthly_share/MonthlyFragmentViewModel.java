package com.example.smarthome.ui.monthly_share;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MonthlyFragmentViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MonthlyFragmentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is share fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}