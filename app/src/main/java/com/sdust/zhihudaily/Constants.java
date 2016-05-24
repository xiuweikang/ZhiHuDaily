
package com.sdust.zhihudaily;

/**
 * Created by Kevin on 2015/7/5.
 */
public class Constants {



	public static final String GITGUB_PROJECT = "https://github.com/xiuweikang/ZhiHuDaily";

	/**
	 * Datebases Constants
	 */
	public static final String DATABASE_NAME = "DailyDB";
	public static final int DATABASE_VERSION = 2;

	/**
	 * HTTP Constants
	 */
	public static final int IMAGE_CACHE_SIZE = 50 * 1024 * 1024;//Image_Loader的图片缓存
	public static final int HTTP_CACHE_SIZE = 50 * 1024 * 1024;//HTTP缓存
	public static final int HTTP_CONNECT_TIMEOUT = 1000 * 6;//HTTP连接超时
	public static final int HTTP_READ_TIMEOUT = 1000 * 6;//HTTP读取超时
	
}
