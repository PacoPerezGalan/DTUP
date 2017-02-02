package com.pacoperezgalan.dtup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.resource;
import static android.R.string.no;

/**
 * Created by pacoperezgalan on 30/1/17.
 */

public class AdaptadorDrawerGrid extends ArrayAdapter{
    public AdaptadorDrawerGrid(Context context,String[] noms){
        super(context,0,noms);

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView=inflater.inflate(R.layout.layout_item_drawer,null);
        }

        TextView nom=(TextView) convertView.findViewById(R.id.nom_item_drawer);

        nom.setText((Integer) getItem(position));

        return convertView;
    }
}
