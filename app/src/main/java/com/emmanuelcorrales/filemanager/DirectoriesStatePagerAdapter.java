package com.emmanuelcorrales.filemanager;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.io.File;
import java.util.List;

class DirectoriesStatePagerAdapter extends FragmentStatePagerAdapter {
    private List<File> mDirectories;
    private FilesFragment.OnDirectoryClickedListener mOnDirectoryClickedListener;
    private FilesFragment.OnFileClickedListener mOnFileClickedListener;

    DirectoriesStatePagerAdapter(FragmentManager fm, List<File> directories,
                                 @Nullable FilesFragment.OnDirectoryClickedListener onDirectoryClickedListener,
                                 @Nullable FilesFragment.OnFileClickedListener onFileClickedListener) {
        super(fm);
        setDirectories(directories);
        mOnDirectoryClickedListener = onDirectoryClickedListener;
        mOnFileClickedListener = onFileClickedListener;
    }

    @Override
    public Fragment getItem(int i) {
        return createFilesFragment(mDirectories.get(i));
    }

    @Override
    public int getCount() {
        return mDirectories.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Internal Storage";
        }
        return mDirectories.get(position).getName();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void setDirectories(List<File> directories) {
        if (directories == null) {
            throw new IllegalArgumentException("Argument directories cannot be null");
        }
        mDirectories = directories;
    }

    private FilesFragment createFilesFragment(File directory) {
        if (directory == null) {
            throw new IllegalArgumentException("Argument directory cannot be null");
        }
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Calling directory.isDirectory should return false.");
        }
        FilesFragment filesFragment = FilesFragment.newInstance(directory);
        filesFragment.setOnDirectoryClickedListener(mOnDirectoryClickedListener);
        filesFragment.setOnFileClickedListener(mOnFileClickedListener);
        return filesFragment;
    }
}