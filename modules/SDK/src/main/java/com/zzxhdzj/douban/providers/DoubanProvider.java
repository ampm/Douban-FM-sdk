package com.zzxhdzj.douban.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;
import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.db.DoubanDb;
import com.zzxhdzj.douban.db.tables.ChannelTable;
import com.zzxhdzj.douban.db.tables.DbTable;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy@snda.com
 * Date: 6/2/14
 * To change this template use File | Settings | File Templates.
 */
public class DoubanProvider extends ContentProvider {
    private static final UriMatcher URI_MATCHER;
    private static final int CHANNELS = 1;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(DbTable.AUTHORITY, ChannelTable.TABLE_NAME, CHANNELS);
    }

    private SQLiteOpenHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = DoubanDb.getInstance(getContext()).getSQLiteOpenHelper();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        switch (URI_MATCHER.match(uri)) {
            case CHANNELS:
                qb.setTables(ChannelTable.TABLE_NAME);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI:" + uri);
        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        if(Constants.DEBUG == true){
            return new CursorDetector(cursor);
        }else return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (values == null) {
            values = new ContentValues();
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(URI_MATCHER.match(uri) == CHANNELS) {
            long rowId = db.insert(ChannelTable.TABLE_NAME, null, values);
            return ContentUris.withAppendedId(uri, rowId);
        }
        throw new IllegalArgumentException("Unknown URI:" + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        if (URI_MATCHER.match(uri) == CHANNELS) {
            count = db.delete(ChannelTable.TABLE_NAME, null, selectionArgs);
        } else {
            throw new IllegalArgumentException("Unknown URI:" + uri);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values == null) {
            return 0;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        if (URI_MATCHER.match(uri) == CHANNELS) {
            count = db.update(ChannelTable.TABLE_NAME, values, selection,selectionArgs);
        } else {
            throw new IllegalArgumentException("Unknown URI:" + uri);
        }
        return count;
    }

    private static class CursorDetector extends CursorWrapper {
        private static final String TAG = "CursorDetector";
        private boolean mIsClosed = false;
        private Throwable mTrace;

        public CursorDetector(Cursor c) {
            super(c);
            mTrace = new Throwable("Explicit termination method 'close()' not called");
        }

        @Override
        public void close() {
            mIsClosed = true;
        }

        @Override
        protected void finalize() throws Throwable {
            try {
                if (mIsClosed != true) {
                    Log.e(TAG, "Cursor leaks", mTrace);
                }
            } finally {
                super.finalize();
            }
        }
    }
}

