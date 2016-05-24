package com.sdust.zhihudaily.util;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * Created by Kevin on 16/5/24.
 */
public class ImageLoaderUtils {

    public static DisplayImageOptions getImageOptions() {
        return new DisplayImageOptions.Builder().cacheInMemory(false)
                .cacheOnDisk(true).considerExifParams(true).build();
    }
}
