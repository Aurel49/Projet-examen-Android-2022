package com.ynov.examenandroid.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ynov.examenandroid.R;
import com.ynov.examenandroid.bo.detail_logement.DetailsDate;

import java.util.List;

public class CustomGridAdapter extends BaseAdapter {

    // Attributs
    private List<DetailsDate> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    // Constructeur
    public CustomGridAdapter(Context aContext, List<DetailsDate> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    // Assecceurs de grid
    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_layout_date_reservation, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.idDetailsDateImage);
            holder.dateView = (TextView) convertView.findViewById(R.id.idDetailsDateLaDate);
            holder.lieuView = (TextView) convertView.findViewById(R.id.idDetailsDateLieu);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DetailsDate details = this.listData.get(position);
        holder.dateView.setText(details.get_date());
        holder.lieuView.setText(details.get_lieu());
       // holder.imageView.setImageBitmap(details.get_urlImage());


        return convertView;
    }


    static class ViewHolder {
        ImageView imageView;
        TextView dateView;
        TextView lieuView;
    }

}
