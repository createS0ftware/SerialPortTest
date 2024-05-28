package uk.co.ht.serialporttest.ui.dkm;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dkm.dkmdevice.DkmDevice;
import com.dkm.dkmdevice.RegCallBack;

public class DkmViewModel extends ViewModel {

    private final MutableLiveData<String> statusText = new MutableLiveData<>();
    private final StringBuilder textBuilder = new StringBuilder();

    public LiveData<String> getText() {
        return statusText;
    }

    public void connect(String machineId) {
        initMachine(machineId);
    }


    public void addText(String newText) {
        textBuilder.append(newText).append("\n");
        statusText.setValue(textBuilder.toString());
    }


    public void initMachine(String machineId) {
        Pair<Integer, String> result= DkmDevice.get().regDevice(machineId,"ttyS4", 115200,  new RegCallBack() {
            @Override
            public void onResult(int code, String msg, com.alibaba.fastjson.JSONObject jsonObject) {
                if(code==1){
                    addText("Machine Found");
                }
                else{
                    addText("Machine not Found");
                }
            }
        });
        if(result.first!=1){
            addText("Machine not Found");
        }
    }
}