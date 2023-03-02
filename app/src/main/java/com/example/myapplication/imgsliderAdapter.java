package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class imgsliderAdapter extends SliderViewAdapter<imgsliderAdapter.Holder> {

    String[] sliderlist;

    public imgsliderAdapter(String[] sliderlist) {
        this.sliderlist = sliderlist;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_item,parent,false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {
        Picasso.get().load(sliderlist[position]).into(viewHolder.imageIt);
    }

    @Override
    public int getCount() {

        return sliderlist.length;
    }

    public class Holder extends SliderViewAdapter.ViewHolder {

        ImageView imageIt;
        View itemView;

        public Holder(View itemView) {
            super(itemView);

            imageIt = itemView.findViewById(R.id.imageIt);
            this.itemView = itemView;
        }
    }
}
