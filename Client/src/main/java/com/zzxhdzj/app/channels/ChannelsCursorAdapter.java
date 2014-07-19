package com.zzxhdzj.app.channels;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

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
            channelItemView.bindView(null);
        }
    }
}
