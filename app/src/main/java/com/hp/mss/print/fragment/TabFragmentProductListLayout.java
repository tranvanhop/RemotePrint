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

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hp.mss.print.R;
import com.hp.mss.print.activity.ProductActivity;
import com.hp.mss.print.adapter.ListViewProductAdapter;
import com.hp.mss.print.helper.SQLiteHandler;
import com.hp.mss.print.helper.TAG;
import com.hp.mss.print.model.Product;

import java.io.Serializable;
import java.util.ArrayList;

import static com.hp.mss.print.activity.ProductActivity.TAG_FRAGMENT_PRODUCT_ADD;
import static com.hp.mss.print.activity.ProductActivity.TAG_FRAGMENT_PRODUCT_LIST;

public class TabFragmentProductListLayout extends Fragment{

    ListView lvProduct;
    ListViewProductAdapter adapter;

    SQLiteHandler db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_product_list, container, false);

        if (ProductActivity.menu != null)
            ProductActivity.menu.findItem(R.id.actionAdd).setVisible(true);

        db = new SQLiteHandler(getActivity());

        init(inflatedView);

        return inflatedView;
    }

    private void init(View v){
        lvProduct = (ListView) v.findViewById(R.id.lvProduct);
        ArrayList<Product> products = db.getAllProduct();
        adapter = new ListViewProductAdapter(getActivity(), R.layout.list_view_product_item, products, mListener);
        lvProduct.setAdapter(adapter);
    }

    OnListenerClick mListener = new OnListenerClick() {
        @Override
        public void onClickEdit(Product p) {

            TabFragmentProductAddLayout fr = new TabFragmentProductAddLayout();
            Bundle b = new Bundle();
            b.putInt(TAG.ID, p.getId());
            fr.setArguments(b);

            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentReplace, fr, TAG_FRAGMENT_PRODUCT_ADD);
            transaction.addToBackStack(TAG_FRAGMENT_PRODUCT_LIST);
            transaction.commit();
        }
    };

    public interface OnListenerClick{
        public abstract void onClickEdit(Product p);
    }
}
