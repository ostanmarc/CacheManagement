package com.ostan.cachemanagement.cache;

import android.app.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marco on 12/03/2016.
 */
public class CacheManager {

    HashMap<String, CacheableObject> cacheMap;
    private static CacheManager instance = null;
    protected static long cacheTimeout;
    private static final String CACHE_FOLDER_NAME = Utils.CACHE_FOLDER_NAME;
    Activity activity;

    private CacheManager(Activity activity) {
        synchronized (CACHE_FOLDER_NAME)  {
            this.activity = activity;
            cacheMap = Utils.retrieveData(activity);
            checkCacheForObsolleteData();
        }
    }

    protected static CacheManager getInstance(Activity activity) {
        if (instance == null) {
            instance = new CacheManager(activity);
        }

        return instance;
    }

    private void checkCacheForObsolleteData(){

        List<String> oldUrls = new ArrayList<>();
        for (String keyIterator: cacheMap.keySet()) {
            if(System.currentTimeMillis() - cacheMap.get(keyIterator).dateAccessed > cacheTimeout) {
                oldUrls.add(keyIterator);
            }
        }

        for (String keyIterator: oldUrls) {
            cacheMap.remove(keyIterator);
            CacheableObject obj = cacheMap.get(keyIterator);
            Utils.deleteFile(obj.localPath);

        }

    }

    public CacheableObject getCacheable(String url) {
        return cacheMap.get(url);
    }

    public void addCacheable(String url, CacheableObject object) {
        cacheMap.put(url, object);
        Utils.saveData(cacheMap, activity);
    }


    public void updateCacheable(String url, CacheableObject object) {
        if (cacheMap.containsKey(url)) {
            cacheMap.remove(url);
        }
        cacheMap.put(url, object);
        Utils.saveData(cacheMap, activity);
    }

    /**
     * Initialize cache settings
     * @param cacheTimeout - timeout that the cached data will be deleted after
     * @param cacheFolderIsHidden - cache folder will be hidden if true
     * */
    public static void initialize(int cacheTimeout, boolean cacheFolderIsHidden){
       CacheManager.cacheTimeout = cacheTimeout * 24 * 3600 * 100;
       Utils.CACHE_FOLDER_NAME = cacheFolderIsHidden ? "." + CACHE_FOLDER_NAME : CACHE_FOLDER_NAME;
    }

}
