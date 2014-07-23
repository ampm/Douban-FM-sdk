package com.zzxhdzj.app.channels;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import com.zzxhdzj.douban.modules.channel.Channel;
import com.zzxhdzj.douban.modules.channel.ChannelBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 7/19/14
 * To change this template use File | Settings | File Templates.
 */
public class ChannelsCursorAdapter extends CursorAdapter {

    public ChannelsCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public ChannelsCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View channelItem = new ChannelItemView(context,null);
        channelItem.setTag(channelItem);
        return channelItem;
    }



    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ChannelItemView channelItemView = (ChannelItemView)view.getTag();
        if(channelItemView!=null){
            channelItemView.bindView(ChannelBuilder.aChannel()
                    .withBanner(cursor.getString(Channel.BANNER_INDEX))
                    .withCategory(cursor.getInt(Channel.CATEGORY_INDEX))
                    .withCover(cursor.getString(Channel.COVER_INDEX))
                    .withIntro(cursor.getString(Channel.INTRO_INDEX))
                    .withId(cursor.getInt(Channel.CHANNEL_ID_INDEX))
                    .withName(cursor.getString(Channel.NAME_INDEX))
                    .withSongNum(cursor.getInt(Channel.SONG_NUM_INDEX))
                    .build());
        }
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
    @Override
    public long getItemId(int position) {
        Cursor cursor = getCursor();
        if(cursor!=null&&cursor.moveToPosition(position)){
            return cursor.getLong(Channel.CHANNEL_ID_INDEX);
        }
        return -1;
    }
}
