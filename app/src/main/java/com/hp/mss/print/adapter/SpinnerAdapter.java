package com.hp.mss.print.adapter;

/**
 * Created by hop on 26/02/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hp.mss.print.R;
import com.hp.mss.print.item.DeviceItem;

/***** Adapter class extends with ArrayAdapter ******/
public class SpinnerAdapter extends ArrayAdapter<DeviceItem>{

    Context context = null;
    DeviceItem[] data = null;
    int layoutResourceId;
    ViewGroup viewGroup;

    public SpinnerAdapter(Context context, int layoutResourceId, DeviceItem[] data)
    {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    static class RecordHolder {
        TextView txtName;
    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;
        viewGroup = parent;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RecordHolder();
            holder.txtName = (TextView) row.findViewById(R.id.txtName);

            row.setTag(holder);
        }
        else
            holder = (RecordHolder) row.getTag();

        DeviceItem item = data[position];
        holder.txtName.setText(item.getName());

        return row;
    }
}
