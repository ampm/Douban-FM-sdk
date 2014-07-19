package com.zzxhdzj.app.channels;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.zzxhdzj.douban.R;
import com.zzxhdzj.douban.db.tables.ChannelTable;
import com.zzxhdzj.douban.modules.channel.Channel;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 7/19/14
 * To change this template use File | Settings | File Templates.
 */
public class ChannelListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String KEY_CHANNEL_CATEGORY_NAME = "key_channel_category_name";
    public static final String KEY_CHANNEL_CATEGORY_ID = "key_channel_category_id";
    private static final int LOAD_ID_QUERY_CHANNELS_BY_CATEGORY = 1;
    @InjectView(R.id.channel_category_name)
    TextView mChannelCategoryName;
    @InjectView(R.id.channels_list_view)
    ListView mChannelsListView;
    ChannelsCursorAdapter mChannelsCursorAdapter;
    private String categoryId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channel_detail, container, false);
        ButterKnife.inject(this, view);
        mChannelsCursorAdapter = new ChannelsCursorAdapter(getActivity(), null, false);
        mChannelsListView.setAdapter(mChannelsCursorAdapter);
        String categoryName = getArguments() != null ? getArguments().getString(KEY_CHANNEL_CATEGORY_NAME) : "";
        categoryId = getArguments() != null ? getArguments().getString(KEY_CHANNEL_CATEGORY_ID) : "";
        mChannelCategoryName.setText(categoryName);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOAD_ID_QUERY_CHANNELS_BY_CATEGORY, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        if (loaderId == LOAD_ID_QUERY_CHANNELS_BY_CATEGORY) {
            String selection = ChannelTable.Columns.CATEGORY_ID + " = ?";
            return new CursorLoader(getActivity(), ChannelTable.CONTENT_URI,
                    Channel.CHANNEL_PROJECTION,
                    selection, new String[]{categoryId},
                    null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == LOAD_ID_QUERY_CHANNELS_BY_CATEGORY) {
            mChannelsCursorAdapter.swapCursor(data);
            mChannelsCursorAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (loader.getId() == LOAD_ID_QUERY_CHANNELS_BY_CATEGORY) {
            mChannelsCursorAdapter.swapCursor(null);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(channelListFragmentListener!=null){
            channelListFragmentListener.onChannelListFragmentDestroy();
        }
    }

    ChannelListFragmentListener channelListFragmentListener;

    public void setChannelListFragmentListener(ChannelListFragmentListener channelListFragmentListener) {
        this.channelListFragmentListener = channelListFragmentListener;
    }

    public interface ChannelListFragmentListener{
        void onChannelListFragmentDestroy();
    }
}
