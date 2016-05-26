
package com.sdust.zhihudaily.data.source.local;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.sdust.zhihudaily.data.model.DailyStories;
import com.sdust.zhihudaily.data.model.StartImage;
import com.sdust.zhihudaily.data.model.Story;
import com.sdust.zhihudaily.data.model.StoryExtra;
import com.sdust.zhihudaily.data.model.Theme;
import com.sdust.zhihudaily.data.model.Themes;

import java.util.List;


public interface CacheRepository {

	void getStartImage(Callback<StartImage> callback);
	void saveStartImage(int width, int height, DisplayImageOptions options,
						StartImage startImage);

	void getThemes(String url, Callback<Themes> callback);
	void saveThemes(Themes themes, String url);

	void getLatestDailyStories(String url, Callback<DailyStories> callback);
	void saveLatestDailyStories(DailyStories dailyStories, String url);

	void getBeforeDailyStories(String url, Callback<DailyStories> callback);
	void saveBeforeDailyStories(DailyStories dailyStories, String url);

	void getThemeStories(String url, Callback<Theme> callback);
	void saveThemeStories(Theme theme,String url);

	void getBeforeThemeStories(String url, Callback<Theme> callback);
	void saveBeforeThemesStories(Theme theme,String url);

	void getStoryDetail(String url, Callback<Story> callback);
	void saveStoryDetail(Story story, String url);

	void getStroyExtra(String url, Callback<StoryExtra> callback);
	void saveStoryExtra(StoryExtra story,String url);

	boolean isCollected(String storyId);
	void saveStory(Story story);
	List<Story> getAllCollectedStory();
	void deleteCollected(String storyId);

	public interface Callback<T> {
		public void success(T t);

		public void failure(Exception e);
	}
}
