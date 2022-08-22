package com.musongzi.core.util;


import android.content.pm.PackageManager;
import android.os.Environment;

import androidx.core.app.ActivityCompat;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by linhui on 2017/10/18.
 */
public final class WriteTxt {


    public static final File PARENT_FILE = new File(ActivityThreadHelp.getCurrentApplication().getFilesDir(),
            ActivityThreadHelp.getCurrentApplication().getPackageName() + "_dir");

    private static final File FILE = new File(PARENT_FILE, "defualt.txt");
    private static final File ERROR_FILE = new File(PARENT_FILE, "error.txt");
    private static final String TGA = "WriteTxt";

    private static final long FILE_MAX_LENGHT = 10 * 1024 * 1024;

    private static final long ERROR_MAX_LENGHT = 50 * 1024 * 1024;

    static {
        if (!PARENT_FILE.exists()) {
            try {
                PARENT_FILE.mkdirs();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


        if (!FILE.exists()) {
            try {
                FILE.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                checkFileCacheLenght(FILE, FILE_MAX_LENGHT);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (!ERROR_FILE.exists()) {
            try {
                ERROR_FILE.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                checkFileCacheLenght(FILE, ERROR_MAX_LENGHT);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void checkFileCacheLenght(final File file, long fileMaxLenght) throws Exception {

        if (file != null && file.exists() && file.length() > fileMaxLenght) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    file.delete();
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

//        if (file.length() >= fileMaxLenght / 2) {
//            RandomAccessFile randomAccessFile=  new RandomAccessFile(file,"rw");
//            randomAccessFile.seek(fileMaxLenght/2);
//            randomAccessFile.wr
//        }


    }


    public static <T> void wirte(T adShowErrorEntity) {
//        if (GodDebug.isDebug()) {
            w(new Gson().toJson(adShowErrorEntity), FILE);
//        }
    }

    public static void wirte(String s) {
//        if (GodDebug.isDebug()) {
            w(s, FILE);
//        }
    }

    public static void w(String text, File file) {
        try {
            if (ActivityCompat.checkSelfPermission(ActivityThreadHelp.getCurrentApplication(), WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n*********** start " + time() + " ***********\n");
            stringBuilder.append(kernelConvertString(text));
            stringBuilder.append("\n*********** end  ***********");
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            byte[] bytes = stringBuilder.toString().getBytes();
            fileOutputStream.write(bytes);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String time() {

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());

    }

    public static void exception(Throwable e) {

        StringBuilder stringBuilder = new StringBuilder();
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        stringBuilder.append(e.getMessage());
        for (StackTraceElement s : stackTraceElements) {
            stringBuilder.append("\n");
            stringBuilder.append(s.toString());
        }
        w(stringBuilder.toString(), ERROR_FILE);
    }

    private static String kernelConvertString(String s) {
        try {
//            return RsaUtil.encrypt(s);
        } catch (Exception ex) {
            ex.printStackTrace();
//            Log.i(TGA, "kernelConvertString: " + ex.getMessage());

        }
        return s;
    }

}
