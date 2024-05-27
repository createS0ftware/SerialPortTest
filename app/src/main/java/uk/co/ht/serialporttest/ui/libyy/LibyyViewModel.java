package uk.co.ht.serialporttest.ui.libyy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LibyyViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public LibyyViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}