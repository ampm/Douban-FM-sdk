package com.zzxhdzj.douban.modules.channel;

import android.content.Context;
import android.database.Cursor;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.zzxhdzj.douban.db.tables.ChannelTable;
import com.zzxhdzj.douban.modules.Creator;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/28/13
 * Time: 12:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class Channel {
    @Expose
    public int _id;

    protected Channel() {
    }

    public static final String[] CHANNEL_PROJECTION = new String[]{
            ChannelTable.Columns._ID,
            ChannelTable.Columns.CHANNEL_ID,
            ChannelTable.Columns.SONG_NUM,
            ChannelTable.Columns.NAME,
            ChannelTable.Columns.BANNER,
            ChannelTable.Columns.INTRO,
            ChannelTable.Columns.HOT_SONGS,
            ChannelTable.Columns.COVER,
            ChannelTable.Columns.GENRE,
            ChannelTable.Columns.CATEGORY_ID
    };
    public static final int ID_INDEX = 0;
    public static final int CHANNEL_ID_INDEX = 1;
    public static final int SONG_NUM_INDEX = 2;
    public static final int NAME_INDEX = 3;
    public static final int BANNER_INDEX = 4;
    public static final int INTRO_INDEX = 5;
    public static final int HOT_SONGS_INDEX = 6;
    public static final int COVER_INDEX = 7;
    public static final int TYPE_INDEX = 8;
    public static final int CATEGORY_INDEX = 9;
    public String banner;
    public String cover;
    public Creator creator;
    @SerializedName("hot_songs")
    public String[] hotSongs;
    public int id;
    public String intro;
    public String name;
    @SerializedName("song_num")
    public int songNum;

    @Expose
    public int category;

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public void setHotSongs(String[] hotSongs) {
        this.hotSongs = hotSongs;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSongNum(int songNum) {
        this.songNum = songNum;
    }

    public void setCategory(int category) {
        this.category = category;
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
