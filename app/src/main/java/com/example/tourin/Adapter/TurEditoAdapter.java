package com.example.tourin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tourin.DetailActivity;
import com.example.tourin.Model.Editor;
import com.example.tourin.R;

import java.util.Vector;

public class TurEditoAdapter  extends RecyclerView.Adapter<TurEditoAdapter.ViewHolder>{
    Context ctx;
    Vector<Editor> places;
    private String url;

    public TurEditoAdapter(Context ctx, Vector<Editor> places){
        this.ctx = ctx;
        this.places = places;
    }
    @NonNull
    @Override
    public TurEditoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.tureditor_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TurEditoAdapter.ViewHolder holder, int position) {
        url = places.get(position).getImageUrl();
        Glide.with(ctx).load(url).into(holder.imageViewFoto);
        holder.tvName.setText(places.get(position).getName());
        holder.placeId = places.get(position).getPlaceId();
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView view;
        ImageView imageViewFoto;
        TextView tvName;
        String placeId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.tutoritem);
            imageViewFoto = itemView.findViewById(R.id.fotoLokasiEditor);
            tvName = itemView.findViewById(R.id.namaLokasiEditor);
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
