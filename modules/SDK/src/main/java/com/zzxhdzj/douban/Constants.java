package com.zzxhdzj.douban;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 10/29/13
 * Time: 12:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class Constants {
    public static final boolean DEBUG = true;

    private static final String BASER_URL = "http://douban.fm";
    public static final String LOGIN_URL = BASER_URL + "/j/login";
    public static final String CAPTCHA_ID = BASER_URL + "/j/new_captcha";
    public static final String CAPTCHA_URL = BASER_URL + "/misc/captcha?size=l";
    public static final String INIT_URL = "http://www.douban.fm";

    public static final String HOT_CHANNELS = BASER_URL + "/j/explore/hot_channels";
    public static final String CHANNEL_DETAILS = BASER_URL + "/j/explore/channel_detail";
    public static final String SEARCH_URL = BASER_URL + "/j/explore/search";
    public static final String TRENDING_CHANNELS = BASER_URL + "/j/explore/up_trending_channels";
    public static final String GENRE_CHANNEL_URL = BASER_URL + "/j/explore/genre";
    public static final String SONGS_URL = BASER_URL + "/j/mine/playlist";
    public static final String DOUBAN_AUTH = "douban_fm_auth";
    public static final String LOGIN_CHLS_URL = BASER_URL + "/j/explore/get_login_chls";
    public static final String RECOMMEND_CHLS_URL = BASER_URL + "/j/explore/get_recommend_chl";
    public static final String CHANNEL_ACTION_URL = BASER_URL + "/j/explore/";
    public static final String FAV_CHANNELS = BASER_URL + "/j/fav_channels";
    public static final String HEART_NUM = BASER_URL + "/mine?type=played#!type=liked";
    public static final String TOTAL_NUM = BASER_URL + "/mine?type=played#!type=liked";
    public static boolean UNIT_TEST = false;
    public static final String USER_AGENT = "Mozilla/5.0 (iPhone; CPU iPhone OS 6_0 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53";

}