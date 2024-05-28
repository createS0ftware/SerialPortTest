package uk.co.ht.serialporttest.ui.gbserial;

import android.content.Context;
import android.serialport.YySerialPort;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.lomoment.serialportsdk.VendingMachineCode;
import com.lomoment.serialportsdk.VendingMachineKey;
import com.lomoment.serialportsdk.VendingMachineMananger;
import com.lomoment.serialportsdk.utils.VendingMachineDevicesUtils;

import java.util.HashMap;
import java.util.Map;

import uk.co.ht.serialporttest.R;

public class GBSerialViewModel extends ViewModel {
    private final MutableLiveData<String> statusText = new MutableLiveData<>();
    private final StringBuilder textBuilder = new StringBuilder();

    public LiveData<String> getText() {
        return statusText;
    }


    private final Map<String, Object> requestMap = new HashMap<>();


    public void addText(String newText) {
        textBuilder.append(newText).append("\n");
        statusText.setValue(textBuilder.toString());
    }


    private void createSerialPort(final Context context, String path, int baudrate) {
        VendingMachineMananger serialPort = new VendingMachineMananger(context);
        addText("Vending Machine Manager initialised");
        YySerialPort.setSuPath(VendingMachineDevicesUtils.getSuPathFromDevices());
        serialPort.init(path, baudrate);
        addText("Serial Port initialised");

        VendingMachineMananger.isShowLog = true;

        serialPort.setVendingmachineResponseListener((machine, operation, state, seq, args) -> {
            if (requestMap.containsKey(seq)) {
                boolean removeSeq = true;
                //splicing result

                //deliver operation
                int status = -1;
                if (operation == VendingMachineKey.OP_DELIVER) {
                    addText("Operation => Deliver");

                    if (state == VendingMachineCode.STATE_SUCCESS) {
                        if (args[0] == VendingMachineCode.DELIVER_EMBODY_SUCCESS) {
                            status = args[0];
                            addText("Success " + args[0]);
                        } else {
                            status = args[1];
                            addText("Success 1=" + context.getString(R.string.explain_code) + " " + args[1]);
                        }
                    } else if (state == VendingMachineCode.STATE_FAIL) {
                        status = args[1];
                        addText("Failed2=" + context.getString(R.string.explain_code) + " " + args[1]);
                    }
                } else if (operation == VendingMachineKey.OP_LIFT_STORY_HEIGHT_SETTING_ALL
                        || operation == VendingMachineKey.OP_LIFT_STORY_HEIGHT_QUERY
                        || operation == VendingMachineKey.OP_LIFT_STORY_HEIGHT_RESET) {
                    addText("OPLIFT");
                } else {
                    if (state == VendingMachineCode.STATE_SUCCESS) {
                        addText("General OPLIFT");
                    } else {
                        addText("General Fail");
                    }
                }
            } else {
                addText("operation not found");
            }
        });


        serialPort.setMachineQueryResponseListener(list -> {
            if (list != null && !list.isEmpty()) {
                addText("List is empty. Serial port hasn't returned any data");
            } else {
                addText("Found " + list.size() + " items of machine data");
            }
        });

        if (serialPort == null) {
            addText("Serial port could not be initialised");
        }
        serialPort.sendQueryMachineMsg();
    }
}