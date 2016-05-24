package com.sdust.zhihudaily.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sdust.zhihudaily.data.model.Story;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 2015/8/11.
 */
public class CollectedDao {
    public static final String TABLE_NAME = "collect";
    private DBOpenHelper mHelper;

    public CollectedDao(Context context) {
        mHelper = DBOpenHelper.getInstance(context);
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint
                + TABLE_NAME
                + " (" +
                "story_id TEXT PRIMARY KEY ," + // 0: 文章ID
                "story_title TEXT," + // 1: 文章标题
                "story_image TEXT," + // 2: 文章Image的URL
                "story_multipic TEXT);"); // 3: 文章是否包含多张图片
    }


    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + TABLE_NAME;
        db.execSQL(sql);
    }

    public void insertCollected(Story story) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String imagesUrl = story.getImages().get(0);
        db.execSQL("insert into "
                        + TABLE_NAME
                        + " (story_id, story_title, story_image,story_multipic) values(?,?,?,?)",
                new Object[]{story.getId(), story.getTitle(), imagesUrl, story.getMultiPic()});
        db.close();
    }

    public void deleteCollected(String id) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("delete from "
                        + TABLE_NAME
                        + " where story_id = ?",
                new Object[]{id});
        db.close();
    }


    public List<Story> getAllCollected() {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        List<Story> stories = new ArrayList<Story>();
        Cursor cursor = db.rawQuery("select * from "
                + TABLE_NAME, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Story story = new Story();
                story.setId(cursor.getString(cursor.getColumnIndex("story_id")));
                story.setTitle(cursor.getString(cursor
                        .getColumnIndex("story_title")));
                String imageUrl = cursor.getString(cursor.getColumnIndex("story_image"));
                List<String> imageUrlList = new ArrayList<String>();
                imageUrlList.add(imageUrl);
                story.setImages(imageUrlList);
                story.setMultiPic(cursor.getString(cursor.getColumnIndex("story_multipic")));
                stories.add(story);
            }
        }

        if (cursor != null)
            cursor.close();
        return stories;
    }

    public boolean exists(String id) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "
                        + TABLE_NAME
                        + " where story_id = ?",
                new String[]{id});
        boolean isExist = cursor.moveToNext();

        if (cursor != null) {
            cursor.close();
        }

        db.close();
        return isExist;
    }


}
