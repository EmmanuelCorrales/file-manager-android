package com.emmanuelcorrales.filemanager.fileselector;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emmanuelcorrales.filemanager.R;
import com.emmanuelcorrales.filemanager.Utils;

import java.io.File;

public class FileBrowserFragment extends Fragment implements FileBrowserAdapter.OnItemClickedListener {

    public interface OnFileClickedListener {
        void OnFileClicked(File file);
    }

    public static final String TAG = FileBrowserFragment.class.getSimpleName();

    private static final int REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 34783;

    private FileBrowserAdapter mFileBrowserAdapter;
    private OnFileClickedListener mOnFileClickedListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_file_browser, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_READ_EXTERNAL_STORAGE);
        } else {
            initRecyclerView();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult()");
        switch (requestCode) {
            case REQUEST_PERMISSION_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Permission granted.");
                    initRecyclerView();
                }
                break;

            default:
                Log.d(TAG, "Permission not granted.");
                break;
        }
    }

    @Override
    public void onClickFile(final File file) {
        if (file.isDirectory()) {
            changeDirectory(file);
        } else if (mOnFileClickedListener != null) {
            mOnFileClickedListener.OnFileClicked(file);
        }
    }

    public void navigateParentDirectory() {
        if (!Utils.isExternalStorageRoot(mFileBrowserAdapter.getDirectory())) {
            changeDirectory(mFileBrowserAdapter.getDirectory().getParentFile());
        }
    }

    public void setOnFileClickedListener(OnFileClickedListener onFileClickedListener) {
        mOnFileClickedListener = onFileClickedListener;
    }

    private void initRecyclerView() {
        if (!Utils.isExternalStorageReadable()) {
            Log.d(TAG, "External storage is not readable.");
            return;
        }
        mFileBrowserAdapter = new FileBrowserAdapter(Environment.getExternalStorageDirectory(), this);
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mFileBrowserAdapter);
    }

    private void changeDirectory(File dir) {
        if (dir == null) {
            throw new IllegalArgumentException("Argument 'dir' cannot be null.");
        }
        if (!dir.isDirectory()) {
            throw new IllegalArgumentException("Argument 'dir' must be a directory.");
        }
        mFileBrowserAdapter.setDirectory(dir);
        mFileBrowserAdapter.notifyDataSetChanged();
    }
}
