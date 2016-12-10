package com.example.android.movies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.movies.helpers.InternetConnectionListener;
import com.example.android.movies.helpers.LayoutListener;

public class Posters extends AppCompatActivity implements LayoutListener{
    private boolean mIsTwoPane = false;
    private InternetConnectionListener con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        con = new InternetConnectionListener();
        con.onReceive(this.getApplicationContext(),this.getIntent());

        if(!con.isOnline(this))
        {
            Toast.makeText(this, "No internet connection!", Toast.LENGTH_LONG);
            setContentView(R.layout.activity_no_internet);
        }
        else
        {
            setContentView(R.layout.activity_posters);
            PostersFragment postersFragment = new PostersFragment();
            postersFragment.setLayoutListener(this);
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.posters, postersFragment)
                        .commit();

                //Check if 2 panes
                if(findViewById(R.id.details)!=null)
                    mIsTwoPane = true;
            }
        }
    }

    @Override
    public void set(String name) {
        //One Pane
        if(!mIsTwoPane)
        {
            startActivity(new Intent(this,DetailsActivity.class).putExtra("movie_name", name));
        }
        //Two Pane
        else
        {
            DetailsFragment detailsFragment = new DetailsFragment();
            Bundle data = new Bundle();
            data.putString("movie_name",name);
            detailsFragment.setArguments(data);
            getSupportFragmentManager().beginTransaction().replace(R.id.details,detailsFragment,"").commit();
        }
    }
}
