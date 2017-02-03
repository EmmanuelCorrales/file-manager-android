package com.emmanuelcorrales.filemanager;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.emmanuelcorrales.android.utils.FileUtils;

import java.io.File;
import java.io.IOException;

public class Utils {

    private Utils() {
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
        if (FileUtils.isDocument(file)) {
            intent.setDataAndType(uri, "application/msword");
        } else if (file.toString().contains(".pdf")) {
            // PDF file
            intent.setDataAndType(uri, "application/pdf");
        } else if (FileUtils.isPowerPoint(file)) {
            // Powerpoint file
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        } else if (FileUtils.isSpreadsheet(file)) {
            intent.setDataAndType(uri, "application/vnd.ms-excel");
        } else if (file.toString().contains(".zip") || file.toString().contains(".rar")) {
            // WAV audio file
            intent.setDataAndType(uri, "application/x-wav");
        } else if (file.toString().contains(".rtf")) {
            // RTF file
            intent.setDataAndType(uri, "application/rtf");
        } else if (FileUtils.isAudio(file)) {
            intent.setDataAndType(uri, "audio/x-wav");
        } else if (file.toString().contains(".gif")) {
            intent.setDataAndType(uri, "image/gif");
        } else if (FileUtils.isImage(file)) {
            intent.setDataAndType(uri, "image/jpeg");
        } else if (file.toString().contains(".txt")) {
            // Text file
            intent.setDataAndType(uri, "text/plain");
        } else if (FileUtils.isVideo(file)) {
            intent.setDataAndType(uri, "video/*");
        } else if (FileUtils.isAPK(file)) {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        } else if (FileUtils.isCalendar(file)) {
            intent.setDataAndType(uri, "text/calendar");
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
        if (FileUtils.isDocument(file)) {
            return R.drawable.ic_doc;
        } else if (file.toString().contains(".pdf")) {
            // PDF file
        } else if (FileUtils.isPowerPoint(file)) {
            return R.drawable.ic_ppt;
        } else if (FileUtils.isSpreadsheet(file)) {
            return R.drawable.ic_xls;
        } else if (file.toString().contains(".zip") || file.toString().contains(".rar")) {
            // WAV audio file
        } else if (file.toString().contains(".rtf")) {
            // RTF file
        } else if (FileUtils.isAudio(file)) {
            return R.drawable.ic_music;
        } else if (file.toString().contains(".gif")) {
            // GIF file
        } else if (FileUtils.isImage(file)) {
            return R.drawable.ic_image;
        } else if (file.toString().contains(".txt")) {
            // Text file
        } else if (FileUtils.isVideo(file)) {
            return R.drawable.ic_video;
        } else if (FileUtils.isAPK(file)) {
            return R.drawable.ic_apk;
        } else if (FileUtils.isCalendar(file)) {
            return R.drawable.ic_calendar;
        }
        return R.drawable.ic_file;
    }
}
