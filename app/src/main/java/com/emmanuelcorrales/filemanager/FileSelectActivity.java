package com.emmanuelcorrales.filemanager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.emmanuelcorrales.filemanager.fileselector.FileBrowserFragment;

import java.io.File;

public class FileSelectActivity extends AppCompatActivity implements FileBrowserFragment.OnFileClickedListener {

    private static final String TAG = FileSelectActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_select);

        FileBrowserFragment fileBrowserFragment = getFileBrowserFragment();
        if (fileBrowserFragment != null) {
            fileBrowserFragment.setOnFileClickedListener(this);
        }
    }

    @Override
    public void OnFileClicked(File file) {
        Intent intent = getIntent();
        if (file == null) {
            Toast.makeText(this, "Cannot select this file.", Toast.LENGTH_SHORT).show();
        }
        intent.setData(Uri.fromFile(file));
        setResult(RESULT_OK, intent);
        finish();
    }

    private FileBrowserFragment getFileBrowserFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (fragment != null) {
            return ((FileBrowserFragment) fragment);
        }
        return null;
    }
}
