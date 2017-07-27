package com.emmanuelcorrales.filemanager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AnalyticsActivity implements View.OnClickListener,
        FilesFragment.OnFileClickedListener, FilesFragment.OnDirectoryClickedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String KEY_PATHS = "key_paths";

    private List<File> mDirectories = new ArrayList<>();
    private boolean mSnackbarShowing = false;
    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        } else {
            mDirectories.add(Environment.getExternalStorageDirectory());
        }
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new DirectoriesStatePagerAdapter(getSupportFragmentManager(), mDirectories, this, this));

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(mPager, true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        ArrayList<String> paths = Utils.convertFilesToStrings(mDirectories);
        outState.putStringArrayList(KEY_PATHS, paths);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() > 0) {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        } else if (!mSnackbarShowing) {
            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
            Snackbar.make(coordinatorLayout, R.string.snackbar_close_txt, Snackbar.LENGTH_SHORT)
                    .setAction(R.string.snackbar_close_btn, this).setCallback(new Snackbar.Callback() {

                @Override
                public void onShown(Snackbar snackbar) {
                    super.onShown(snackbar);
                    mSnackbarShowing = true;
                }

                @Override
                public void onDismissed(Snackbar snackbar, int event) {
                    super.onDismissed(snackbar, event);
                    mSnackbarShowing = false;
                }
            }).show();
        }
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void OnDirectoryClicked(File directory) {
        Log.d(TAG, "Opening directory " + directory.getAbsolutePath());
        mDirectories.subList(mPager.getCurrentItem() + 1, mDirectories.size()).clear();
        mDirectories.add(directory);
        DirectoriesStatePagerAdapter adapter = (DirectoriesStatePagerAdapter) mPager.getAdapter();
        adapter.setDirectories(mDirectories);
        adapter.notifyDataSetChanged();
        mPager.setCurrentItem(mDirectories.size() - 1);
    }

    @Override
    public void OnFileClicked(File file) {
        if (file == null) {
            Toast.makeText(this, "Cannot select this file.", Toast.LENGTH_SHORT).show();
        }
        if (getIntent() != null && getIntent().getAction() != null
                && getIntent().getAction().equals(Intent.ACTION_GET_CONTENT)) {
            getIntent().setData(Uri.fromFile(file));
            setResult(RESULT_OK, getIntent());
            finish();
        } else {
            try {
                startActivity(Utils.createOpenFileIntent(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void restoreState(Bundle savedInstanceState) {
        ArrayList<String> paths = savedInstanceState.getStringArrayList(KEY_PATHS);
        if (paths != null) {
            mDirectories = Utils.convertStringsToFiles(paths);
        }

        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment f : fragments) {
                if (f instanceof FilesFragment) {
                    FilesFragment filesFragment = (FilesFragment) f;
                    filesFragment.setOnDirectoryClickedListener(this);
                    filesFragment.setOnFileClickedListener(this);
                }
            }
        }
    }

}
