package com.pacoperezgalan.dtup;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdaptadorRecycler extends RecyclerView.Adapter<AdaptadorRecycler.itemViewHolder>{
    ArrayList<Actor> actors;
    public static class itemViewHolder extends RecyclerView.ViewHolder{
        TextView nom,descripcio,naixement,ciutat,altura,matrimoni,fills;
        ImageView imatge;
        public itemViewHolder(View v){
            super(v);
            nom=(TextView) v.findViewById(R.id.nom);
            descripcio=(TextView) v.findViewById(R.id.descripcio);
            naixement=(TextView) v.findViewById(R.id.naixement);
            ciutat=(TextView) v.findViewById(R.id.ciutat);
            altura=(TextView) v.findViewById(R.id.altura);
            matrimoni=(TextView) v.findViewById(R.id.matrimoni);
            fills=(TextView) v.findViewById(R.id.fills);
            imatge=(ImageView) v.findViewById(R.id.imatge);
        }

    }


    public AdaptadorRecycler(ArrayList<Actor> actors){
        this.actors=actors;
    }

    @Override
    public int getItemCount() {
        return actors.size();
    }

    @Override
    public itemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item,parent,false);
        return new itemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final itemViewHolder holder, int position) {
        holder.nom.setText(actors.get(position).getNom());
        holder.descripcio.setText(actors.get(position).getDescricio());
        holder.naixement.setText(actors.get(position).getNaixement());
        holder.ciutat.setText(actors.get(position).getCiutat());
        holder.altura.setText(actors.get(position).getAltura());
        holder.matrimoni.setText(actors.get(position).getMatrimoni());
        holder.fills.setText(actors.get(position).getFills());
        holder.imatge.setImageBitmap(actors.get(position).getImage());

        holder.imatge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),actors.get(holder.getAdapterPosition()).getNom(),Toast.LENGTH_SHORT).show();
            }
        });

    }





}
