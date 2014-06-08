package com.zzxhdzj.douban.api.channels.local;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import com.zzxhdzj.douban.db.tables.ChannelTable;
import com.zzxhdzj.douban.db.tables.ChannelTypes;
import com.zzxhdzj.douban.modules.channel.Channel;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 6/3/14
 * To change this template use File | Settings | File Templates.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ChannelHelper {
    private static final int QUERY_STATIC_CHANNEL = 1;
    private static final int QUERY_DYNAMIC_CHANNEL = 2;
    private Activity context;
    private ChannelHelperListener dynamicListener;
    private ChannelHelperListener staticListener;

    public ChannelHelper(Activity context) {
        this.context = context;
        context.getLoaderManager().initLoader(QUERY_STATIC_CHANNEL, null, callback);
        context.getLoaderManager().initLoader(QUERY_DYNAMIC_CHANNEL, null, callback);
    }


    LoaderManager.LoaderCallbacks<? extends Object> callback = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
            if (loaderId == QUERY_STATIC_CHANNEL) {
                String staticWhere = " category in ("+ChannelTypes.queryIndexString(true)+") ";
                return new CursorLoader(context, ChannelTable.CONTENT_URI,
                        Channel.RECEIPT_PROJECTION, staticWhere,null , null);
            } else if (loaderId == QUERY_DYNAMIC_CHANNEL) {
                String dynamicWhere = " category in ("+ChannelTypes.queryIndexString(false)+") ";
                return new CursorLoader(context, ChannelTable.CONTENT_URI,
                        Channel.RECEIPT_PROJECTION, dynamicWhere,null, null);
            }
            return null;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if (loader.getId() == QUERY_STATIC_CHANNEL) {
                if(staticListener!=null)staticListener.onResult(data);
            } else if (loader.getId() == QUERY_DYNAMIC_CHANNEL) {
                if(dynamicListener!=null)dynamicListener.onResult(data);
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
        }
    };

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void queryStaticChannels(final ChannelHelperListener listener) {
        staticListener = listener;
        context.getLoaderManager().restartLoader(QUERY_STATIC_CHANNEL, null, callback);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void queryDynamicChannels(final ChannelHelperListener listener) {
        dynamicListener = listener;
        context.getLoaderManager().restartLoader(QUERY_DYNAMIC_CHANNEL, null, callback);
    }


    public void update(Channel channel) {
        ContentValues value = new ContentValues();
        value.put(ChannelTable.Columns.SONG_NUM, channel.songNum);
        value.put(ChannelTable.Columns.NAME, channel.name);
        value.put(ChannelTable.Columns.BANNER, channel.banner);
        value.put(ChannelTable.Columns.INTRO, channel.intro);
        value.put(ChannelTable.Columns.COVER, channel.cover);
        if (channel.category > 0) {
            value.put(ChannelTable.Columns.CATEGORY_ID, channel.category);
        }
        String where = ChannelTable.Columns._ID + "=" + channel._id;
        context.getContentResolver().update(ChannelTable.CONTENT_URI, value, where, null);
    }

    /**
     * update而不reset的目的在于保留历史channel点击次数
     *
     * @param cursor
     * @param newChannels
     */
    public void createOrUpdateDynamicChannels(Cursor cursor, ArrayList<Channel> newChannels) {
        ArrayList<Channel> filteredToUpdate = new ArrayList<Channel>();
        ArrayList<Channel> filteredToInsert = newChannels;
        while (cursor.moveToNext()) {
            int channelId = cursor.getInt(Channel.CHANNEL_ID_INDEX);
            for (Channel ch : newChannels) {
                if (channelId == ch.id) {
                    ch._id = cursor.getInt(Channel.ID_INDEX);
                    filteredToUpdate.add(ch);
                    filteredToInsert.remove(ch);
                    break;
                }
            }
        }
        cursor.close();
        for (Channel channel : filteredToInsert) {
            ContentValues value = new ContentValues();
            value.put(ChannelTable.Columns.CHANNEL_ID, channel.id);
            value.put(ChannelTable.Columns.SONG_NUM, channel.songNum);
            value.put(ChannelTable.Columns.NAME, channel.name);
            value.put(ChannelTable.Columns.BANNER, channel.banner);
            value.put(ChannelTable.Columns.INTRO, channel.intro);
            value.put(ChannelTable.Columns.COVER, channel.cover);
            value.put(ChannelTable.Columns.CATEGORY_ID, channel.category);
            context.getContentResolver().insert(ChannelTable.CONTENT_URI, value);
        }
        for (Channel channel : filteredToUpdate) {
            update(channel);
        }
    }


    public interface ChannelHelperListener {
        void onResult(Cursor data);
    }
}
