/*
 * Hewlett-Packard Company
 * All rights reserved.
 *
 * This file, its contents, concepts, methods, behavior, and operation
 * (collectively the "Software") are protected by trade secret, patent,
 * and copyright laws. The use of the Software is governed by a license
 * agreement. Disclosure of the Software to third parties, in any form,
 * in whole or in part, is expressly prohibited except as authorized by
 * the license agreement.
 */

package com.hp.mss.print.fragment;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.hp.mss.print.R;
import com.hp.mss.print.adapter.SpinnerBlueToothAdapter;
import com.hp.mss.print.helper.P25ConnectionException;
import com.hp.mss.print.helper.P25Connector;
import com.hp.mss.print.helper.SessionManager;
import com.hp.mss.print.helper.TAG;
import com.hp.mss.print.item.DeviceItem;
import com.hp.mss.print.util.Util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

public class TabFragmentSystemPrintLayout extends Fragment {

    private Button btnConnect;
    private Button btnEnable;
    private Button btnPrint;
    private Button btnScan;
    private Spinner spDevice;
    private ImageView imgPreview;
    private EditText edtPrint;
    DeviceItem[] deviceItems;
    SpinnerBlueToothAdapter spinnerAdapter = null;

    private ProgressDialog mProgressDlg;
    private ProgressDialog mConnectingDlg;

    private BluetoothAdapter mBluetoothAdapter;

    private P25Connector mConnector;

    private ArrayList<BluetoothDevice> mDeviceList = new ArrayList<BluetoothDevice>();

    private static String textPrint = "Trần Văn Hợp";

    private SessionManager session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_system_print_layout, container, false);

        Intent i = getActivity().getIntent();
        Bundle b = i.getExtras();
        if(b != null)
            textPrint = b.getString(TAG.TEXT_PRINT);

        session = new SessionManager(getActivity());

        btnScan = (Button) inflatedView.findViewById(R.id.btnScan);
        btnConnect = (Button) inflatedView.findViewById(R.id.btnConnect);
        btnEnable = (Button) inflatedView.findViewById(R.id.btnEnable);
        btnPrint = (Button) inflatedView.findViewById(R.id.btnPrint);
        spDevice = (Spinner) inflatedView.findViewById(R.id.spDevice);
        imgPreview = (ImageView) inflatedView.findViewById(R.id.imgPreview);
        edtPrint = (EditText) inflatedView.findViewById(R.id.edtPrint);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            showUnsupported();
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                showDisabled();
            } else {
                showEnabled();

                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

                if (pairedDevices != null) {
                    mDeviceList.addAll(pairedDevices);
                    updateDeviceList();
                }
            }

            mProgressDlg = new ProgressDialog(getContext());

            mProgressDlg.setMessage("Scanning...");
            mProgressDlg.setCancelable(false);
            mProgressDlg.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    mBluetoothAdapter.cancelDiscovery();
                }
            });

            mConnectingDlg = new ProgressDialog(getContext());

            mConnectingDlg.setMessage("Connecting...");
            mConnectingDlg.setCancelable(false);

            mConnector = new P25Connector(new P25Connector.P25ConnectionListener() {

                @Override
                public void onStartConnecting() {
                    mConnectingDlg.show();
                }

                @Override
                public void onConnectionSuccess() {
                    mConnectingDlg.dismiss();
                    showConnected();
                }

                @Override
                public void onConnectionFailed(String error) {
                    mConnectingDlg.dismiss();
                }

                @Override
                public void onConnectionCancelled() {
                    mConnectingDlg.dismiss();
                }

                @Override
                public void onDisconnected() {
                    showDisconnected();
                }
            });

            btnScan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBluetoothAdapter.startDiscovery();
                }
            });

            //enable bluetooth
            btnEnable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, 1000);
                }
            });

            //connect/disconnect
            btnConnect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    connect(true);
                }
            });

            //print text
            btnPrint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
