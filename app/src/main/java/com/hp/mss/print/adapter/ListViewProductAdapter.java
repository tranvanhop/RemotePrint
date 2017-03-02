package com.hp.mss.print.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hp.mss.print.R;
import com.hp.mss.print.fragment.TabFragmentProductListLayout;
import com.hp.mss.print.helper.SQLiteHandler;
import com.hp.mss.print.model.Product;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by hop on 25/02/2017.
 */

public class ListViewProductAdapter extends ArrayAdapter<Product> {

    Context context = null;
    ArrayList<Product> data = null;
    int layoutResourceId;
    ViewGroup viewGroup;
    SQLiteHandler db;
    TabFragmentProductListLayout.OnListenerProductEvent mListener;

    public ListViewProductAdapter(Context context, int layoutResourceId, ArrayList<Product> data,
                                  TabFragmentProductListLayout.OnListenerProductEvent mListener) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.data = data;
        this.layoutResourceId = layoutResourceId;
        this.mListener = mListener;

        db = new SQLiteHandler(context);
    }

    static class RecordHolder {
        TextView txtName;
        ImageView imgThumbnail;
        ImageButton imgBtnEdit;
        ImageButton imgBtnDelete;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View row = convertView;
        RecordHolder holder = null;
        viewGroup = parent;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RecordHolder();
            holder.txtName = (TextView) row.findViewById(R.id.txtName);
            holder.imgThumbnail = (ImageView) row.findViewById(R.id.imgThumbnail);
            holder.imgBtnEdit = (ImageButton) row.findViewById(R.id.imgBtnEdit);
            holder.imgBtnDelete = (ImageButton) row.findViewById(R.id.imgBtnDelete);

            row.setTag(holder);
        }
        else
            holder = (RecordHolder) row.getTag();

        final Product item = data.get(position);
        holder.txtName.setText(item.getName());

        File file = new File(item.getThumbnail());
        Bitmap b;
        if(file.exists()) {
            b = BitmapFactory.decodeFile(item.getThumbnail());
            holder.imgThumbnail.setImageBitmap(b);
        }
        else {
            AssetManager am = context.getAssets();
            try {
                b = BitmapFactory.decodeStream(am.open("ic_product_thumbnail.png"));
                holder.imgThumbnail.setImageBitmap(b);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        holder.imgBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickEdit(item);
            }
        });

        holder.imgBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(context.getResources().getString(R.string.message_warning_title))
                        .setMessage(context.getResources().getString(R.string.message_warning_delete))
                        .setPositiveButton(context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mListener.onClickDelete(item);
                            }
                        })
                        .setNegativeButton(context.getResources().getString(R.string.no), null)
                        .show();
            }
        });

        return row;
    }
}
