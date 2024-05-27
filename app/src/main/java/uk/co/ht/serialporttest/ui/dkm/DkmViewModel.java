package uk.co.ht.serialporttest.ui.dkm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DkmViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DkmViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}