//                    InputTextDialog dialog = new InputTextDialog(getActivity(), new InputTextDialog.OnListener() {
//                        @Override
//                        public void onPrintClick(String text) {
//                            printText(text);
//                        }
//                    });
//
//                    dialog.show();
                    textPrint = edtPrint.getText().toString();
                    if(textPrint.length() > 0) {
                        printText(textPrint);
//                        Bitmap b = Util.textAsBitmap(textPrint, 32f, Color.BLACK);
//                        imgPreview.setImageBitmap(b);
//                        printPhoto(b);
                    }
                }
            });
        }

        IntentFilter filter = new IntentFilter();

        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);

        getActivity().registerReceiver(mReceiver, filter);

        return inflatedView;
    }

    @Override
    public void onPause() {
        if (mBluetoothAdapter != null) {
            if (mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }
        }

//        if (mConnector != null) {
//            try {
//                mConnector.disconnect();
//            } catch (P25ConnectionException e) {
//                e.printStackTrace();
//            }
//        }

        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();

        String address = session.getDeviceBluetoothAddress();
        if(address.length() > 0)
        {
            BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
            if(device!= null & mDeviceList != null){

                if(mDeviceList.contains(device)){
                    mDeviceList.remove(device);
                }
                mDeviceList.add(0, device);
                updateDeviceList();

                connect(false);
            }
        }
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(mReceiver);

        super.onDestroy();
    }

    private DeviceItem[] getDeviceItems(ArrayList<BluetoothDevice> data) {

        DeviceItem[] list = new DeviceItem[0];
        if(data == null)
            return list;

        int size = data.size();
        list = new DeviceItem[size];

        for (int i = 0; i < size; i++) {
            list[i] = new DeviceItem(data.get(i).getName());
        }

        return list;
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void updateDeviceList() {

        deviceItems = getDeviceItems(mDeviceList);
        spinnerAdapter = new SpinnerBlueToothAdapter(getActivity(), R.layout.spinner_device_item, deviceItems);
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spDevice.setAdapter(spinnerAdapter);

        Drawable spinnerDrawable = spDevice.getBackground().getConstantState().newDrawable();

        spinnerDrawable.setColorFilter(getResources().getColor(R.color.HPFontColorBlue), PorterDuff.Mode.SRC_ATOP);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            spDevice.setBackground(spinnerDrawable);
        }else{
            spDevice.setBackgroundDrawable(spinnerDrawable);
        }

        spDevice.setSelection(0);
    }

    private void showDisabled() {
        showToast("Bluetooth disabled");

        btnEnable.setVisibility(View.VISIBLE);
        btnScan.setVisibility(View.GONE);
        btnConnect.setVisibility(View.GONE);
        btnPrint.setVisibility(View.GONE);
        spDevice.setVisibility(View.GONE);
    }

    private void showEnabled() {
        showToast("Bluetooth enabled");

        btnEnable.setVisibility(View.GONE);
        btnScan.setVisibility(View.VISIBLE);
        btnConnect.setVisibility(View.VISIBLE);
        btnPrint.setVisibility(View.VISIBLE);
        spDevice.setVisibility(View.VISIBLE);
    }

    private void showUnsupported() {
        showToast("Bluetooth is unsupported by this device");

        btnConnect.setEnabled(false);
        btnScan.setEnabled(false);
        btnPrint.setEnabled(false);
        spDevice.setEnabled(false);
    }

    private void showConnected() {
        showToast("Connected");

        btnConnect.setText("Disconnect");
        btnScan.setEnabled(false);
        btnPrint.setEnabled(true);
        spDevice.setEnabled(false);
    }

    private void showDisconnected() {
        showToast("Disconnected");

        btnConnect.setText("Connect");
        btnPrint.setEnabled(false);
        btnScan.setEnabled(true);
        spDevice.setEnabled(true);
    }

    private void connect(boolean isReconnect) {
        if (mDeviceList == null || mDeviceList.size() == 0) {
            return;
        }

        BluetoothDevice device = mDeviceList.get(spDevice.getSelectedItemPosition());
        session.setDeviceBluetooth(device.getName(), device.getAddress());

        if (device.getBondState() == BluetoothDevice.BOND_NONE) {
            try {
                createBond(device);
            } catch (Exception e) {
                showToast("Failed to pair device");

                return;
            }
        }

        try {
            if (!mConnector.isConnected()) {
                mConnector.connect(device);
            } else {
                if(isReconnect)
                {
                    mConnector.disconnect();
                    showDisconnected();
                }
            }
        } catch (P25ConnectionException e) {
            e.printStackTrace();
        }
    }

    private void createBond(BluetoothDevice device) throws Exception {

        try {
            Class<?> cl = Class.forName("android.bluetooth.BluetoothDevice");
            Class<?>[] par = {};

            Method method = cl.getMethod("createBond", par);

            method.invoke(device);

        } catch (Exception e) {
            e.printStackTrace();

            throw e;
        }
    }

    private void sendData(byte[] bytes) {
        try {
            mConnector.sendData(bytes);
        } catch (P25ConnectionException e) {
            e.printStackTrace();
        }
    }

    private void printText(String text) {

        byte[] b = text.getBytes();
        sendData(b);
    }

    private void printBytes(byte[] b) {
        sendData(b);
    }

    private void printPhoto(Bitmap bmp) {
        try {
            if(bmp!=null){
                byte[] command = Util.decodeBitmap(bmp);
                printBytes(command);
            }else{
                Log.e("Print Photo error", "the file isn't exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PrintTools", "the file isn't exists");
        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);

                if (state == BluetoothAdapter.STATE_ON) {
                    showEnabled();
                } else if (state == BluetoothAdapter.STATE_OFF) {
                    showDisabled();
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                mDeviceList = new ArrayList<BluetoothDevice>();

                mProgressDlg.show();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                mProgressDlg.dismiss();

                updateDeviceList();
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if(!mDeviceList.contains(device))
                    mDeviceList.add(device);

                showToast("Found device " + device.getName());
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                final int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);

                if (state == BluetoothDevice.BOND_BONDED) {
                    showToast("Paired");

                    connect(true);
                }
            }
        }
    };
}
