package com.emmanuelcorrales.filemanager;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.io.File;
import java.util.List;

class DirectoriesStatePagerAdapter extends FragmentStatePagerAdapter {
    private List<File> mFiles;
    private FilesFragment.OnDirectoryClickedListener mOnDirectoryClickedListener;
    private FilesFragment.OnFileClickedListener mOnFileClickedListener;

    DirectoriesStatePagerAdapter(FragmentManager fm, List<File> files,
                                 @Nullable FilesFragment.OnDirectoryClickedListener onDirectoryClickedListener,
                                 @Nullable FilesFragment.OnFileClickedListener onFileClickedListener) {
        super(fm);
        if (files == null) {
            throw new IllegalArgumentException("Argument 'files' cannot be null.");
        }
        mFiles = files;
        mOnDirectoryClickedListener = onDirectoryClickedListener;
        mOnFileClickedListener = onFileClickedListener;
    }

    @Override
    public Fragment getItem(int i) {
        return createFilesFragment(mFiles.get(i));
    }

    @Override
    public int getCount() {
        return mFiles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Internal Storage";
        }
        return mFiles.get(position).getName();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    private FilesFragment createFilesFragment(File directory) {
        FilesFragment filesFragment = FilesFragment.newInstance(directory);
        filesFragment.setOnDirectoryClickedListener(mOnDirectoryClickedListener);
        filesFragment.setOnFileClickedListener(mOnFileClickedListener);
        return filesFragment;
    }
}