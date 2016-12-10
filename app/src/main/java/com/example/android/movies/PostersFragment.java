package com.example.android.movies;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RadioGroup;

import com.example.android.movies.adapters.ImageAdapter;
import com.example.android.movies.data.DatabaseHelper;
import com.example.android.movies.data.MoviesContract;
import com.example.android.movies.helpers.DataDisplay;
import com.example.android.movies.helpers.FetchData;
import com.example.android.movies.helpers.MovieDetailsParser;
import com.example.android.movies.helpers.LayoutListener;

import java.util.HashMap;


public class PostersFragment extends Fragment implements DataDisplay {
    private ImageAdapter adapter;
    private LayoutListener layoutListener;
    public static DatabaseHelper db;
    private HashMap<String,String>[] movies;


    public void setLayoutListener(LayoutListener layoutListener) {
        this.layoutListener = layoutListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        db = new DatabaseHelper(getContext());
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == R.id.action_sort)
        {
            final Dialog sort_dialog = new Dialog(getContext());
            sort_dialog.setContentView(R.layout.sort_dialog);
            sort_dialog.show();
            final RadioGroup sort_choice = (RadioGroup) sort_dialog.findViewById(R.id.sort_radio_group);
            sort_choice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if(checkedId == R.id.popularity_option)
                        adapter.sortBy("popularity");
                    else if (checkedId == R.id.top_rated_option)
                        adapter.sortBy("top_rated");
                    sort_dialog.cancel();
                }
            });
            return true;
        }
        else if(id == R.id.action_show)
        {
            final Dialog show_dialog = new Dialog(getContext());
            show_dialog.setContentView(R.layout.show_dialog);
            show_dialog.show();
            final RadioGroup sort_choice = (RadioGroup) show_dialog.findViewById(R.id.show_radio_group);
            sort_choice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if(checkedId == R.id.fav_option)
                        adapter.showFav();
                    else if (checkedId == R.id.all_option)
                        adapter.add(movies);
                    show_dialog.cancel();
                }
            });

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_posters, container, false);
        HashMap<String,String>[] temp = new HashMap[0];
        adapter = new ImageAdapter(getActivity(),temp);
        GridView posters_grid  =(GridView) rootView.findViewById(R.id.posters_gridview);
        posters_grid.setAdapter(adapter);
        posters_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = String.valueOf(position);
                layoutListener.set(name);
            }
        });
        return rootView;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        updatePosters();
    }

    public void updatePosters()
    {
        FetchData moviesTask = new FetchData(getContext(), new MovieDetailsParser(),this, MoviesContract.MOVIE_DB_URL+ MoviesContract.MOVIE_DB_POPULAR);
        moviesTask.execute();
    }



    public void display(HashMap<String,String>[] results)
    {
        if(results!=null)
        {
            movies = results;
            adapter.add(results);
            adapter.notifyDataSetChanged();
        }
    }

}
