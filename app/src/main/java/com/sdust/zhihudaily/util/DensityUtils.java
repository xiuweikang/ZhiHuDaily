package com.sdust.zhihudaily.util;

import android.content.Context;

/**
 * Created by Kevin on 2015/8/10.
 */
public class DensityUtils {

    /**
     * dp转px
     * @param context
     * @param dpValue
     * @return
     */
    public static int dipToPx(Context context, float dpValue) {

        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);

    }

    /**
     * px转到dp
     * @param context
     * @param pxValue
     * @return
     */
    public static int pxToDip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
