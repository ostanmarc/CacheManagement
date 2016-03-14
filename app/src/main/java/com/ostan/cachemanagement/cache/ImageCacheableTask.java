package com.ostan.cachemanagement.cache;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by marco on 14/03/2016.
 */
public class ImageCacheableTask<Drawable> extends AbstractCacheableTask<Drawable> {

    @Override
    public Drawable proccessIntput(String path) {
        File imgFile = new  File(path);
        Drawable result = null;
        if(imgFile.exists()){
            result = (Drawable) BitmapDrawable.createFromPath(imgFile.getAbsolutePath());
        }
        return result;
    }

    public ImageCacheableTask(Activity ctx, DataReceivedCallback callback) {
        super(ctx, callback);
    }


    public static void setNetworkImageToView(Activity activity, String url, final ImageView target, int defaultResource, final int fallbackImageResource){
        target.setImageResource(defaultResource);

        new ImageCacheableTask<android.graphics.drawable.Drawable>(activity, new ImageCacheableTask.DataReceivedCallback<android.graphics.drawable.Drawable>() {
            @Override
            public void onDataReceived(android.graphics.drawable.Drawable data) {
               if(data == null) {
                   target.setImageResource(fallbackImageResource);
               } else {
                   target.setImageDrawable(data);
               }
            }
        }).execute(url);
    }



}
