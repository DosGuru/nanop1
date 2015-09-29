package com.example.android.p1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.android.p1.API.MovieAPI;
import com.example.android.p1.model.TMDBMovie;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DetailActivity extends AppCompatActivity {
    private TMDBMovie _movie;
    private TextView _textViewTitle;
    private TextView _textViewReleaseDate;
    private TextView _textViewRating;
    private TextView _textViewSynopsis;
    private ImageView _imageViewThumbnail;
    private LinearLayout _layoutMain;
    private String _key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        _key = getResources().getString(R.string.apikey);
        _layoutMain = (LinearLayout) findViewById(R.id.layout_main);
        _textViewTitle = (TextView) findViewById(R.id.textView_title);
        _textViewRating = (TextView) findViewById(R.id.textView_rating);
        _textViewReleaseDate = (TextView) findViewById(R.id.textView_releasedate);
        _textViewSynopsis = (TextView) findViewById(R.id.textView_synopsis);
        _imageViewThumbnail = (ImageView) findViewById(R.id.imageView_thumbnail);

        Intent intent = getIntent();
        String movieId = intent.getStringExtra("movieid");

        String baseAPIURL = "http://api.themoviedb.org/3/";
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(baseAPIURL)
                .build();
        MovieAPI service = restAdapter.create(MovieAPI.class);
        service.getMovie(movieId, _key, new Callback<TMDBMovie>() {
            @Override
            public void success(TMDBMovie tmdbMovie, Response response) {
                _textViewTitle.setText(tmdbMovie.getTitle());
                _textViewRating.setText("Average Rating: " + String.valueOf(tmdbMovie.getVoteAverage()));
                _textViewReleaseDate.setText("Release Date: " + tmdbMovie.getReleaseDate());
                _textViewSynopsis.setText(tmdbMovie.getOverview());
                String imageUrl = "http://image.tmdb.org/t/p/" + getPosterSize() + tmdbMovie.getPosterPath();
                Picasso.with(getApplicationContext()).load(imageUrl).into(_imageViewThumbnail);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "A Network Error Has Occurred", Toast.LENGTH_LONG);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String getPosterSize()
    {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int densityDpi = (int)(metrics.density * 160f);

        if (densityDpi >= 550) {
            return "w500";
        } else if (densityDpi >= 400) {
            return "w342";
        } else {
            return "w185";
        }
    }

}
