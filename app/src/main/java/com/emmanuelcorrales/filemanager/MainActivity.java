package com.emmanuelcorrales.filemanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;

import filebrowser.FileBrowserFragment;
import filebrowser.Utils;

public class MainActivity extends AppCompatActivity implements FileBrowserFragment.OnFileClickedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FileBrowserFragment fileBrowserFragment = getFileBrowserFragment();
        if (fileBrowserFragment != null) {
            fileBrowserFragment.setOnFileClickedListener(this);
        }
    }

    @Override
    public void onBackPressed() {
        FileBrowserFragment fileBrowserFragment = getFileBrowserFragment();
        if (fileBrowserFragment != null) {
            getFileBrowserFragment().navigateParentDirectory();
        }
    }

    @Override
    public void OnFileClicked(File file) {
        try {
            startActivity(Utils.createOpenFileIntent(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FileBrowserFragment getFileBrowserFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (fragment != null) {
            return ((filebrowser.FileBrowserFragment) fragment);
        }
        return null;
    }
}
