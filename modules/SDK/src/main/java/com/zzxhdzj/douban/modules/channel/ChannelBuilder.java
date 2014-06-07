package com.zzxhdzj.douban.modules.channel;

import com.zzxhdzj.douban.modules.Creator;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 6/3/14
 * To change this template use File | Settings | File Templates.
 */
public class ChannelBuilder {
    public String banner;
    public String cover;
    public Creator creator;
    public String[] hotSongs;
    public int id;
    public String intro;
    public String name;
    public int songNum;
    private int category;

    private ChannelBuilder() {
    }

    public static ChannelBuilder aChannel() {
        return new ChannelBuilder();
    }

    public ChannelBuilder withBanner(String banner) {
        this.banner = banner;
        return this;
    }

    public ChannelBuilder withCover(String cover) {
        this.cover = cover;
        return this;
    }

    public ChannelBuilder withCreator(Creator creator) {
        this.creator = creator;
        return this;
    }

    public ChannelBuilder withHotSongs(String[] hotSongs) {
        this.hotSongs = hotSongs;
        return this;
    }

    public ChannelBuilder withId(int id) {
        this.id = id;
        return this;
    }

    public ChannelBuilder withIntro(String intro) {
        this.intro = intro;
        return this;
    }

    public ChannelBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ChannelBuilder withSongNum(int songNum) {
        this.songNum = songNum;
        return this;
    }

    public ChannelBuilder withCategory(int category) {
        this.category = category;
        return this;
    }

    public Channel build() {
        Channel channel = new Channel();
        channel.setBanner(banner);
        channel.setCover(cover);
        channel.setCreator(creator);
        channel.setHotSongs(hotSongs);
        channel.setId(id);
        channel.setIntro(intro);
        channel.setName(name);
        channel.setSongNum(songNum);
        channel.setCategory(category);
        return channel;
    }

}
