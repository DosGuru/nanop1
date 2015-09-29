package com.example.android.p1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.p1.API.MovieAPI;
import com.example.android.p1.model.PopularMovies;
import com.example.android.p1.model.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {
    private GridView _gridViewMain;
    private MovieGridViewAdapter _adapter;
    private String _posterImageSize;
    private String _sortParam;
    private String _key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _posterImageSize = getPosterSize();
        _gridViewMain = (GridView) findViewById(R.id.gridView);
        _key = getResources().getString(R.string.apikey);
        if (savedInstanceState != null) {
            _sortParam = savedInstanceState.getString("sort_param");
        } else {
            _sortParam = getResources().getString(R.string.param_popular);
        }
        LoadMovies();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("sort_param", _sortParam);
    }

    public void SortPopular(MenuItem item) {
        _sortParam = getResources().getString(R.string.param_popular);
        LoadMovies();
    }

    public void SortRating(MenuItem item) {
        _sortParam = getResources().getString(R.string.param_rating);
        LoadMovies();
    }
    public void LoadMovies() {
        String baseAPIURL = "http://api.themoviedb.org/3/";
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(baseAPIURL)
                .build();
        String key = getResources().getString(R.string.apikey);
        MovieAPI service = restAdapter.create(MovieAPI.class);
        service.getMovies(_sortParam, key, new Callback<PopularMovies>() {
            @Override
            public void success(PopularMovies popularMovies, Response response) {
                ArrayList<MovieItem> items = new ArrayList<MovieItem>();

                for (Result result: popularMovies.getResults()) {
                    MovieItem item = new MovieItem();
                    item.setImageUrl("http://image.tmdb.org/t/p/" + _posterImageSize + result.getPosterPath());
                    item.setMovieId(result.getId().toString());
                    item.setTitle(result.getTitle());
                    items.add(item);
                }
                MovieGridViewAdapter adapter = new MovieGridViewAdapter(getApplicationContext(), R.layout.moviegrid_item_layout, items);
                _gridViewMain.setAdapter(adapter);
                _gridViewMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String movieId = ((MovieGridViewAdapter.ViewHolder) view.getTag()).movieId;
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra("movieid", movieId);
                        MainActivity.this.startActivity(intent);
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "A Network Error Has Occurred", Toast.LENGTH_LONG);
            }
        });
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
