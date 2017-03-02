package com.hp.mss.print.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hp.mss.print.R;
import com.hp.mss.print.item.MenuItem;

import java.util.ArrayList;

/**
 * Created by hop on 24/02/2017.
 */

public class GridViewMenuAdapter extends ArrayAdapter<MenuItem>{

    Context context;
    int layoutResourceId;
    ArrayList<MenuItem> data = new ArrayList<MenuItem>();

    public GridViewMenuAdapter(Context context, int layoutResourceId, ArrayList<MenuItem> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    static class RecordHolder {
        TextView txtTitle;
        ImageView imgIcon;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RecordHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.txtTitle);
            holder.imgIcon = (ImageView) row.findViewById(R.id.imgIcon);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }

        MenuItem item = data.get(position);
        holder.txtTitle.setText(item.getTxtTitle());
        holder.imgIcon.setImageBitmap(item.getImgIcon());
        return row;
    }
}
