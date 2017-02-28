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

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.hp.mss.print.R;
import com.hp.mss.print.activity.OrderActivity;
import com.hp.mss.print.activity.ProductActivity;
import com.hp.mss.print.activity.ReportActivity;
import com.hp.mss.print.activity.SystemActivity;
import com.hp.mss.print.adapter.GridViewAdapter;
import com.hp.mss.print.item.MenuItem;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class TabFragmentMenuLayout extends Fragment {

    GridView gridViewMenu;
    ArrayList<MenuItem> gridListMenu = new ArrayList<MenuItem>();
    GridViewAdapter gridViewAdapter;

    static final int POSITION_SYSTEM = 0;
    static final int POSITION_PRODUCT = 1;
    static final int POSITION_ORDER = 2;
    static final int POSITION_REPORT = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_menu_layout, container, false);

        setGridListMenu();

        gridViewMenu = (GridView) inflatedView.findViewById(R.id.gridViewMenu);
        gridViewAdapter = new GridViewAdapter(getActivity(), R.layout.grid_view_menu_item, gridListMenu);
        gridViewMenu.setAdapter(gridViewAdapter);

        gridViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: " + position);

                Intent intent = null;

                switch (position) {
                    case POSITION_SYSTEM:
                        intent = new Intent(getActivity(), SystemActivity.class);
                        break;
                    case POSITION_PRODUCT:
                        intent = new Intent(getActivity(), ProductActivity.class);
                        break;
                    case POSITION_ORDER:
                        intent = new Intent(getActivity(), OrderActivity.class);
                        break;
                    case POSITION_REPORT:
                        intent = new Intent(getActivity(), ReportActivity.class);
                        break;
                }

                if(intent != null){
                    startActivity(intent);
//                    getActivity().finish();
                }
            }
        });

        return inflatedView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
    }

    private void setGridListMenu() {
        //set grid view item
        Bitmap iconOrder = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_order);
        String titleOrder = getResources().getString(R.string.title_order);

        Bitmap iconSystem = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_system);
        String titleSystem = getResources().getString(R.string.title_system);

        Bitmap iconProduct = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_product);
        String titleProduct = getResources().getString(R.string.title_product);

        Bitmap iconReport = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_report);
        String titleReport = getResources().getString(R.string.title_report);

        gridListMenu.add(new MenuItem(iconSystem, titleSystem));
        gridListMenu.add(new MenuItem(iconProduct, titleProduct));
        gridListMenu.add(new MenuItem(iconOrder, titleOrder));
        gridListMenu.add(new MenuItem(iconReport, titleReport));
    }
}
