package com.example.tourin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
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
import com.example.tourin.Model.Place;
import com.example.tourin.R;
import com.example.tourin.SavedFragment;

import java.util.Vector;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.ViewHolder> {
    Context ctx;
    Vector<Place> places;
    private String url;

    public SavedAdapter(Context ctx){
        this.ctx = ctx;
    }

    public void setSaved(Vector<Place> places){
        this.places = places;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.saved_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        url = places.get(position).getImageUrl();
        Glide.with(ctx).load(url).into(holder.imageView);
        holder.tvName.setText(places.get(position).getName());
        holder.tvLocation.setText(places.get(position).getRegion());
        holder.placeId = places.get(position).getPlaceId();
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView view;
        ImageView imageView;
        TextView tvName, tvLocation;
        String placeId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.savedView);
            view.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.locationPic);
            tvName = itemView.findViewById(R.id.tvName);
            tvLocation = itemView.findViewById(R.id.tvLocation);
        }

        @Override
        public void onClick(View v) {
            if(v == view){
                //go to detail and send extras
                Log.wtf("test123123", "masuk");
                Intent i = new Intent(v.getContext(), DetailActivity.class);
                i.putExtra("id", placeId);
                v.getContext().startActivity(i);
            }
        }
    }
}
