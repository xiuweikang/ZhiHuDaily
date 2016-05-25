
package com.sdust.zhihudaily.data.source;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.sdust.zhihudaily.data.model.DailyStories;
import com.sdust.zhihudaily.data.model.StartImage;
import com.sdust.zhihudaily.data.model.Story;
import com.sdust.zhihudaily.data.model.Theme;
import com.sdust.zhihudaily.data.model.Themes;

import java.util.List;


public interface Repository {

	public interface Callback<T> {
		public void success(T t,boolean outDate);

		public void failure(Exception e);
	}
	void getThemes(Callback<Themes> callback);

	void getStartImage(int width, int height, DisplayImageOptions options,
			Callback<StartImage> callback);
	void getLatestDailyStories(Callback<DailyStories> callback);

	void getBeforeDailyStories(String date, Callback<DailyStories> callback);

	void getThemeStories(String themeId, Callback<Theme> callback);

	void getBeforeThemeStories(String themeId, String storyId, Callback<Theme> callback);


	void getStoryDetail(String storyId, Callback<Story> callback);

	boolean isCollected(String storyId);
	void saveStory(Story story);
	List<Story> getAllCollectedStory();
	void deleteCollected(String storyId);

}
