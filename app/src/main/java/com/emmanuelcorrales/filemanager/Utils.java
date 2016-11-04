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
        if (isDocument(file)) {
            intent.setDataAndType(uri, "application/msword");
        } else if (file.toString().contains(".pdf")) {
            // PDF file
            intent.setDataAndType(uri, "application/pdf");
        } else if (isPowerPoint(file)) {
            // Powerpoint file
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        } else if (isExcel(file)) {
            intent.setDataAndType(uri, "application/vnd.ms-excel");
        } else if (file.toString().contains(".zip") || file.toString().contains(".rar")) {
            // WAV audio file
            intent.setDataAndType(uri, "application/x-wav");
        } else if (file.toString().contains(".rtf")) {
            // RTF file
            intent.setDataAndType(uri, "application/rtf");
        } else if (isMusic(file)) {
            intent.setDataAndType(uri, "audio/x-wav");
        } else if (file.toString().contains(".gif")) {
            intent.setDataAndType(uri, "image/gif");
        } else if (isImage(file)) {
            intent.setDataAndType(uri, "image/jpeg");
        } else if (file.toString().contains(".txt")) {
            // Text file
            intent.setDataAndType(uri, "text/plain");
        } else if (isVideo(file)) {
            intent.setDataAndType(uri, "video/*");
        } else if (isAPK(file)) {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
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
        if (isDocument(file)) {
            return R.drawable.ic_doc;
        } else if (file.toString().contains(".pdf")) {
            // PDF file
        } else if (isPowerPoint(file)) {
            return R.drawable.ic_ppt;
        } else if (isExcel(file)) {
            return R.drawable.ic_xls;
        } else if (file.toString().contains(".zip") || file.toString().contains(".rar")) {
            // WAV audio file
        } else if (file.toString().contains(".rtf")) {
            // RTF file
        } else if (isMusic(file)) {
            return R.drawable.ic_music;
        } else if (file.toString().contains(".gif")) {
            // GIF file
        } else if (isImage(file)) {
            return R.drawable.ic_image;
        } else if (file.toString().contains(".txt")) {
            // Text file
        } else if (isVideo(file)) {
            return R.drawable.ic_video;
        } else if (isAPK(file)) {
            return R.drawable.ic_apk;
        }
        return R.drawable.ic_file;
    }

    public static boolean isDocument(File file) {
        return file.toString().contains(".doc") || file.toString().contains(".docx");
    }

    public static boolean isPowerPoint(File file) {
        return file.toString().contains(".ppt") || file.toString().contains(".pptx");
    }

    public static boolean isExcel(File file) {
        return file.toString().contains(".xls") || file.toString().contains(".xlsx");
    }

    public static boolean isMusic(File file) {
        return file.toString().contains(".wav") || file.toString().contains(".mp3");
    }

    public static boolean isImage(File file) {
        return file.toString().contains(".jpg") || file.toString().contains(".jpeg")
                || file.toString().contains(".png");
    }

    public static boolean isVideo(File file) {
        return file.toString().contains(".3gp") || file.toString().contains(".mpg")
                || file.toString().contains(".mpeg") || file.toString().contains(".mpe")
                || file.toString().contains(".mp4") || file.toString().contains(".avi");
    }

    public static boolean isAPK(File file) {
        return file.toString().contains(".apk");
    }
}
