package com.hp.mss.print.item;

/**
 * Created by hop on 24/02/2017.
 */

public class BluetoothItem {

    public String getTxtName() {
        return txtName;
    }

    public void setTxtName(String txtName) {
        this.txtName = txtName;
    }

    String txtName;

    public String getTxtAddress() {
        return txtAddress;
    }

    public void setTxtAddress(String txtAddress) {
        this.txtAddress = txtAddress;
    }

    String txtAddress;

    public boolean getCbConnected() {
        return cbConnected;
    }

    public void setCbConnected(boolean cbConnected) {
        this.cbConnected = cbConnected;
    }

    boolean cbConnected;

    public BluetoothItem(String name, String address, boolean isConnected) {
        super();
        this.txtName = name;
        this.txtAddress = address;
        this.cbConnected = isConnected;
    }


}
