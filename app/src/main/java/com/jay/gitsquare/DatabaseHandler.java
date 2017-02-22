package com.jay.gitsquare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jay on 22-02-2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "GitSquare";
    private static final String TABLE_CONTRIBUTORS = "contributors";
    private static final String KEY_LOGIN = "login";
    private static final String KEY_ID = "id";
    private static final String KEY_AVATAR_URL = "avatar_url";
    private static final String KEY_GRAVATAR_ID = "gravatar_id";
    private static final String KEY_URL = "url";
    private static final String KEY_HTML_URL = "html_url";
    private static final String KEY_FOLLOWERS_URL = "followers_url";
    private static final String KEY_FOLLOWING_URL = "following_url";
    private static final String KEY_GISTS_URL = "gists_url";
    private static final String KEY_STARRED_URL = "starred_url";
    private static final String KEY_SUBSCRIPTIONS_URL = "subscriptions_url";
    private static final String KEY_ORGANIZATIONS_URL = "organizations_url";
    private static final String KEY_REPOS_URL = "repos_url";
    private static final String KEY_EVENTS_URL = "events_url";
    private static final String KEY_RECEIVED_EVENTS_URL = "received_events_url";
    private static final String KEY_TYPE = "type";
    private static final String KEY_SITE_ADMIN = "site_admin";
    private static final String KEY_CONTRIBUTIONS = "contributions";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTRIBUTORS + "("
                + KEY_LOGIN + " TEXT,"
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_AVATAR_URL + " TEXT,"
                + KEY_GRAVATAR_ID + " TEXT,"
                + KEY_URL + " TEXT,"
                + KEY_HTML_URL + " TEXT,"
                + KEY_FOLLOWERS_URL + " TEXT,"
                + KEY_FOLLOWING_URL + " TEXT,"
                + KEY_GISTS_URL + " TEXT,"
                + KEY_STARRED_URL + " TEXT,"
                + KEY_SUBSCRIPTIONS_URL + " TEXT,"
                + KEY_ORGANIZATIONS_URL + " TEXT,"
                + KEY_REPOS_URL + " TEXT,"
                + KEY_EVENTS_URL + " TEXT,"
                + KEY_RECEIVED_EVENTS_URL + " TEXT,"
                + KEY_TYPE + " TEXT,"
                + KEY_SITE_ADMIN + " INTEGER,"
                + KEY_CONTRIBUTIONS + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTRIBUTORS);
        onCreate(db);
    }

    public void addContributor(Contributor contributor) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LOGIN, contributor.getLogin());
        values.put(KEY_ID, contributor.getId());
        values.put(KEY_AVATAR_URL, contributor.getAvatarUrl());
        values.put(KEY_GRAVATAR_ID, contributor.getGravatarId());
        values.put(KEY_URL, contributor.getUrl());
        values.put(KEY_HTML_URL, contributor.getHtmlUrl());
        values.put(KEY_FOLLOWERS_URL, contributor.getFollowersUrl());
        values.put(KEY_FOLLOWING_URL, contributor.getFollowingUrl());
        values.put(KEY_GISTS_URL, contributor.getGistsUrl());
        values.put(KEY_STARRED_URL, contributor.getStarredUrl());
        values.put(KEY_SUBSCRIPTIONS_URL, contributor.getSubscriptionsUrl());
        values.put(KEY_ORGANIZATIONS_URL, contributor.getOrganizationsUrl());
        values.put(KEY_REPOS_URL, contributor.getReposUrl());
        values.put(KEY_EVENTS_URL, contributor.getEventsUrl());
        values.put(KEY_RECEIVED_EVENTS_URL, contributor.getReceivedEventsUrl());
        values.put(KEY_TYPE, contributor.getType());
        values.put(KEY_SITE_ADMIN, contributor.isSiteAdmin());
        values.put(KEY_CONTRIBUTIONS, contributor.getContributions());

        db.insert(TABLE_CONTRIBUTORS, null, values);
        db.close();
    }

    public Contributor getContributor(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTRIBUTORS, new String[] {
                        KEY_LOGIN,
                        KEY_ID,
                        KEY_AVATAR_URL,
                        KEY_GRAVATAR_ID,
                        KEY_URL,
                        KEY_HTML_URL,
                        KEY_FOLLOWERS_URL,
                        KEY_FOLLOWING_URL,
                        KEY_GISTS_URL,
                        KEY_STARRED_URL,
                        KEY_SUBSCRIPTIONS_URL,
                        KEY_ORGANIZATIONS_URL,
                        KEY_REPOS_URL,
                        KEY_EVENTS_URL,
                        KEY_RECEIVED_EVENTS_URL,
                        KEY_TYPE,
                        KEY_SITE_ADMIN,
                        KEY_CONTRIBUTIONS }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contributor contributor = new Contributor(
                cursor.getString(0),Integer.parseInt(cursor.getString(1)),
                cursor.getString(2),cursor.getString(3),
                cursor.getString(4),cursor.getString(5),
                cursor.getString(6),cursor.getString(7),
                cursor.getString(8),cursor.getString(9),
                cursor.getString(10),cursor.getString(11),
                cursor.getString(12),cursor.getString(13),
                cursor.getString(14),cursor.getString(15),
                Integer.parseInt(cursor.getString(16)) > 0, Integer.parseInt(cursor.getString(17))
        );
        return contributor;
    }

    public List<Contributor> getAllContributors() {
        List<Contributor> contributorList = new ArrayList<Contributor>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTRIBUTORS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Contributor contributor = new Contributor();
                contributor.setLogin(cursor.getString(0));
                contributor.setId(Integer.parseInt(cursor.getString(1)));
                contributor.setAvatarUrl(cursor.getString(2));
                contributor.setGravatarId(cursor.getString(3));
                contributor.setUrl(cursor.getString(4));
                contributor.setHtmlUrl(cursor.getString(5));
                contributor.setFollowersUrl(cursor.getString(6));
                contributor.setFollowingUrl(cursor.getString(7));
                contributor.setGistsUrl(cursor.getString(8));
                contributor.setStarredUrl(cursor.getString(9));
                contributor.setSubscriptionsUrl(cursor.getString(10));
                contributor.setOrganizationsUrl(cursor.getString(11));
                contributor.setReposUrl(cursor.getString(12));
                contributor.setEventsUrl(cursor.getString(13));
                contributor.setReceivedEventsUrl(cursor.getString(14));
                contributor.setType(cursor.getString(15));
                contributor.setSiteAdmin(Integer.parseInt(cursor.getString(16)) > 0);
                contributor.setContributions(Integer.parseInt(cursor.getString(17)));
                contributorList.add(contributor);
            } while (cursor.moveToNext());
        }
        return contributorList;
    }

    public int updateContributor(Contributor contributor) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LOGIN, contributor.getLogin());
        values.put(KEY_ID, contributor.getId());
        values.put(KEY_AVATAR_URL, contributor.getAvatarUrl());
        values.put(KEY_GRAVATAR_ID, contributor.getGravatarId());
        values.put(KEY_URL, contributor.getUrl());
        values.put(KEY_HTML_URL, contributor.getHtmlUrl());
        values.put(KEY_FOLLOWERS_URL, contributor.getFollowersUrl());
        values.put(KEY_FOLLOWING_URL, contributor.getFollowingUrl());
        values.put(KEY_GISTS_URL, contributor.getGistsUrl());
        values.put(KEY_STARRED_URL, contributor.getStarredUrl());
        values.put(KEY_SUBSCRIPTIONS_URL, contributor.getSubscriptionsUrl());
        values.put(KEY_ORGANIZATIONS_URL, contributor.getOrganizationsUrl());
        values.put(KEY_REPOS_URL, contributor.getReposUrl());
        values.put(KEY_EVENTS_URL, contributor.getEventsUrl());
        values.put(KEY_RECEIVED_EVENTS_URL, contributor.getReceivedEventsUrl());
        values.put(KEY_TYPE, contributor.getType());
        values.put(KEY_SITE_ADMIN, contributor.isSiteAdmin());
        values.put(KEY_CONTRIBUTIONS, contributor.getContributions());

        return db.update(TABLE_CONTRIBUTORS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contributor.getId()) });
    }

    public void deleteContributor(Contributor contributor) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTRIBUTORS, KEY_ID + " = ?",
                new String[] { String.valueOf(contributor.getId()) });
        db.close();
    }

    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTRIBUTORS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}
