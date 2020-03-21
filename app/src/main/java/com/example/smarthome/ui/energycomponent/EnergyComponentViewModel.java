package com.example.smarthome.ui.energycomponent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EnergyComponentViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EnergyComponentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}