/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package com.hp.mss.print.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

	private static final String TAG = SQLiteHandler.class.getSimpleName();
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "remote_print";
	private static final String TABLE_USER = "user";

	// Login Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_KEY = "key";
	private static final String KEY_DEVICE_ID = "device_id";
	private static final String KEY_EXPIRE_AT = "expire_at";
	private static final String KEY_CREATED_AT = "created_at";

	private static final String TABLE_PRODUCT = "product";
	private static final String KEY_NAME = "name";
	private static final String KEY_UNIT = "unit";

	SQLiteDatabase db;

	public SQLiteHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {

		this.db = db;

		String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + KEY_KEY + " TEXT,"
				+ KEY_DEVICE_ID + " TEXT UNIQUE," + KEY_EXPIRE_AT + " TEXT,"
				+ KEY_CREATED_AT + " TEXT" + ")";
		db.execSQL(CREATE_LOGIN_TABLE);

		Log.d(TAG, "Database tables created");
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

		// Create tables again
		onCreate(db);
	}

	public void addUser(String key, String deviceId, String expireAt, String createdAt) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_KEY, key);
		values.put(KEY_DEVICE_ID, deviceId);
		values.put(KEY_EXPIRE_AT, expireAt);
		values.put(KEY_CREATED_AT, createdAt);

		// Inserting Row
		long id = db.insert(TABLE_USER, null, values);
		db.close(); // Closing database connection

		Log.d(TAG, "New user inserted into sqlite: " + id);
	}

	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		String selectQuery = "SELECT  * FROM " + TABLE_USER;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			user.put("key", cursor.getString(1));
			user.put("device_id", cursor.getString(2));
			user.put("expire_at", cursor.getString(3));
			user.put("created_at", cursor.getString(4));
		}
		cursor.close();
		db.close();
		// return user
		Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

		return user;
	}

	public void deleteUsers() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_USER, null, null);
		db.close();

		Log.d(TAG, "Deleted all user info from sqlite");
	}

	public void createProduct(){
		String CREATE_PRODUCT_TABLE = "DROP TABLE IF EXISTS " + TABLE_PRODUCT + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ KEY_NAME + " TEXT,"
				+ KEY_UNIT + " TEXT,"
				+ KEY_CREATED_AT + " TEXT" + ")";
		db.execSQL(CREATE_PRODUCT_TABLE);

		Log.d(TAG, "Database tables created");
	}

	public long addProduct(String name, String unit) {
		SQLiteDatabase db = this.getWritableDatabase();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createAt = sdf.format(new Date());

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, name);
		values.put(KEY_UNIT, unit);
		values.put(KEY_CREATED_AT, createAt);

		// Inserting Row
		long id = db.insert(TABLE_PRODUCT, null, values);
		db.close(); // Closing database connection

		Log.d(TAG, "New user inserted into sqlite: " + id);

		return id;
	}

	public HashMap<String, String> getProduct(int id) {
		HashMap<String, String> product = new HashMap<String, String>();
		String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT + " WHERE id=" + id;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			product.put(KEY_ID, cursor.getString(cursor.getColumnIndex(KEY_ID)));
			product.put(KEY_NAME, cursor.getString(cursor.getColumnIndex(KEY_NAME)));
			product.put(KEY_UNIT, cursor.getString(cursor.getColumnIndex(KEY_UNIT)));
			product.put(KEY_CREATED_AT, cursor.getString(cursor.getColumnIndex(KEY_CREATED_AT)));
		}
		cursor.close();
		db.close();
		Log.d(TAG, "Fetching product from Sqlite: " + product.toString());

		return product;
	}
}
