package com.pg.fontdemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class TabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(this, getSupportFragmentManager());

        // Set the adapter onto the view pager
       setupViewPager(viewPager);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
    
    
    private void setupViewPager(ViewPager viewPager) {
        // IMPORT V4 FRAGMENT AND FRAGMENT MANAGER
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        Bundle b = new Bundle();

        for (int i = 0; i < 6; i++) {
            b.putString("Text", "String :" + i);
            adapter.addFragment(new OneFragment(), "Tab " + i);
            arrayList.add("ArrayList string :: " + i);

        }
        viewPager.setAdapter(adapter);

    }
}
