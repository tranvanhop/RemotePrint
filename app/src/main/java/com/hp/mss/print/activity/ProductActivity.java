package com.hp.mss.print.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.hp.mss.print.R;
import com.hp.mss.print.dialog.ProductAddDialog;
import com.hp.mss.print.fragment.TabFragmentProductListLayout;
import com.hp.mss.print.fragment.TabFragmentSystemPrintLayout;
import com.hp.mss.print.helper.SQLiteHandler;
import com.hp.mss.print.model.Product;

import static com.hp.mss.print.dialog.ProductAddDialog.PICK_IMAGE;
import static com.hp.mss.print.dialog.ProductAddDialog.PICK_THUMBNAIL;

public class ProductActivity extends AppCompatActivity {

    public interface OnListenerProduct {
        public abstract void setThumbnail(String filePath);
        public abstract void setImage(String filePath);
    }

    private OnListenerProduct mListenerProduct;
    private SQLiteHandler db;
    ProductAddDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarProduct);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        db = new SQLiteHandler(this);

        selectFragment();
    }

    public void selectFragment(){
        TabFragmentProductListLayout fr = new TabFragmentProductListLayout();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentPlace, fr);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        // Define the listener
        MenuItemCompat.OnActionExpandListener expandListener = new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when action item collapses
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;  // Return true to expand action view
            }
        };

        MenuItem actionMenuItem = menu.findItem(R.id.actionAdd);
        MenuItemCompat.setOnActionExpandListener(actionMenuItem, expandListener);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionAdd:
                dialog = new ProductAddDialog(ProductActivity.this, mProductAddDialogOnListener);
                dialog.show();
                return true;

            case R.id.actionSearch:

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case PICK_THUMBNAIL:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(
                            selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    mListenerProduct.setThumbnail(filePath);
                }
                break;
            case PICK_IMAGE:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(
                            selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    mListenerProduct.setImage(filePath);
                }
                break;
        }
    }

    ProductAddDialog.OnListener mProductAddDialogOnListener = new ProductAddDialog.OnListener() {
        @Override
        public void onAddClick(Product p) {
            db.addProduct(p);
        }

        @Override
        public void onCancelClick() {

        }

        @Override
        public void onChooseThumbnail(ProductActivity.OnListenerProduct mOnListenerProduct) {

            mListenerProduct = mOnListenerProduct;

            Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
            getIntent.setType("image/*");

            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");

            Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

            startActivityForResult(chooserIntent, PICK_THUMBNAIL);
        }

        @Override
        public void onChooseImage(ProductActivity.OnListenerProduct mOnListenerProduct) {

            mListenerProduct = mOnListenerProduct;

            Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
            getIntent.setType("image/*");

            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");

            Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

            startActivityForResult(chooserIntent, PICK_IMAGE);
        }
    };
}