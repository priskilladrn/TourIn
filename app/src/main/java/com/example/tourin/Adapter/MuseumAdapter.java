package com.example.tourin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tourin.Model.Menu;
import com.example.tourin.Model.Place;
import com.example.tourin.R;

import java.util.Vector;

public class MuseumAdapter extends RecyclerView.Adapter<MuseumAdapter.ViewHolder> {
    Context ctx;
    Vector<Menu> places;
    private String url;

    public MuseumAdapter(Context ctx){
        this.ctx = ctx;
    }

    public void setMuseum(Vector<Menu> places){
        this.places = places;
    }

    @NonNull
    @Override
    public MuseumAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.museum_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MuseumAdapter.ViewHolder holder, int position) {
        url = places.get(position).getImageUrl();
        Glide.with(ctx).load(url).into(holder.imageViewFoto);
        holder.tvName.setText(places.get(position).getName());
        holder.tvLocation.setText(places.get(position).getRegion());
        holder.placeId = places.get(position).getPlaceId();
        holder.tvDeskripsi.setText(places.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView view;
        ImageView imageViewFoto;
        TextView tvName, tvLocation, tvDeskripsi;
        String placeId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.museumView);
            imageViewFoto = itemView.findViewById(R.id.lokasiFoto);
            tvName = itemView.findViewById(R.id.tvNamaTempat);
            tvLocation = itemView.findViewById(R.id.tvLokasi);
            tvDeskripsi = itemView.findViewById(R.id.tvDescription);
        }

    }
}
