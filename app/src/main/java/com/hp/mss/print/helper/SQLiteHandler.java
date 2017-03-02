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

import com.hp.mss.print.model.Order;
import com.hp.mss.print.model.OrderProduct;
import com.hp.mss.print.model.Product;
import com.hp.mss.print.model.Unit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SQLiteHandler extends SQLiteOpenHelper {

	private static final String TAG = SQLiteHandler.class.getSimpleName();
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "remote_print";

	private static final String TABLE_UNIT = "unit";
	private static final String TABLE_PRODUCT = "product";
	private static final String TABLE_ORDER = "_order";
	private static final String TABLE_ORDER_PRODUCT = "_order_product";

	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_PRICE = "price";
	private static final String KEY_UNIT_ID = "unit_id";
	private static final String KEY_TAG = "tag";
	private static final String KEY_THUMBNAIL = "thumbnail";
	private static final String KEY_IMAGE = "image";
	private static final String KEY_ORDER_ID = "order_id";
	private static final String KEY_PRODUCT_ID = "product_id";
	private static final String KEY_COUNT = "count";
	private static final String KEY_CREATED_AT = "created_at";

	private static final String DEFAULT_PRODUCT_THUMBNAIL = "/ic_product_thumbnail.png";
	private static final String DEFAULT_PRODUCT_IMAGE = "/ic_product_image.png";
	private static final String DEFAULT_PRODUCT_TAG = "Product";


	public SQLiteHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			createUnit(db);
			createProduct(db);
			createOrder(db);
			createOrderProduct(db);
		}catch (Exception e){

		}
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_UNIT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_PRODUCT);

		onCreate(db);
	}

	private void createUnit(SQLiteDatabase db){
		String CREATE_TABLE_UNIT = "CREATE TABLE " + TABLE_UNIT + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ KEY_NAME + " TEXT,"
				+ KEY_CREATED_AT + " TEXT" + ")";
		db.execSQL(CREATE_TABLE_UNIT);

		Log.d(TAG, "Database tables created");
	}
	private void createProduct(SQLiteDatabase db){

		String CREATE_TABLE_PRODUCT = "CREATE TABLE " + TABLE_PRODUCT + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ KEY_NAME + " TEXT,"
				+ KEY_PRICE + " REAL,"
				+ KEY_UNIT_ID + " INTEGER,"
				+ KEY_TAG + " TEXT,"
				+ KEY_THUMBNAIL + " TEXT,"
				+ KEY_IMAGE + " TEXT,"
				+ KEY_CREATED_AT + " TEXT" + ")";
		db.execSQL(CREATE_TABLE_PRODUCT);

		Log.d(TAG, "Database tables created");
	}
	private void createOrder(SQLiteDatabase db){
		String CREATE_TABLE_ORDER = "CREATE TABLE " + TABLE_ORDER + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ KEY_NAME + " TEXT,"
				+ KEY_CREATED_AT + " TEXT" + ")";
		db.execSQL(CREATE_TABLE_ORDER);

		Log.d(TAG, "Database tables created");
	}
	private void createOrderProduct(SQLiteDatabase db){
		String CREATE_TABLE_ORDER_PRODUCT = "CREATE TABLE " + TABLE_ORDER_PRODUCT + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ KEY_ORDER_ID + " INTEGER,"
				+ KEY_PRODUCT_ID + " INTEGER,"
				+ KEY_COUNT + " INTEGER,"
				+ KEY_CREATED_AT + " TEXT" + ")";
		db.execSQL(CREATE_TABLE_ORDER_PRODUCT);

		Log.d(TAG, "Database tables created");
	}

	public long addUnit(Unit u) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, u.getName());

		long id = db.insert(TABLE_UNIT, null, values);
		db.close();

		Log.d(TAG, "New unit inserted into sqlite: " + id);

		return id;
	}
	public long addProduct(Product p) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, p.getName());
		values.put(KEY_PRICE, p.getPrice());
		values.put(KEY_UNIT_ID, p.getUnitId());

		if(p.getTag().length() == 0)
			p.setTag(DEFAULT_PRODUCT_TAG);
		values.put(KEY_TAG, p.getTag());

		if(p.getThumbnail().length() == 0)
			p.setThumbnail(DEFAULT_PRODUCT_THUMBNAIL);
		values.put(KEY_THUMBNAIL, p.getThumbnail());

		if(p.getImage().length() == 0)
			p.setImage(DEFAULT_PRODUCT_IMAGE);
		values.put(KEY_IMAGE, p.getImage());

		if(p.getCreateAt().length() == 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String createAt = sdf.format(new Date());
			p.setCreateAt(createAt);
		}

		values.put(KEY_CREATED_AT, p.getCreateAt());

		long id = db.insert(TABLE_PRODUCT, null, values);
		db.close();

		Log.d(TAG, "New product inserted into sqlite: " + id);

		return id;
	}
	public long addOrder(Order o) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, o.getName());

		if(o.getCreateAt().length() == 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String createAt = sdf.format(new Date());
			o.setCreateAt(createAt);
		}

		values.put(KEY_CREATED_AT, o.getCreateAt());

		long id = db.insert(TABLE_ORDER, null, values);
		db.close();

		Log.d(TAG, "New order inserted into sqlite: " + id);

		return id;
	}
	public long addOrderProduct(OrderProduct op) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ORDER_ID, op.getOrder_id());
		values.put(KEY_PRODUCT_ID, op.getProduct_id());
		values.put(KEY_COUNT, op.getCount());

		if(op.getCreateAt().length() == 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String createAt = sdf.format(new Date());
			op.setCreateAt(createAt);
		}

		values.put(KEY_CREATED_AT, op.getCreateAt());

		long id = db.insert(TABLE_ORDER_PRODUCT, null, values);
		db.close();

		Log.d(TAG, "New unit inserted into sqlite: " + id);

		return id;
	}

	public int deleteUnit(Unit u){
		SQLiteDatabase db = this.getWritableDatabase();

		int result = 0;

		ArrayList<Product> products = getProductByUnitId(u.getId());
		if(products.size() == 0) {
			result = db.delete(TABLE_UNIT, KEY_ID + " = ?",
					new String[]{String.valueOf(u.getId())});
			db.close();
		}

		return result;
	}
	public int deleteProduct(Product p){
		SQLiteDatabase db = this.getWritableDatabase();

		int result = 0;

		ArrayList<OrderProduct> ops = getOrderProductByProductId(p.getId());
		if (ops.size() == 0){
			db.delete(TABLE_PRODUCT, KEY_ID + " = ?",
					new String[] { String.valueOf(p.getId()) });
			db.close();
		}

		return result;
	}
	public int deleteOrder(Order o){
		SQLiteDatabase db = this.getWritableDatabase();

		ArrayList<OrderProduct> ops = getOrderProductByOrderId(o.getId());
		if(ops.size() > 0){
			for (OrderProduct op : ops)
				deleteOrderProduct(op);
		}

		int result = db.delete(TABLE_ORDER, KEY_ID + " = ?",
				new String[] { String.valueOf(o.getId()) });
		db.close();

		return result;
	}
	public int deleteOrderProduct(OrderProduct op){
		SQLiteDatabase db = this.getWritableDatabase();
		int result = db.delete(TABLE_ORDER_PRODUCT, KEY_ID + " = ?",
				new String[] { String.valueOf(op.getId()) });
		db.close();

		return result;
	}

	public int updateUnit(Unit u) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, u.getName());

		// updating row
		int result = db.update(TABLE_UNIT, values, KEY_ID + " = ?",
				new String[] { String.valueOf(u.getId()) });
		db.close();

		return result;
	}
	public int updateProduct(Product p) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, p.getName());
		values.put(KEY_PRICE, p.getPrice());
		values.put(KEY_UNIT_ID, p.getUnitId());
		values.put(KEY_TAG, p.getTag());
		values.put(KEY_THUMBNAIL, p.getThumbnail());
		values.put(KEY_IMAGE, p.getImage());

		// updating row
		int result = db.update(TABLE_PRODUCT, values, KEY_ID + " = ?",
				new String[] { String.valueOf(p.getId()) });
		db.close();

		return result;
	}
	public int updateOrder(Order o) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, o.getName());

		// updating row
		int result = db.update(TABLE_ORDER, values, KEY_ID + " = ?",
				new String[] { String.valueOf(o.getId()) });
		db.close();

		return result;
	}
	public int updateOrderProduct(OrderProduct op) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ORDER_ID, op.getOrder_id());
		values.put(KEY_PRODUCT_ID, op.getProduct_id());
		values.put(KEY_COUNT, op.getCount());

		// updating row
		int result = db.update(TABLE_ORDER_PRODUCT, values, KEY_ID + " = ?",
				new String[] { String.valueOf(op.getId()) });
		db.close();

		return result;
	}

	public Unit getUnit(int id) {
		Unit u = null;

		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.query(TABLE_UNIT, new String[]
							{
									KEY_ID,
									KEY_NAME
							},
					KEY_ID + "=?",
					new String[]{String.valueOf(id)}, null, null, null, null);
			if (cursor != null) {
				cursor.moveToFirst();
				u = new Unit(
						cursor.getString(cursor.getColumnIndex(KEY_NAME))
				);
				u.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
			}
			db.close();
		}catch (Exception e)
		{

		}

		return u;
	}
	public ArrayList<Unit> getAllUnit() {
		ArrayList<Unit> units = new ArrayList<Unit>();

		try {
			SQLiteDatabase db = this.getWritableDatabase();
			String q = "SELECT * FROM " + TABLE_UNIT;
			Cursor cursor = db.rawQuery(q, null);
			if (cursor != null) {
				if(cursor.moveToFirst()) {
					do {
						Unit u = new Unit(
								cursor.getString(cursor.getColumnIndex(KEY_NAME))
						);
						u.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
						units.add(u);
					}while (cursor.moveToNext());
				}
			}
			db.close();
		}catch (Exception e)
		{

		}

		return units;
	}
	public Product getProduct(int id) {
		Product p = null;

		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.query(TABLE_PRODUCT, new String[]
							{
									KEY_ID,
									KEY_NAME,
									KEY_PRICE,
									KEY_UNIT_ID,
									KEY_TAG,
									KEY_THUMBNAIL,
									KEY_IMAGE,
									KEY_CREATED_AT
							},
					KEY_ID + "=?",
					new String[]{String.valueOf(id)}, null, null, null, null);
			if (cursor != null) {
				cursor.moveToFirst();
				p = new Product(
						cursor.getString(cursor.getColumnIndex(KEY_NAME)),
						cursor.getFloat(cursor.getColumnIndex(KEY_PRICE)),
						cursor.getInt(cursor.getColumnIndex(KEY_UNIT_ID)),
						cursor.getString(cursor.getColumnIndex(KEY_TAG)),
						cursor.getString(cursor.getColumnIndex(KEY_THUMBNAIL)),
						cursor.getString(cursor.getColumnIndex(KEY_IMAGE))
				);
				p.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
				p.setCreateAt(cursor.getString(cursor.getColumnIndex(KEY_CREATED_AT)));
			}
		}catch (Exception e)
		{

		}

		return p;
	}
	public ArrayList<Product> getAllProduct() {
		ArrayList<Product> products = new ArrayList<Product>();

		try {
			SQLiteDatabase db = this.getWritableDatabase();
			String q = "SELECT * FROM " + TABLE_PRODUCT;
			Cursor cursor = db.rawQuery(q, null);
			if (cursor != null) {
				if(cursor.moveToFirst()) {
					do {
						Product p = new Product(
								cursor.getString(cursor.getColumnIndex(KEY_NAME)),
								cursor.getFloat(cursor.getColumnIndex(KEY_PRICE)),
								cursor.getInt(cursor.getColumnIndex(KEY_UNIT_ID)),
								cursor.getString(cursor.getColumnIndex(KEY_TAG)),
								cursor.getString(cursor.getColumnIndex(KEY_THUMBNAIL)),
								cursor.getString(cursor.getColumnIndex(KEY_IMAGE))
						);
						p.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
						p.setCreateAt(cursor.getString(cursor.getColumnIndex(KEY_CREATED_AT)));

						products.add(p);
					}while (cursor.moveToNext());
				}
			}
			db.close();
		}catch (Exception e)
		{

		}

		return products;
	}
	private ArrayList<Product> getProductByUnitId(int unitId) {
		ArrayList<Product> products = new ArrayList<Product>();

		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.query(TABLE_PRODUCT, new String[]
							{
									KEY_ID,
									KEY_NAME,
									KEY_PRICE,
									KEY_UNIT_ID,
									KEY_TAG,
									KEY_THUMBNAIL,
									KEY_IMAGE,
									KEY_CREATED_AT
							},
					KEY_UNIT_ID + "=?",
					new String[]{String.valueOf(unitId)}, null, null, null, null);
			if (cursor != null) {

				if(cursor.moveToFirst()) {

					do {
						Product p = new Product(
								cursor.getString(cursor.getColumnIndex(KEY_NAME)),
								cursor.getFloat(cursor.getColumnIndex(KEY_PRICE)),
								cursor.getInt(cursor.getColumnIndex(KEY_UNIT_ID)),
								cursor.getString(cursor.getColumnIndex(KEY_TAG)),
								cursor.getString(cursor.getColumnIndex(KEY_THUMBNAIL)),
								cursor.getString(cursor.getColumnIndex(KEY_IMAGE))
						);
						p.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
						p.setCreateAt(cursor.getString(cursor.getColumnIndex(KEY_CREATED_AT)));

						products.add(p);

					} while (cursor.moveToNext());

				}
			}
			db.close();
		}catch (Exception e)
		{

		}

		return products;
	}
	public Order getOrder(int id) {
		Order o = null;

		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.query(TABLE_ORDER, new String[]
							{
									KEY_ID,
									KEY_NAME,
									KEY_CREATED_AT
							},
					KEY_ID + "=?",
					new String[]{String.valueOf(id)}, null, null, null, null);
			if (cursor != null) {
				cursor.moveToFirst();
				o = new Order(
						cursor.getString(cursor.getColumnIndex(KEY_NAME))
				);
				o.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
				o.setCreateAt(cursor.getString(cursor.getColumnIndex(KEY_CREATED_AT)));

				ArrayList<OrderProduct> ops = getOrderProductByOrderId(id);
				ArrayList<Product> products = new ArrayList<Product>();
				if(ops.size() > 0) {
					for (OrderProduct op : ops) {
						Product p = getProduct(op.getProduct_id());
						if (p != null)
							products.add(p);
					}
				}

				o.setProducts(products);
			}
			db.close();
		}catch (Exception e)
		{

		}

		return o;
	}
	public OrderProduct getOrderProduct(int id) {
		OrderProduct op = null;

		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.query(TABLE_ORDER_PRODUCT, new String[]
							{
									KEY_ID,
									KEY_ORDER_ID,
									KEY_PRODUCT_ID,
									KEY_COUNT,
									KEY_CREATED_AT
							},
					KEY_ID + "=?",
					new String[]{String.valueOf(id)}, null, null, null, null);
			if (cursor != null) {
				cursor.moveToFirst();
				op = new OrderProduct(
						cursor.getInt(cursor.getColumnIndex(KEY_ORDER_ID)),
						cursor.getInt(cursor.getColumnIndex(KEY_PRODUCT_ID)),
						cursor.getInt(cursor.getColumnIndex(KEY_COUNT))
				);
				op.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
				op.setCreateAt(cursor.getString(cursor.getColumnIndex(KEY_CREATED_AT)));
			}
			db.close();
		}catch (Exception e)
		{

		}

		return op;
	}
	private ArrayList<OrderProduct> getOrderProductByOrderId(int orderId) {
		ArrayList<OrderProduct> ops = new ArrayList<OrderProduct>();

		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.query(TABLE_ORDER_PRODUCT, new String[]
							{
									KEY_ID,
									KEY_ORDER_ID,
									KEY_PRODUCT_ID,
									KEY_COUNT,
									KEY_CREATED_AT
							},
					KEY_ORDER_ID + "=?",
					new String[]{String.valueOf(orderId)}, null, null, null, null);
			if (cursor != null) {
				if(cursor.moveToFirst()) {
					do {
						OrderProduct op = new OrderProduct(
								cursor.getInt(cursor.getColumnIndex(KEY_ORDER_ID)),
								cursor.getInt(cursor.getColumnIndex(KEY_PRODUCT_ID)),
								cursor.getInt(cursor.getColumnIndex(KEY_COUNT))
						);
						op.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
						op.setCreateAt(cursor.getString(cursor.getColumnIndex(KEY_CREATED_AT)));

						ops.add(op);
					}while (cursor.moveToNext());
				}
			}
			db.close();
		}catch (Exception e)
		{

		}

		return ops;
	}
	private ArrayList<OrderProduct> getOrderProductByProductId(int productId) {
		ArrayList<OrderProduct> ops = new ArrayList<OrderProduct>();

		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.query(TABLE_ORDER_PRODUCT, new String[]
							{
									KEY_ID,
									KEY_ORDER_ID,
									KEY_PRODUCT_ID,
									KEY_COUNT,
									KEY_CREATED_AT
							},
					KEY_PRODUCT_ID + "=?",
					new String[]{String.valueOf(productId)}, null, null, null, null);
			if (cursor != null) {
				if(cursor.moveToFirst()) {
					do {
						OrderProduct op = new OrderProduct(
								cursor.getInt(cursor.getColumnIndex(KEY_ORDER_ID)),
								cursor.getInt(cursor.getColumnIndex(KEY_PRODUCT_ID)),
								cursor.getInt(cursor.getColumnIndex(KEY_COUNT))
						);
						op.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
						op.setCreateAt(cursor.getString(cursor.getColumnIndex(KEY_CREATED_AT)));

						ops.add(op);
					}while (cursor.moveToNext());
				}
			}

			db.close();
		}catch (Exception e)
		{

		}

		return ops;
	}
}
