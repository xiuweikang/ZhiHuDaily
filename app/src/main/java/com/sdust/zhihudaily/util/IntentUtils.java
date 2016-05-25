
package com.sdust.zhihudaily.util;

import android.app.Activity;
import android.content.Intent;

import com.sdust.zhihudaily.R;
import com.sdust.zhihudaily.mainpage.MainActivity;
import com.sdust.zhihudaily.story.StoryActivity;
import com.sdust.zhihudaily.data.model.Story;


public class IntentUtils {
	public static final String EXTRA_STORY_ID = "extra_story_id";
	public static final String EXTRA_STORY_TITLE = "extra_story_title";
	public static final String EXTRA_STORY_IMAGES = "extra_story_images";
	public static final String EXTRA_STORY_MULTIPIC = "extra_story_multipic";//是否含多张图片


	public static void intentToMainActivity(Activity activity) {
		Intent intent = new Intent(activity,MainActivity.class);
		activity.startActivity(intent);
		activity.finish();
		activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
		
	}

	public static final void intentToStoryActivity(Activity activity, Story story) {
		Intent intent = new Intent(activity, StoryActivity.class);
		intent.putExtra(EXTRA_STORY_ID, story.getId());
		intent.putExtra(EXTRA_STORY_TITLE, story.getTitle());
		intent.putExtra(EXTRA_STORY_IMAGES, story.getImages() == null ? "" : story.getImages().get(0));
		intent.putExtra(EXTRA_STORY_MULTIPIC, story.getMultiPic());

		activity.startActivity(intent);
	}
	public static final void share(Activity activity, Story story) {
		StringBuilder sb = new StringBuilder();
		sb.append(story.getTitle()).append(" ")
				.append(activity.getString(R.string.share_link))
				.append(story.getShareUrl())
				.append(" ")
				.append(activity.getString(R.string.share_from));
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TITLE, story.getTitle());
		intent.putExtra(Intent.EXTRA_TEXT, sb.toString());
		activity.startActivity(intent);
	}

}
