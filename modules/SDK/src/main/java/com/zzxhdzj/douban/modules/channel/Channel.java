package com.zzxhdzj.douban.modules.channel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.zzxhdzj.douban.db.tables.ChannelTable;
import com.zzxhdzj.douban.db.tables.DbTable;
import com.zzxhdzj.douban.modules.Creator;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/28/13
 * Time: 12:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class Channel {
    protected Channel() {
    }

    public static final String[] RECEIPT_PROJECTION = new String[]{
            DbTable._ID,
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
}
