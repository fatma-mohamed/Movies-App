package com.example.android.movies;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.example.android.movies.adapters.ImageAdapter;

import java.util.HashMap;
import java.util.Map;

public class DetailsActivity extends FragmentActivity implements ActionBar.TabListener {
    String movieName;
    HashMap<String, String> movieData;
    AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    ViewPager mViewPager;
    /** ViewPager will display the three primary sections of the app, one at a
     * time.
     * */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        movieName = getIntent().getStringExtra("movie_name");
        movieData = getData(movieName);

        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager(),movieData);
        final ActionBar actionBar = this.getActionBar();

        // Specify that the Home/Up button should not be enabled, since there is no hierarchical
        // parent.
        //actionBar.setHomeButtonEnabled(false);

        // Specify that we will be displaying tabs in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mAppSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {
        HashMap<String, String> movieData;

        public AppSectionsPagerAdapter(FragmentManager fm, HashMap<String, String> movie_data) {
            super(fm);
            movieData = movie_data;
        }

        @Override
        public Fragment getItem(int i) {
            Bundle data = DetailsActivity.insertIntoBundle(movieData);
            Fragment fragment = null;
            switch (i) {
                case 0:
                    fragment = new DetailsFragment();
                    fragment.setArguments(data);
                    break;
                case 1:
                    fragment = new ReviewsFragment();
                    fragment.setArguments(data);
                    break;
                case 2:
                    fragment = new TrailersFragment();
                    fragment.setArguments(data);
                    break;
                default:
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            CharSequence name = "";
            switch (position) {
                case 0: {
                    name =  "Details";
                    break;
                }
                case 1: {
                    name = "Reviews";
                    break;
                }
                case (2): {
                    name = "Trailers";
                    break;
                }
                default: {
                    break;
                }
            }
            return name;
        }
    }

    public HashMap<String, String> getData(String position)
    {
        HashMap<String, String> m = (HashMap<String, String>)(new ImageAdapter()).getItem(Integer.parseInt(position));
        return m;
    }

    public static Bundle insertIntoBundle (HashMap<String, String> m){
        Bundle data = new Bundle();
        for(Map.Entry<String,String> e : m.entrySet()){
            data.putString(e.getKey(),e.getValue());
        }
        return data;
    }

}
