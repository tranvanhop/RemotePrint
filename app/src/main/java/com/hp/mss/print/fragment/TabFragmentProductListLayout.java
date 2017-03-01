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

public class TabFragmentProductListLayout extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_menu_layout, container, false);

        return inflatedView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
    }
}
