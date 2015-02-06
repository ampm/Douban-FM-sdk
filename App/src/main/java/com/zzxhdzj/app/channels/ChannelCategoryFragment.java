package com.zzxhdzj.app.channels;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.zzxhdzj.douban.R;
import com.zzxhdzj.douban.db.tables.ChannelTypes;

import static com.zzxhdzj.app.channels.ChannelListFragment.ChannelListFragmentListener;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 7/12/14
 * To change this template use File | Settings | File Templates.
 */
public class ChannelCategoryFragment extends Fragment implements ChannelListFragmentListener {
    @InjectView(R.id.btn_channel_item_region)
    TextView mBtnChannelItemRegion;
    @InjectView(R.id.btn_channel_item_ages)
    TextView mBtnChannelItemAges;
    @InjectView(R.id.btn_channel_item_genre)
    TextView mBtnChannelItemGenre;
    @InjectView(R.id.btn_channel_item_special)
    TextView mBtnChannelItemSpecial;
    @InjectView(R.id.btn_channel_item_genre)
    TextView mBtnQuickGenre;
    @InjectView(R.id.btn_channel_item_artist)
    TextView mBtnChannelItemArtist;
    @InjectView(R.id.btn_channel_item_trending)
    TextView mBtnChannelItemTrending;
    @InjectView(R.id.btn_channel_item_hits)
    TextView mBtnChannelItemHits;
    @InjectView(R.id.btn_channel_item_try)
    TextView mBtnChannelItemTry;
    @InjectView(R.id.channels_grid)
    RelativeLayout mChannelsGrid;
//    @InjectView(R.id.channels_grid_close_btn)
//    View mChannelsGridCloseBtn;
    @InjectView(R.id.btn_channel_item_brand)
    TextView mBtnChannelItemBrand;
    private ChannelFragmentListener listener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channels_category, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @OnClick({
            R.id.btn_channel_item_region,
            R.id.btn_channel_item_ages,
            R.id.btn_channel_item_genre,
            R.id.btn_channel_item_special,
            R.id.btn_channel_item_brand,
            R.id.btn_channel_item_artist,
            R.id.btn_channel_item_trending,
            R.id.btn_channel_item_hits,
            R.id.btn_channel_item_try
//            R.id.channels_grid_close_btn

    })
    public void registerOnClickListener(View view) {
        switch (view.getId()) {
            case R.id.btn_channel_item_region:
                mChannelsGrid.setVisibility(View.INVISIBLE);
                showChannelsInThisCategory(ChannelTypes.Region);
                break;
            case R.id.btn_channel_item_ages:
                mChannelsGrid.setVisibility(View.INVISIBLE);
                showChannelsInThisCategory(ChannelTypes.Ages);
                break;
            case R.id.btn_channel_item_genre:
                mChannelsGrid.setVisibility(View.INVISIBLE);
                showChannelsInThisCategory(ChannelTypes.Genre);
                break;
            case R.id.btn_channel_item_special:
                mChannelsGrid.setVisibility(View.INVISIBLE);
                showChannelsInThisCategory(ChannelTypes.Special);
                break;
            case R.id.btn_channel_item_brand:
                mChannelsGrid.setVisibility(View.INVISIBLE);
                showChannelsInThisCategory(ChannelTypes.Brand);
                break;
            case R.id.btn_channel_item_artist:
                mChannelsGrid.setVisibility(View.INVISIBLE);
                showChannelsInThisCategory(ChannelTypes.Artist);
                break;
            case R.id.btn_channel_item_trending:
                mChannelsGrid.setVisibility(View.INVISIBLE);
                showChannelsInThisCategory(ChannelTypes.Trending);
                break;
            case R.id.btn_channel_item_hits:
                mChannelsGrid.setVisibility(View.INVISIBLE);
                showChannelsInThisCategory(ChannelTypes.Hits);
                break;
            case R.id.btn_channel_item_try:
                mChannelsGrid.setVisibility(View.INVISIBLE);
                showChannelsInThisCategory(ChannelTypes.Try);
                break;
//            case R.id.channels_grid_close_btn:
//                getActivity().getSupportFragmentManager().popBackStack();
//                break;
            default:
                break;
        }
    }

    private void showChannelsInThisCategory(ChannelTypes channelType) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ChannelListFragment fragment = new ChannelListFragment();
        fragment.setChannelListFragmentListener(this);
        Bundle bundle = new Bundle();
        bundle.putString(ChannelListFragment.KEY_CHANNEL_CATEGORY_ID, channelType.getIndex() + "");
        bundle.putString(ChannelListFragment.KEY_CHANNEL_CATEGORY_NAME, channelType.getZhName() + "");
        fragment.setArguments(bundle);
        ft.replace(R.id.channels_grid_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(listener!=null)listener.onChannelFragmentDestroy();
    }

    public void setListener(ChannelFragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public void onChannelListFragmentDestroy() {
        mChannelsGrid.setVisibility(View.VISIBLE);
    }

    public interface ChannelFragmentListener {
        void onChannelFragmentDestroy();
    }
}
