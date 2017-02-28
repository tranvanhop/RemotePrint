package com.hp.mss.print.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SessionManager {
	// LogCat tag
	private static String TAG = SessionManager.class.getSimpleName();

	// Shared Preferences
	SharedPreferences pref;

	Editor editor;
	Context _context;

	// Shared pref mode
	int PRIVATE_MODE = 0;

	// Shared preferences file name
	private static final String PREF_NAME = "RemotePrint";
	
	private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
	private static final String KEY_BLUETOOTH_NAME = "bluetooth_name";
	private static final String KEY_BLUETOOTH_ADDRESS = "bluetooth_address";

	private static final String KEY_COMPANY_NAME = "company_name";
	private static final String KEY_COMPANY_ADDRESS = "company_address";
	private static final String KEY_COMPANY_CASHIER = "company_cashier";
	private static final String KEY_COMPANY_VERSION = "company_version";

	public SessionManager(Context context) {
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}

	public void setLogin(boolean isLoggedIn) {

		editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);

		// commit changes
		editor.commit();

		Log.d(TAG, "User login session modified!");
	}

	public void setDeviceBluetooth(String name, String address) {

		editor.putString(KEY_BLUETOOTH_NAME, name);
		editor.putString(KEY_BLUETOOTH_ADDRESS, address);

		// commit changes
		editor.commit();

		Log.d(TAG, "Add bluetooth device !");
	}

	public String getDeviceBluetoothAddress(){
		return pref.getString(KEY_BLUETOOTH_ADDRESS, "");
	}
	
	public boolean isLoggedIn(){
		return pref.getBoolean(KEY_IS_LOGGED_IN, false);
	}

	public String getKeyCompanyName(){
		return pref.getString(KEY_COMPANY_NAME, "");
	}

	public String getKeyCompanyAddress(){
		return pref.getString(KEY_COMPANY_ADDRESS, "");
	}

	public String getKeyCompanyCashier(){
		return pref.getString(KEY_COMPANY_CASHIER, "");
	}

	public String getKeyCompanyVersion(){
		return pref.getString(KEY_COMPANY_VERSION, "");
	}

	public void setKeyCompanyName(String name) {

		editor.putString(KEY_COMPANY_NAME, name);
		// commit changes
		editor.commit();
	}

	public void setKeyCompanyAddress(String address) {

		editor.putString(KEY_COMPANY_ADDRESS, address);
		// commit changes
		editor.commit();
	}

	public void setKeyCompanyCashier(String cashier) {

		editor.putString(KEY_COMPANY_CASHIER, cashier);
		// commit changes
		editor.commit();
	}

	public void setKeyCompanyVersion(String version) {

		editor.putString(KEY_COMPANY_VERSION, version);
		// commit changes
		editor.commit();
	}
}
