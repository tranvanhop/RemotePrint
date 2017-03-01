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
import com.hp.mss.print.model.Unit;

import java.util.ArrayList;

/***** Adapter class extends with ArrayAdapter ******/
public class SpinnerUnitAdapter extends ArrayAdapter<Unit>{

    Context context = null;
    ArrayList<Unit> data = new ArrayList<Unit>();
    int layoutResourceId;
    ViewGroup viewGroup;

    public SpinnerUnitAdapter(Context context, int layoutResourceId, ArrayList<Unit> data)
    {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    static class RecordHolder {
        TextView txtName;
        int id;
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

        Unit item = data.get(position);
        holder.txtName.setText(item.getName());
        holder.id = item.getId();

        return row;
    }
}
