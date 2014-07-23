package com.zzxhdzj.app.channels;

import android.content.Context;
import android.database.Cursor;
import com.zzxhdzj.douban.db.tables.ChannelTable;
import com.zzxhdzj.douban.modules.channel.Channel;
import com.zzxhdzj.douban.modules.channel.ChannelBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 7/24/14
 * To change this template use File | Settings | File Templates.
 */
public class ChannelUtil {
    public static Channel DumpChannel(Cursor cursor) {
        return ChannelBuilder.aChannel()
                .withBanner(cursor.getString(Channel.BANNER_INDEX))
                .withCategory(cursor.getInt(Channel.CATEGORY_INDEX))
                .withCover(cursor.getString(Channel.COVER_INDEX))
                .withIntro(cursor.getString(Channel.INTRO_INDEX))
                .withId(cursor.getInt(Channel.CHANNEL_ID_INDEX))
                .withName(cursor.getString(Channel.NAME_INDEX))
                .withSongNum(cursor.getInt(Channel.SONG_NUM_INDEX))
                .build();
    }
    public static Channel queryChannel(int channelId, Context context) {

        Cursor cursor = context.getContentResolver()
                .query(ChannelTable.CONTENT_URI, Channel.CHANNEL_PROJECTION, ChannelTable.Columns.CHANNEL_ID + " = " + channelId, null, null);

        Channel channel = null;
        try {
            if (cursor != null && cursor.moveToFirst()) {
                channel = ChannelBuilder.aChannel()
                        .withBanner(cursor.getString(Channel.BANNER_INDEX))
                        .withCategory(cursor.getInt(Channel.CATEGORY_INDEX))
                        .withCover(cursor.getString(Channel.COVER_INDEX))
                        .withName(cursor.getString(Channel.NAME_INDEX))
                        .withId(cursor.getInt(Channel.CHANNEL_ID_INDEX))
                        .withIntro(cursor.getString(Channel.INTRO_INDEX))
                        .build();
            }
        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return channel;
    }
}
