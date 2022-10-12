package com.example.tourin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tourin.DetailActivity;
import com.example.tourin.Model.Menu;
import com.example.tourin.Model.Place;
import com.example.tourin.R;

import java.util.Vector;

public class MuseumAdapter extends RecyclerView.Adapter<MuseumAdapter.ViewHolder> {
    Context ctx;
    Vector<Menu> places;
    private String url;

    public MuseumAdapter(Context ctx,Vector<Menu> places){
        this.ctx = ctx;
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

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        CardView view;
        ImageView imageViewFoto;
        TextView tvName, tvLocation, tvDeskripsi;
        ImageButton showButton;
        LinearLayout hiddenLayout;
        String placeId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.museumView);
            imageViewFoto = itemView.findViewById(R.id.lokasiFoto);
            tvName = itemView.findViewById(R.id.tvNamaTempat);
            tvLocation = itemView.findViewById(R.id.tvLokasi);
            tvDeskripsi = itemView.findViewById(R.id.tvDescription);
            showButton = itemView.findViewById(R.id.imageButton);
            hiddenLayout = itemView.findViewById(R.id.layoutExpand);

            showButton.setOnClickListener(v -> {
                if(hiddenLayout.getVisibility() == View.VISIBLE){
                    TransitionManager.beginDelayedTransition(view, new AutoTransition());
                    hiddenLayout.setVisibility(View.GONE);
                    showButton.setImageResource(R.drawable.arrow);
                } else{
                    TransitionManager.beginDelayedTransition(view, new AutoTransition());
                    hiddenLayout.setVisibility(View.VISIBLE);
                    showButton.setImageResource(R.drawable.arrowup);
                }
            });
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Intent i = new Intent(view.getContext(), DetailActivity.class);
            i.putExtra("id", placeId);
            view.getContext().startActivity(i);
        }
    }
}