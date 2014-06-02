package com.zzxhdzj.douban.modules.channel;

import com.google.gson.annotations.SerializedName;
import com.zzxhdzj.douban.db.DbTables;
import com.zzxhdzj.douban.modules.Creator;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/28/13
 * Time: 12:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class Channel {
    public static final String[] RECEIPT_PROJECTION = new String[]{
            DbTables._ID,
            DbTables.ChannelTable.Columns.CHANNEL_ID,
            DbTables.ChannelTable.Columns.SONG_NUM,
            DbTables.ChannelTable.Columns.NAME,
            DbTables.ChannelTable.Columns.BANNER,
            DbTables.ChannelTable.Columns.INTRO,
            DbTables.ChannelTable.Columns.HOT_SONGS,
            DbTables.ChannelTable.Columns.COVER,
            DbTables.ChannelTable.Columns.TYPE,
            DbTables.ChannelTable.Columns.CATEGORY
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
}
