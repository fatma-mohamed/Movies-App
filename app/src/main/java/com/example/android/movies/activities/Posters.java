package com.example.android.movies.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.movies.R;
import com.example.android.movies.fragments.PostersFragment;
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
            //Toast.makeText(this, "No internet connection!", Toast.LENGTH_LONG);
            setContentView(R.layout.activity_no_internet);
            Button try_again_btn = (Button)findViewById(R.id.try_again_btn);
            try_again_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent t= new Intent(Posters.this,Posters.class);
                    startActivity(t);
                }
            });
        }
        else
        {
            setContentView(R.layout.activity_posters);
            PostersFragment postersFragment = new PostersFragment();
            postersFragment.setLayoutListener(this);
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.posters, postersFragment, "posters_fragment")
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
            startActivity(new Intent(this,DetailsActivity.class).putExtra("movie_name",name));
        }
    }

    @Override
    public void onBackPressed(){
        PostersFragment fragment = (PostersFragment)getSupportFragmentManager().findFragmentByTag("posters_fragment");
        if (fragment != null && fragment.isVisible()){
            if (fragment.favShown) {
                fragment.favShown = false;
                fragment.updatePosters();
            }
            else
                this.finish();
        }

    }
}
