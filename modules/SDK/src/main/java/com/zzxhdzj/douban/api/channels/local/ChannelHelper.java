package com.zzxhdzj.douban.api.channels.local;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager;
import android.os.Build;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 6/3/14
 * To change this template use File | Settings | File Templates.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ChannelHelper{
    private static final int QUERY_CHANNEL = 1;
    private Activity context;

    public ChannelHelper(Activity context) {
        this.context = context;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void queryChannels(LoaderManager.LoaderCallbacks<? extends Object> callback) {
        context.getLoaderManager().initLoader(QUERY_CHANNEL, null,callback);
    }
}
