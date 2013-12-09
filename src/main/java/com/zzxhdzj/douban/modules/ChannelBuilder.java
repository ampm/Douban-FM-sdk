package com.zzxhdzj.douban.modules;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/9/13
 * Time: 7:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChannelBuilder {
    Channel channel = new Channel();

    private ChannelBuilder() {
    }

    public static ChannelBuilder aChannel() {
        return new ChannelBuilder();
    }

    public ChannelBuilder withIntro(String intro) {
        channel.setIntro(intro);
        return this;
    }

    public ChannelBuilder withId(int id) {
        channel.setId(id);
        return this;
    }

    public ChannelBuilder withCover(String cover) {
        channel.setCover(cover);
        return this;
    }

    public ChannelBuilder withName(String name) {
        channel.setName(name);
        return this;
    }

    public Channel build() {
       return channel;
    }
}
