package com.emmanuelcorrales.filemanager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.ViewHolder> {

    interface OnItemClickedListener {
        void onItemClicked(File file);
    }

    private File mDirectory;
    private OnItemClickedListener mOnItemClickedListener;

    FilesAdapter(File directory, OnItemClickedListener onItemClickedListener) {
        setDirectory(directory);
        setOnFileClickedListener(onItemClickedListener);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final File file = mDirectory.listFiles()[position];
        holder.mTextView.setText(file.getName());
        if (file.isDirectory()) {
            holder.mIcon.setImageResource(R.drawable.ic_folder);
        } else {
            holder.mIcon.setImageResource(getFileDrawable(file));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mOnItemClickedListener != null) {
                    mOnItemClickedListener.onItemClicked(file);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDirectory.listFiles().length;
    }

    public File getDirectory() {
        return mDirectory;
    }

    private void setDirectory(File dir) {
        if (dir == null) {
            throw new IllegalArgumentException("Argument 'dir' cannot be null.");
        }
        if (!dir.isDirectory()) {
            throw new IllegalArgumentException("Argument 'dir' must be a directory.");
        }
        mDirectory = dir;
    }

    private void setOnFileClickedListener(OnItemClickedListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Argument 'listener' cannot be null.");
        }
        mOnItemClickedListener = listener;
    }

    private static int getFileDrawable(File file) {
        if (file.toString().contains(".doc") || file.toString().contains(".docx")) {
            return R.drawable.ic_doc;
        } else if (file.toString().contains(".pdf")) {
            // PDF file
        } else if (file.toString().contains(".ppt") || file.toString().contains(".pptx")) {
            // Powerpoint file
            return R.drawable.ic_ppt;
        } else if (file.toString().contains(".xls") || file.toString().contains(".xlsx")) {
            return R.drawable.ic_xls;
        } else if (file.toString().contains(".zip") || file.toString().contains(".rar")) {
            // WAV audio file
        } else if (file.toString().contains(".rtf")) {
            // RTF file
        } else if (file.toString().contains(".wav") || file.toString().contains(".mp3")) {
            // WAV audio file
        } else if (file.toString().contains(".gif")) {
            // GIF file
        } else if (file.toString().contains(".jpg") || file.toString().contains(".jpeg")
                || file.toString().contains(".png")) {
            // JPG file
        } else if (file.toString().contains(".txt")) {
            // Text file
        } else if (file.toString().contains(".3gp") || file.toString().contains(".mpg")
                || file.toString().contains(".mpeg") || file.toString().contains(".mpe")
                || file.toString().contains(".mp4") || file.toString().contains(".avi")) {
            // Video files
        }
        return R.drawable.ic_file;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        ImageView mIcon;

        ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.filename);
            mIcon = (ImageView) view.findViewById(R.id.icon);
        }
    }
}
