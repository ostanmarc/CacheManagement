package com.ostan.cachemanagement.cache;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * Created by marco on 13/03/2016.
 */
public class Utils {
    public static final String CACHE_INDEX_NAME = "cache_contents";
    protected static String CACHE_FOLDER_NAME = "cache";

    protected static void saveData(HashMap<String, CacheableObject> src, Activity activity) {

        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(activity.openFileOutput(CACHE_INDEX_NAME, Context.MODE_PRIVATE));
            outputStream.writeObject(src);
            outputStream.flush();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected static HashMap<String, CacheableObject> retrieveData(Activity activity) {
        HashMap<String, CacheableObject> result = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(activity.openFileInput(CACHE_INDEX_NAME));
            result = (HashMap<String, CacheableObject>) objectInputStream.readObject();

        } catch (Exception e) {
            e.printStackTrace();
            result = new HashMap<>();
        }
        return result;
    }

    protected static File initializeCacheDirectoryIfNeeded() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + CACHE_FOLDER_NAME);
        if (!file.exists()) {
            file.mkdir();
        }
        return file;

    }

    protected static void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }
}
