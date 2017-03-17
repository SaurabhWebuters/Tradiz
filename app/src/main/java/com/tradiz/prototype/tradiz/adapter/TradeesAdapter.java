package com.tradiz.prototype.tradiz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.squareup.picasso.Picasso;
import com.tradiz.prototype.tradiz.R;
import com.tradiz.prototype.tradiz.app.AppController;
import com.tradiz.prototype.tradiz.model.Tradees;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Saurabh on 3/10/2017.
 */

public class TradeesAdapter extends RecyclerView.Adapter<TradeesAdapter.ViewHolder> {
    private ArrayList<Tradees> trades_list;
    private Context context;
    ImageLoader imageLoader;

    public TradeesAdapter(Context context, ArrayList<Tradees> trades_list) {
        this.context = context;
        this.trades_list = trades_list;

    }

    @Override
    public TradeesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tradees_list_row, viewGroup, false);
        return new TradeesAdapter.ViewHolder(view);
    }

    // Binding Data
    @Override
    public void onBindViewHolder(TradeesAdapter.ViewHolder viewHolder, int i) {

        viewHolder.tv_name.setText(trades_list.get(i).getFullName());
        viewHolder.tv_price.setText("$" + trades_list.get(i).getRate() + "/hour");
        viewHolder.tv_availability.setText("Free in " + trades_list.get(i).getAvailability() + " days");
        viewHolder.tv_distance.setText(trades_list.get(i).getDistance() + "Km");
        Log.d("Tradiz", "" + trades_list.get(i).getRank());
        viewHolder.rating_bar.setRating((float) trades_list.get(i).getRank());
        Picasso.with(context).load(trades_list.get(i).getImageURL()).resize(72, 72).error(context.getResources().getDrawable(R.drawable.ic_default_profile)).into(viewHolder.profile_image);
    }

    @Override
    public int getItemCount() {
        return trades_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profile_image;
        TextView tv_name, tv_price, tv_distance, tv_availability;
        RatingBar rating_bar;


        public ViewHolder(View view) {
            super(view);
            if (imageLoader == null) {
                imageLoader = AppController.getInstance().getImageLoader();
            }
            tv_name = (TextView) view.findViewById(R.id.name);
            tv_price = (TextView) view.findViewById(R.id.price);
            rating_bar = (RatingBar) view.findViewById(R.id.rating_bar);
            tv_distance = (TextView) view.findViewById(R.id.distance);
            tv_availability = (TextView) view.findViewById(R.id.availability);
            profile_image = (CircleImageView) view.findViewById(R.id.profile_image);
        }
    }
}