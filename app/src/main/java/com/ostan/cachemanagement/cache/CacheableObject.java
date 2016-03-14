package com.ostan.cachemanagement.cache;

import java.io.Serializable;

/**
 * Created by marco on 12/03/2016.
 */
public class CacheableObject implements Serializable{
    long dateAccessed;
    String localPath;

    public CacheableObject(long dateAccessed, String localPath) {
        this.dateAccessed = dateAccessed;
        this.localPath = localPath;
    }
}
