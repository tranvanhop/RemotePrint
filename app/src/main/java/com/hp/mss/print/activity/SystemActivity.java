package com.hp.mss.print.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.hp.mss.print.R;
import com.hp.mss.print.adapter.ViewPagerAdapter;
import com.hp.mss.print.fragment.TabFragmentSystemInfoLayout;
import com.hp.mss.print.fragment.TabFragmentSystemPrintLayout;

public class SystemActivity extends AppCompatActivity{

    TabFragmentSystemInfoLayout systemInfoLayoutFragment;
    TabFragmentSystemPrintLayout systemPrintLayoutFragment;

    final int[] ICONS = new int[]{
            R.drawable.ic_system_print,
            R.drawable.ic_system_info
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system);

        Toolbar toolbar = (Toolbar) findViewById(R.id.sample_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_system);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout_system);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(ICONS[0]);
        tabLayout.getTabAt(1).setIcon(ICONS[1]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        systemInfoLayoutFragment = new TabFragmentSystemInfoLayout();
        systemPrintLayoutFragment = new TabFragmentSystemPrintLayout();

        adapter.addFrag(systemPrintLayoutFragment, " " + getResources().getString(R.string.title_system_print));
        adapter.addFrag(systemInfoLayoutFragment, " " + getResources().getString(R.string.title_system_info));

        viewPager.setAdapter(adapter);
    }
}