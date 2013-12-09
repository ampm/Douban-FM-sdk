package com.zzxhdzj.douban.modules.channel;

import com.google.gson.annotations.SerializedName;
import com.zzxhdzj.douban.modules.Creator;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/28/13
 * Time: 12:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class Channel {
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
