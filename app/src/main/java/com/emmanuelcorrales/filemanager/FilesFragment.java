package com.emmanuelcorrales.filemanager;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
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

import java.io.File;


public class FilesFragment extends Fragment implements FilesAdapter.OnItemClickedListener {

    public interface OnFileClickedListener {
        void OnFileClicked(File file);
    }

    public interface OnDirectoryClickedListener {
        void OnDirectoryClicked(File directory);
    }

    public static final String TAG = FilesFragment.class.getSimpleName();

    private static final int REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 34783;
    private static final String KEY_DIRECTORY_PATH = "key_directory_path";

    private FilesAdapter mFilesAdapter;
    private FilesFragment.OnFileClickedListener mOnFileClickedListener;
    private OnDirectoryClickedListener mOnDirectoryClickedListener;
    private boolean mRestored = true;

    public static FilesFragment newInstance(File directory) {
        if (directory == null) {
            throw new IllegalArgumentException("Argument 'directory' cannot be null.");
        }
        FilesFragment filesFragment = new FilesFragment();
        filesFragment.mFilesAdapter = new FilesAdapter(directory, filesFragment);
        filesFragment.mRestored = false;
        return filesFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && mRestored) {
            String path = savedInstanceState.getString(KEY_DIRECTORY_PATH);
            if (path != null) {
                mFilesAdapter = new FilesAdapter(new File(path), this);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_files, container, false);
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

        switch (requestCode) {
            case REQUEST_PERMISSION_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Permission granted.");
                    initRecyclerView();
                } else {
                    Log.d(TAG, "Permission not granted. Closing the app.");
                    getActivity().finish();
                }
                break;

            default:
                Log.d(TAG, "Permission not granted.");
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_DIRECTORY_PATH, mFilesAdapter.getDirectory().getAbsolutePath());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onItemClicked(final File item) {
        if (item.isDirectory() && mOnDirectoryClickedListener != null) {
            mOnDirectoryClickedListener.OnDirectoryClicked(item);
        } else if (mOnFileClickedListener != null) {
            mOnFileClickedListener.OnFileClicked(item);
        }
    }

    public void setOnFileClickedListener(FilesFragment.OnFileClickedListener listener) {
        mOnFileClickedListener = listener;
    }

    public void setOnDirectoryClickedListener(OnDirectoryClickedListener listener) {
        mOnDirectoryClickedListener = listener;
    }

    public File getDirectory() {
        return mFilesAdapter.getDirectory();
    }

    private void initRecyclerView() {
        if (!Utils.isExternalStorageReadable()) {
            Log.d(TAG, "External storage is not readable.");
            return;
        }
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mFilesAdapter);
    }
}
