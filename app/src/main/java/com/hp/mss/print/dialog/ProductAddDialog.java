package com.hp.mss.print.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hp.mss.print.R;
import com.hp.mss.print.activity.ProductActivity;
import com.hp.mss.print.adapter.SpinnerUnitAdapter;
import com.hp.mss.print.helper.SQLiteHandler;
import com.hp.mss.print.model.Product;
import com.hp.mss.print.model.Unit;

import java.util.ArrayList;

/**
 * Created by hop on 28/02/2017.
 */

public class ProductAddDialog{

    public static final int PICK_THUMBNAIL = 0;
    public static final int PICK_IMAGE = 1;

    private OnListener mListener;
    private ProductActivity.OnListenerProduct mListenerProduct;

    private AlertDialog mDialog;
    private EditText edtName;
    private EditText edtPrice;
    private Spinner spUnit;
    private EditText edtTag;
    private TextView txtThumbnail;
    private TextView txtTempThumbnail;
    private TextView txtImage;
    private TextView txtTempImage;
    private Button btnThumbnail;

    private Button btnImage;

    private ImageView imgThumbnail;
    private ImageView imgImage;

    private SQLiteHandler db;

    public ProductAddDialog(final Context context, OnListener listener) {

        mListener = listener;
        db = new SQLiteHandler(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_product_add, null);
        edtName = (EditText) view.findViewById(R.id.edtName);
        edtPrice = (EditText) view.findViewById(R.id.edtPrice);

        spUnit = (Spinner) view.findViewById(R.id.spUnit);
        ArrayList<Unit> units = db.getAllUnit();
        SpinnerUnitAdapter spinnerAdapter = new SpinnerUnitAdapter(context, R.layout.spinner_unit_item, units);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spUnit.setAdapter(spinnerAdapter);

        edtTag = (EditText) view.findViewById(R.id.edtTag);

        txtThumbnail = (TextView) view.findViewById(R.id.txtThumbnail);
        txtTempThumbnail = (TextView) view.findViewById(R.id.txtTempThumbnail);
        btnThumbnail = (Button) view.findViewById(R.id.btnThumbnail);
        imgThumbnail = (ImageView) view.findViewById(R.id.imgThumbnail);

        txtImage = (TextView) view.findViewById(R.id.txtImage);
        txtTempImage = (TextView) view.findViewById(R.id.txtTempImage);
        btnImage = (Button) view.findViewById(R.id.btnImage);
        imgImage = (ImageView) view.findViewById(R.id.imgImage);

        btnThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onChooseThumbnail(mListenerProduct);
            }
        });

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onChooseImage(mListenerProduct);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.title_product);
        builder.setView(view)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = edtName.getText().toString();
                        float price = 0;
                        if(edtPrice.getText().length() > 0)
                            price = Float.parseFloat(edtPrice.getText().toString());
                        int unitId = (int)spUnit.getSelectedItemId();
                        String tag = edtTag.getText().toString();
                        String thumbnail = txtThumbnail.getText().toString();
                        String image = txtImage.getText().toString();

                        if(mListener != null && name.length() > 0 && edtPrice.getText().toString().length() > 0){
                            Product p = new Product(name, price, unitId, tag, thumbnail, image);
                            mListener.onAddClick(p);
                        }
                        else
                            Toast.makeText(context, "Tên sản phẩm hoặc giá không đê trống", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(mListener != null)
                            mListener.onCancelClick();
                    }
                })
        ;

        mDialog = builder.create();
        mDialog.setCancelable(false);

        mListenerProduct = new ProductActivity.OnListenerProduct() {
            @Override
            public void setThumbnail(String filePath) {
                txtThumbnail.setText(filePath);

                String filename = filePath.substring(filePath.lastIndexOf("/")+1);
                txtTempThumbnail.setText(filename);

                Bitmap b = BitmapFactory.decodeFile(filePath);
                imgThumbnail.setImageBitmap(b);
            }

            @Override
            public void setImage(String filePath) {
                txtImage.setText(filePath);

                String filename = filePath.substring(filePath.lastIndexOf("/")+1);
                txtTempImage.setText(filename);

                Bitmap b = BitmapFactory.decodeFile(filePath);
                imgImage.setImageBitmap(b);
            }
        };
    }

    public void show() {
        mDialog.show();
    }

    public interface OnListener {
        public abstract void onAddClick(Product p);
        public abstract void onCancelClick();
        public abstract void onChooseThumbnail(ProductActivity.OnListenerProduct mOnListenerProduct);
        public abstract void onChooseImage(ProductActivity.OnListenerProduct mOnListenerProduct);
    }
}
