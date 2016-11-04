package com.emmanuelcorrales.filemanager;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

public class Utils {

    private Utils() {
    }

    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    public static boolean isExternalStorageWritable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static boolean isExternalStorageRoot(File directory) {
        if (directory == null) {
            throw new IllegalArgumentException("Argument 'directory' cannot be null.");
        }
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Argument 'directory' should be a directory.");
        }
        return directory.equals(Environment.getExternalStorageDirectory());
    }

    public static Intent createOpenFileIntent(File file) throws IOException {
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // Check what kind of file you are trying to open, by comparing the url with extensions.
        // When the if condition is matched, plugin sets the correct intent (mime) type,
        // so Android knew what application to use to open the file
        if (file.toString().contains(".doc") || file.toString().contains(".docx")) {
            // Word document
            intent.setDataAndType(uri, "application/msword");
        } else if (file.toString().contains(".pdf")) {
            // PDF file
            intent.setDataAndType(uri, "application/pdf");
        } else if (file.toString().contains(".ppt") || file.toString().contains(".pptx")) {
            // Powerpoint file
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        } else if (file.toString().contains(".xls") || file.toString().contains(".xlsx")) {
            // Excel file
            intent.setDataAndType(uri, "application/vnd.ms-excel");
        } else if (file.toString().contains(".zip") || file.toString().contains(".rar")) {
            // WAV audio file
            intent.setDataAndType(uri, "application/x-wav");
        } else if (file.toString().contains(".rtf")) {
            // RTF file
            intent.setDataAndType(uri, "application/rtf");
        } else if (file.toString().contains(".wav") || file.toString().contains(".mp3")) {
            // WAV audio file
            intent.setDataAndType(uri, "audio/x-wav");
        } else if (file.toString().contains(".gif")) {
            // GIF file
            intent.setDataAndType(uri, "image/gif");
        } else if (file.toString().contains(".jpg") || file.toString().contains(".jpeg")
                || file.toString().contains(".png")) {
            // JPG file
            intent.setDataAndType(uri, "image/jpeg");
        } else if (file.toString().contains(".txt")) {
            // Text file
            intent.setDataAndType(uri, "text/plain");
        } else if (file.toString().contains(".3gp") || file.toString().contains(".mpg")
                || file.toString().contains(".mpeg") || file.toString().contains(".mpe")
                || file.toString().contains(".mp4") || file.toString().contains(".avi")) {
            // Video files
            intent.setDataAndType(uri, "video/*");
        } else {
            //if you want you can also define the intent type for any other file

            //additionally use else clause below, to manage other unknown extensions
            //in this case, Android will show all applications installed on the device
            //so you can choose which application to use
            intent.setDataAndType(uri, "*/*");
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public static int getFileDrawableResource(File file) {
        if (file.toString().contains(".doc") || file.toString().contains(".docx")) {
            return R.drawable.ic_doc;
        } else if (file.toString().contains(".pdf")) {
            // PDF file
        } else if (file.toString().contains(".ppt") || file.toString().contains(".pptx")) {
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
            return R.drawable.ic_image;
        } else if (file.toString().contains(".txt")) {
            // Text file
        } else if (file.toString().contains(".3gp") || file.toString().contains(".mpg")
                || file.toString().contains(".mpeg") || file.toString().contains(".mpe")
                || file.toString().contains(".mp4") || file.toString().contains(".avi")) {
            return R.drawable.ic_video;
        }
        return R.drawable.ic_file;
    }
}
