package com.hp.mss.print.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.hp.mss.print.R;
import com.hp.mss.print.fragment.TabFragmentProductAddLayout;
import com.hp.mss.print.fragment.TabFragmentProductListLayout;

public class ProductActivity extends AppCompatActivity {

    public static Menu menu = null;
    TabFragmentProductListLayout frProductList;
    TabFragmentProductAddLayout frProductAdd;
    public static final String TAG_FRAGMENT_PRODUCT_LIST = "fragment_product_list";
    public static final String TAG_FRAGMENT_PRODUCT_ADD = "fragment_product_add";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarProduct);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        frProductList = new TabFragmentProductListLayout();
        frProductAdd = new TabFragmentProductAddLayout();

        selectFragment(frProductList, TAG_FRAGMENT_PRODUCT_LIST);
    }

    private void selectFragment(Fragment fr, String tag){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentReplace, fr, tag);
        if(tag.equals(TAG_FRAGMENT_PRODUCT_ADD))
            transaction.addToBackStack(TAG_FRAGMENT_PRODUCT_LIST);

        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product, menu);
        this.menu = menu;

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
                selectFragment(frProductAdd, TAG_FRAGMENT_PRODUCT_ADD);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}