
package com.sdust.zhihudaily.util;

import android.content.Context;
import android.os.Environment;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;


public class FileUtils {
	private static final String HTTP_CACHE_DIR = "http";

	public static File getHttpCacheDir(Context context) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return new File(context.getExternalCacheDir(), HTTP_CACHE_DIR);
		}
		return new File(context.getCacheDir(), HTTP_CACHE_DIR);
	}

	public static String getCacheSize() {
		File file = ImageLoader.getInstance().getDiskCache().getDirectory();
		long size = getFileSize(file);
		if (size >= 1 * 1024 * 1024) {
			String result = String.format("%.2f", size * 1.0 / (1024 * 1024));
			return " " + result + "M";
		} else {
			String result = String.format("%.2f", size * 1.0 / (1024));
			return " " + result + "K";
		}
	}
	private static long getFileSize(File file) {

		if (file == null)
			return 0;
		long sum = 0;

		if (file.isDirectory()) {
			File[] files = file.listFiles();

			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					sum += getFileSize(files[i]);
				} else {
					sum += files[i].length();
				}
			}
		} else {
			sum = file.length();
		}
		return sum;
	}


}
