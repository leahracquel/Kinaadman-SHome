package com.example.smarthome.ui.outletenergy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EnergyOutletViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EnergyOutletViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}