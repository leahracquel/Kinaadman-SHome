package com.example.smarthome.ui.monthlyconsump;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MonthlyGraphViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MonthlyGraphViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}