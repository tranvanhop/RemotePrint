package com.hp.mss.print.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hp.mss.print.R;
import com.hp.mss.print.item.BluetoothItem;

import java.util.ArrayList;

/**
 * Created by hop on 25/02/2017.
 */

public class ListViewAdapter extends ArrayAdapter<BluetoothItem> {

    Context context = null;
    ArrayList<BluetoothItem> data = null;
    int layoutResourceId;
    ViewGroup viewGroup;

    public ListViewAdapter(Context context, int layoutResourceId, ArrayList<BluetoothItem> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.data = data;
        this.layoutResourceId = layoutResourceId;
    }

    static class RecordHolder {
        TextView txtName;
        TextView txtAddress;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        RecordHolder holder = null;
        viewGroup = parent;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RecordHolder();
            holder.txtName = (TextView) row.findViewById(R.id.txtName);
            holder.txtAddress = (TextView) row.findViewById(R.id.txtAddress);

            row.setTag(holder);
        }
        else
            holder = (RecordHolder) row.getTag();

        BluetoothItem item = data.get(position);
        holder.txtName.setText(item.getTxtName());
        holder.txtAddress.setText(item.getTxtAddress());

        return row;
    }
}
