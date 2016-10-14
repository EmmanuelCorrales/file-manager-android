package com.emmanuelcorrales.filemanager;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import filebrowser.Utils;

public class MainActivity extends AppCompatActivity implements
        FilesFragment.OnFileClickedListener, FilesFragment.OnDirectoryClickedListener {

    private List<FilesFragment> mFragmentList = new ArrayList<>();
    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFragmentList.add(createFilesFragment(Environment.getExternalStorageDirectory()));
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new FilesStatePagerAdapter(getSupportFragmentManager()));

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(mPager, true);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() > 0) {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    @Override
    public void OnDirectoryClicked(File directory) {
        mFragmentList.subList(mPager.getCurrentItem() + 1, mFragmentList.size()).clear();
        mFragmentList.add(createFilesFragment(directory));
        mPager.getAdapter().notifyDataSetChanged();
        mPager.setCurrentItem(mFragmentList.size() - 1);
    }

    @Override
    public void OnFileClicked(File file) {
        try {
            startActivity(Utils.createOpenFileIntent(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FilesFragment createFilesFragment(File directory) {
        FilesFragment filesFragment = FilesFragment.newInstance(directory);
        filesFragment.setOnDirectoryClickedListener(this);
        filesFragment.setOnFileClickedListener(this);
        return filesFragment;
    }

    private class FilesStatePagerAdapter extends FragmentStatePagerAdapter {
        FilesStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return mFragmentList.get(i);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "Internal Storage";
            }
            return mFragmentList.get(position).getDirectory().getName();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
