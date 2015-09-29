package com.example.android.p1;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by dave_ on 9/10/2015.
 */
public class MovieGridViewAdapter extends ArrayAdapter {
    private Context _context;
    private ArrayList<MovieItem> _movies = new ArrayList<MovieItem>();
    private int _layoutResourceId;

    public MovieGridViewAdapter(Context context, int layoutResourceId, ArrayList<MovieItem> movies) {
        super(context, layoutResourceId, movies);
        _layoutResourceId = layoutResourceId;
        _context = context;
        _movies = movies;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(_context);
            row = inflater.inflate(_layoutResourceId, parent, false);
            holder = new ViewHolder();

            holder.image = (ImageView) row.findViewById(R.id.imageViewPoster);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        MovieItem item = _movies.get(position);
        holder.movieId = item.getMovieId();
        Picasso.with(getContext()).load(item.getImageUrl()).into(holder.image);
        return row;
    }

    static class ViewHolder {
        ImageView image;
        String movieId;
    }
}