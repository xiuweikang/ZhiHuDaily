package com.sdust.zhihudaily;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.sdust.zhihudaily.data.source.Repository;
import com.sdust.zhihudaily.data.source.RepositoryImp;
import com.squareup.leakcanary.LeakCanary;

/**
 * 覆写的Application
 */
public class ZhiHuApplication extends Application{
	
	private static Context applicationContext;
	
	private static Repository sRespository;//全局唯一的仓库
	@Override
	public void onCreate() {
		super.onCreate();

		applicationContext = getApplicationContext();
		LeakCanary.install(this);
		initImageLoader(getApplicationContext());
		
	}
	
	
	public static Context getContext() {
		return applicationContext;
	}
	public static Repository getRepository() {
		if(sRespository == null) {
			sRespository = new RepositoryImp(applicationContext);
		}
		return sRespository;
	}

	/**
	 * 配置ImageLoader
	 * @param context
	 */
	 private void initImageLoader(final Context context) {
	        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
	                .threadPriority(Thread.NORM_PRIORITY - 2)
	                .denyCacheImageMultipleSizesInMemory()
	                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
	                .diskCacheSize(Constants.IMAGE_CACHE_SIZE) // 50 Mb
	                .tasksProcessingOrder(QueueProcessingType.LIFO)
	                .build();
	        ImageLoader.getInstance().init(config);
	    }
	
	
	
} 