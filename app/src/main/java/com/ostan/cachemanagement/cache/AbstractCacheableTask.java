package com.ostan.cachemanagement.cache;

import android.app.Activity;
import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by marco on 12/03/2016.
 */
public abstract class AbstractCacheableTask<T> extends AsyncTask <String, Void, T> {


    public abstract T proccessIntput(String path);

    Activity activity;

    DataReceivedCallback callback;

    public AbstractCacheableTask(Activity activity, DataReceivedCallback callback) {
        this.activity = activity;
        this.callback = callback;
    }


    @Override
    protected T doInBackground(String... params) {
        String localPath;
        CacheableObject obj = CacheManager.getInstance(activity).getCacheable(params[0]);
        if( obj == null) {
            localPath = downLoadRemoteDataAndGetLocalPath(params[0], Utils.initializeCacheDirectoryIfNeeded());
            CacheManager.getInstance(activity).addCacheable(params[0], new CacheableObject(System.currentTimeMillis(), localPath));
            return proccessIntput(localPath);
        } else {
            obj.dateAccessed = System.currentTimeMillis();
            CacheManager.getInstance(activity).updateCacheable(params[0], obj);
            return proccessIntput(obj.localPath);
        }


    }

    @Override
    protected void onPostExecute(T t) {
        super.onPostExecute(t);
        callback.onDataReceived(t);
    }

    private String downLoadRemoteDataAndGetLocalPath(String url, File cacheFolder){
        String result = "";
        try {
            URL u = new URL(url);

            InputStream inputStream = u.openStream();
            result = cacheFolder.getAbsolutePath()+"/"+System.currentTimeMillis()+".tmp";
            File file = new File (result);
            DataOutputStream fos = new DataOutputStream(new FileOutputStream(file));

            byte[] buffer = new byte[1024];
            int temp = -1;
            while((temp = inputStream.read(buffer))!= -1) {
                fos.write(buffer, 0, temp);
            }
            fos.flush();
            fos.close();

        } catch(FileNotFoundException e) {
            return ""; // swallow a 404
        } catch (IOException e) {
            return ""; // swallow a 404
        }
        return result;
    }






    public interface DataReceivedCallback<T>{
        public void  onDataReceived(T data);
    }
}
