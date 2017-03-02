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

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hp.mss.print.R;
import com.hp.mss.print.activity.ProductActivity;
import com.hp.mss.print.adapter.SpinnerUnitAdapter;
import com.hp.mss.print.dialog.UnitDialog;
import com.hp.mss.print.helper.SQLiteHandler;
import com.hp.mss.print.helper.TAG;
import com.hp.mss.print.helper.Utility;
import com.hp.mss.print.model.Product;
import com.hp.mss.print.model.Unit;

import java.io.File;
import java.util.ArrayList;
import static android.app.Activity.RESULT_OK;

public class TabFragmentProductAddLayout extends Fragment{

    private final int PICK_THUMBNAIL = 1;
    private final int PICK_IMAGE = 2;

    private TextView txtTitle;
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

    private Button btnSave;
    private Button btnCancel;

    private SQLiteHandler db;
    private int id = 0;

    private Unit uOther;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_product_add, container, false);

        if (ProductActivity.menu != null)
            ProductActivity.menu.findItem(R.id.actionAdd).setVisible(false);

        init(inflatedView);

        return inflatedView;
    }

    private void init(View view){

        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        edtName = (EditText) view.findViewById(R.id.edtName);
        edtPrice = (EditText) view.findViewById(R.id.edtPrice);

        spUnit = (Spinner) view.findViewById(R.id.spUnit);
        uOther = new Unit("Other");
        db = new SQLiteHandler(getActivity());
        final ArrayList<Unit> units = db.getAllUnit();
        units.add(uOther);
        final SpinnerUnitAdapter spinnerAdapter = new SpinnerUnitAdapter(getActivity(), R.layout.spinner_unit_item, units);
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

        btnSave = (Button) view.findViewById(R.id.btnSave);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);

        btnThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickChooseImage(PICK_THUMBNAIL);
            }
        });

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickChooseImage(PICK_IMAGE);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                float price = 0;
                if(edtPrice.getText().length() > 0)
                    price = Float.parseFloat(edtPrice.getText().toString());
                int unitId = ((Unit)spUnit.getSelectedItem()).getId();
                String tag = edtTag.getText().toString();
                String thumbnail = txtThumbnail.getText().toString();
                String image = txtImage.getText().toString();

                if(id != 0){ // update
                    if(name.length() > 0 && edtPrice.getText().toString().length() > 0) {

                        Product p = new Product(name, price, unitId, tag, thumbnail, image);
                        p.setId(id);
                        if (db.updateProduct(p) != -1) {

                            Toast.makeText(getActivity(), R.string.message_product_edit_success, Toast.LENGTH_LONG).show();
                            onOpenFragmentProductList();
                        }
                        else
                            Toast.makeText(getActivity(), R.string.message_product_edit_error_2, Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(getActivity(), R.string.message_product_edit_error, Toast.LENGTH_LONG).show();
                }
                else { //add
                    if(name.length() > 0 && edtPrice.getText().toString().length() > 0) {

                        Product p = new Product(name, price, unitId, tag, thumbnail, image);
                        if (db.addProduct(p) != -1) {

                            Toast.makeText(getActivity(), R.string.message_product_add_success, Toast.LENGTH_LONG).show();
                            onOpenFragmentProductList();
                        }
                        else
                            Toast.makeText(getActivity(), R.string.message_product_add_error_2, Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(getActivity(), R.string.message_product_add_error, Toast.LENGTH_LONG).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOpenFragmentProductList();
            }
        });


        Bundle b = getArguments();
        if(b != null)
            id = b.getInt(TAG.ID);

        if(id != 0){ // update
            txtTitle.setText(getActivity().getResources().getString(R.string.title_product_edit));
            Product p = db.getProduct(id);
            if(p != null){
                edtName.setText(p.getName());
                edtPrice.setText(Float.toString(p.getPrice()));
                edtTag.setText(p.getTag());

                String thumbnail = p.getThumbnail().substring(p.getThumbnail().lastIndexOf("/")+1);
                txtTempThumbnail.setText(thumbnail);
                txtThumbnail.setText(p.getThumbnail());

                if(new File(p.getThumbnail()).exists()) {
                    Bitmap bThumbnail = BitmapFactory.decodeFile(p.getThumbnail());
                    imgThumbnail.setImageBitmap(bThumbnail);
                }

                String image = p.getImage().substring(p.getImage().lastIndexOf("/")+1);
                txtTempImage.setText(image);
                txtImage.setText(p.getImage());

                if(new File(p.getImage()).exists()) {
                    Bitmap bImage = BitmapFactory.decodeFile(p.getImage());
                    imgImage.setImageBitmap(bImage);
                }

                spUnit.setSelection(Utility.getPositionUnit(units, p.getUnitId()));
            }
        }
        else
            txtTitle.setText(getActivity().getResources().getString(R.string.title_product_add));

        Drawable spinnerDrawable = spUnit.getBackground().getConstantState().newDrawable();

        spinnerDrawable.setColorFilter(getResources().getColor(R.color.HPFontColorDarkBlue), PorterDuff.Mode.SRC_ATOP);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            spUnit.setBackground(spinnerDrawable);
        }else{
            spUnit.setBackgroundDrawable(spinnerDrawable);
        }

        spUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                if(position == spinnerAdapter.getCount() - 1)
                {
                    UnitDialog dialog = new UnitDialog(getActivity(), new UnitDialog.OnListenerUnitEvent() {

                        @Override
                        public void onSaveUnit(Unit u) {
                            int unitId = (int)db.addUnit(u);
                            if(unitId != -1) {
                                u.setId(unitId);
                                spinnerAdapter.remove(uOther);
                                spinnerAdapter.add(u);
                                spinnerAdapter.add(uOther);
                                spinnerAdapter.notifyDataSetChanged();
                                spUnit.setSelection(position);
                            }
                        }
                    });

                    dialog.show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spUnit.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Cảnh báo")
                        .setMessage(getActivity().getResources().getString(R.string.message_warning_delete))
                        .setPositiveButton(getActivity().getResources().getString(R.string.yes), new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Unit u = spinnerAdapter.getItem(position);
                                if(db.deleteUnit(spinnerAdapter.getItem(position)) != -1){
                                    spinnerAdapter.remove(u);
                                    spinnerAdapter.notifyDataSetChanged();
                                }
                            }

                        })
                        .setNegativeButton(getActivity().getResources().getString(R.string.no), null)
                        .show();

                return true;
            }
        });
    }

    private void onClickChooseImage(int type){
//        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
//        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(pickIntent, getActivity().getResources().getString(R.string.text_choose));
//        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, type);
    }

    private void onOpenFragmentProductList(){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentReplace, new TabFragmentProductListLayout(), ProductActivity.TAG_FRAGMENT_PRODUCT_LIST);
        transaction.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case PICK_THUMBNAIL:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getActivity().getContentResolver().query(
                            selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    if(filePath != null && filePath.length() > 0){
                        Bitmap b = BitmapFactory.decodeFile(filePath);
                        imgThumbnail.setImageBitmap(b);

                        String filename = filePath.substring(filePath.lastIndexOf("/")+1);
                        txtTempThumbnail.setText(filename);
                        txtThumbnail.setText(filePath);
                    }
                }
                break;
            case PICK_IMAGE:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getActivity().getContentResolver().query(
                            selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    if(filePath != null && filePath.length() > 0){
                        Bitmap b = BitmapFactory.decodeFile(filePath);
                        imgImage.setImageBitmap(b);

                        String filename = filePath.substring(filePath.lastIndexOf("/")+1);
                        txtTempImage.setText(filename);
                        txtImage.setText(filePath);
                    }
                }
                break;
        }
    }
}
