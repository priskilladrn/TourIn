package com.example.tourin.Adapter;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Vector;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    Vector<Place> list;
    Context ctx;
    private String  url;

    public SearchAdapter(Context ctx, Vector<Place> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        url = list.get(position).getImageUrl();
        Glide.with(ctx).load(url).into(holder.ivPhotoSearch);
        holder.tvNamaTempatSearch.setText(list.get(position).getName());
        holder.tvLocationSearch.setText(list.get(position).getRegion());
        holder.placeId = list.get(position).getPlaceId();
    }

    @Override
    public int getItemCount() {
        if(list == null){
            return 0;
        } else {
            return list.size();
        }
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivPhotoSearch;
        TextView tvNamaTempatSearch, tvLocationSearch;
        CardView searchView;
        String placeId;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPhotoSearch = itemView.findViewById(R.id.ivPhotoSearch);
            tvNamaTempatSearch = itemView.findViewById(R.id.tvNamaTempatSearch);
            tvLocationSearch = itemView.findViewById(R.id.tvLocationSearch);
            searchView = itemView.findViewById(R.id.searchView);
            searchView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(view.getContext(), DetailActivity.class);
            i.putExtra("id", placeId);
            view.getContext().startActivity(i);
        }
    }
}


