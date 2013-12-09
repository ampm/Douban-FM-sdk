package com.zzxhdzj.douban.api.mock;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 10/27/13
 * Time: 6:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestResponses {

    public static final String AUTH_SUCCESS = "{\n" +
            "    \"user_info\":\n" +
            "    {\n" +
            "    \"ck\":\"10se\",\n" +
            "    \"play_record\":{\"fav_chls_count\":2,\"liked\":58,\"banned\":44,\"played\":1715},\"is_new_user\":0,\"uid\":\"69077079\",\"third_party_info\":null,\"url\":\"http:\\/\\/www.douban.com\\/people\\/69077079\\/\",\"is_dj\":false,\"id\":\"69077079\",\"is_pro\":false,\"name\":\"hijack\"\n" +
            "    },\n" +
            "    \"r\":0\n" +
            "}";
    public static final String AUTH_ERROR = "{\"err_no\":1011,\"r\":1,\"err_msg\":\"验证码不正确\"}";
    public static final String NEW_CAPTCHA = "\"8Z9w6tODHEukHkAmBz52dWg4:en\"";
    public static final String ROCK_CHANNELS_SONGS_JSON = "{\n" +
            "    \"r\": 0,\n" +
            "    \"song\": [\n" +
            "        {\n" +
            "            \"aid\": \"25779410\",\n" +
            "            \"album\": \"/subject/25779410/\",\n" +
            "            \"albumtitle\": \"The 'In' Sounds O...\",\n" +
            "            \"artist\": \"Teddy Robin & The Playboys\",\n" +
            "            \"company\": \"Underground Masters\",\n" +
            "            \"kbps\": \"64\",\n" +
            "            \"length\": 162,\n" +
            "            \"like\": false,\n" +
            "            \"picture\": \"http://img3.douban.com/mpic/s27157905.jpg\",\n" +
            "            \"public_time\": \"2007\",\n" +
            "            \"rating_avg\": 3.72372,\n" +
            "            \"sha256\": \"5fca807c164c4181d1d8479bf2f67f48a35bf73080cbea6f88b97fbcab4956bd\",\n" +
            "            \"sid\": \"2002393\",\n" +
            "            \"ssid\": \"904a\",\n" +
            "            \"subtype\": \"\",\n" +
            "            \"title\": \"We Can't Go On This Way\",\n" +
            "            \"url\": \"http://mr4.douban.com/201311290052/74096848fdbf0bb8012cffbc4f86e543/view/song/small/p2002393.mp3\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"aid\": \"25772963\",\n" +
            "            \"album\": \"/subject/25772963/\",\n" +
            "            \"albumtitle\": \"Boy from the North\",\n" +
            "            \"artist\": \"Monica Heldal\",\n" +
            "            \"company\": \"Warner Music Norway\",\n" +
            "            \"kbps\": \"64\",\n" +
            "            \"length\": 280,\n" +
            "            \"like\": false,\n" +
            "            \"picture\": \"http://img5.douban.com/mpic/s27143769.jpg\",\n" +
            "            \"public_time\": \"2013\",\n" +
            "            \"rating_avg\": 3.73639,\n" +
            "            \"sha256\": \"0c9ca43945237143ae81b151c677bd362b699b68cadec7958bd59a8d781ba897\",\n" +
            "            \"sid\": \"2002186\",\n" +
            "            \"ssid\": \"c9d3\",\n" +
            "            \"subtype\": \"\",\n" +
            "            \"title\": \"Tape 03\",\n" +
            "            \"url\": \"http://mr3.douban.com/201311290052/8cbb24f03df16fb775ca5ec41d00e64e/view/song/small/p2002186.mp3\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";
    public static final String LOGIN_CHANNELS_JSON = "{\n" +
            "    \"data\": {\n" +
            "        \"res\": {\n" +
            "            \"fav_chls\": [\n" +
            "                {\n" +
            "                    \"banner\": \"http://img3.douban.com/img/fmadmin/chlBanner/27132.jpg\",\n" +
            "                    \"cover\": \"http://img3.douban.com/img/fmadmin/small/27132.jpg\",\n" +
            "                    \"creator\": {\n" +
            "                        \"id\": 1,\n" +
            "                        \"name\": \"\\u8c46\\u74e3FM\",\n" +
            "                        \"url\": \"http://site.douban.com/douban.fm/\"\n" +
            "                    },\n" +
            "                    \"hot_songs\": [\n" +
            "                        \"Free Loop\",\n" +
            "                        \"I'm Yours\",\n" +
            "                        \"You're Beautiful\"\n" +
            "                    ],\n" +
            "                    \"id\": 153,\n" +
            "                    \"intro\": \"\\u5de5\\u4f5c\\u5b66\\u4e60\\u7684\\u65f6\\u5019\\u542c\\u4ec0\\u4e48\",\n" +
            "                    \"name\": \"\\u5de5\\u4f5c\\u5b66\\u4e60\",\n" +
            "                    \"song_num\": 1\n" +
            "                }\n" +
            "            ],\n" +
            "            \"rec_chls\": [\n" +
            "                {\n" +
            "                    \"banner\": \"http://img3.douban.com/img/fmadmin/chlBanner/27134.jpg\",\n" +
            "                    \"cover\": \"http://img3.douban.com/img/fmadmin/small/27134.jpg\",\n" +
            "                    \"creator\": {\n" +
            "                        \"id\": 1,\n" +
            "                        \"name\": \"\\u8c46\\u74e3FM\",\n" +
            "                        \"url\": \"http://site.douban.com/douban.fm/\"\n" +
            "                    },\n" +
            "                    \"hot_songs\": [\n" +
            "                        \"The Show\",\n" +
            "                        \"Free Loop\",\n" +
            "                        \"Wake Me Up When September Ends\"\n" +
            "                    ],\n" +
            "                    \"id\": 151,\n" +
            "                    \"intro\": \"\\u6237\\u5916\\u7684\\u65f6\\u5019\\u542c\\u4ec0\\u4e48\",\n" +
            "                    \"name\": \"\\u6237\\u5916\",\n" +
            "                    \"song_num\": 1\n" +
            "                },\n" +
            "                {\n" +
            "                    \"banner\": \"http://img3.douban.com/img/fmadmin/chlBanner/27133.jpg\",\n" +
            "                    \"cover\": \"http://img3.douban.com/img/fmadmin/small/27133.jpg\",\n" +
            "                    \"creator\": {\n" +
            "                        \"id\": 1,\n" +
            "                        \"name\": \"\\u8c46\\u74e3FM\",\n" +
            "                        \"url\": \"http://site.douban.com/douban.fm/\"\n" +
            "                    },\n" +
            "                    \"hot_songs\": [\n" +
            "                        \"\\u9999\\u683c\\u91cc\\u62c9\",\n" +
            "                        \"Stupid Like This\",\n" +
            "                        \"\\u68a6\\u4e00\\u573a\"\n" +
            "                    ],\n" +
            "                    \"id\": 152,\n" +
            "                    \"intro\": \"\\u4f11\\u606f\\u7684\\u65f6\\u5019\\u542c\\u4ec0\\u4e48\",\n" +
            "                    \"name\": \"\\u4f11\\u606f\",\n" +
            "                    \"song_num\": 1\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    },\n" +
            "    \"status\": true\n" +
            "}\n";
    public static final String REC_CHANNELS_JSON = "{\n" +
            "\t    \"data\": {\n" +
            "\t        \"res\": {\n" +
            "\t            \"cover\": \"http://img3.douban.com/img/fmadmin/raw/23073\",\n" +
            "\t            \"id\": 1004097,\n" +
            "\t            \"intro\": \"Music has always fascinated us in the way that it communicates. Without words,without pictures. \",\n" +
            "\t            \"name\": \"JUST FEELING\"\n" +
            "\t        }\n" +
            "\t    },\n" +
            "\t    \"status\": true\n" +
            "\t}";
    public static String FAST_CHANNELS_JSON = "{\n" +
            "    \"data\": {\n" +
            "        \"channels\": [\n" +
            "            {\n" +
            "                \"banner\": \"http://img3.douban.com/img/fmadmin/chlBanner/27675.jpg\",\n" +
            "                \"cover\": \"http://img3.douban.com/img/fmadmin/icon/27675.jpg\",\n" +
            "                \"creator\": {\n" +
            "                    \"id\": 48254923,\n" +
            "                    \"name\": \"Bin's\",\n" +
            "                    \"url\": \"http://www.douban.com/people/48254923/\"\n" +
            "                },\n" +
            "                \"hot_songs\": [\n" +
            "                    \"\\u539f\\u8c05\",\n" +
            "                    \"Better Me (\\u56fd\\u8bed)\",\n" +
            "                    \"\\u9b54\\u9b3c\\u4e2d\\u7684\\u5929\\u4f7f\"\n" +
            "                ],\n" +
            "                \"id\": 1000382,\n" +
            "                \"intro\": \"\\u7ec6\\u542c\\u7740\\u522b\\u4eba\\u7684\\u6b4c\\u58f0\\uff0c\\u8bc9\\u8bf4\\u7740\\u81ea\\u5df1\\u7684\\u6545\\u4e8b\\u3002\",\n" +
            "                \"name\": \"\\u4f60\\u7684\\u6b4c\\u8bcd\\u6211\\u7684\\u6545\\u4e8b\",\n" +
            "                \"song_num\": 384\n" +
            "            }\n" +
            "        ],\n" +
            "        \"total\": 23743\n" +
            "    },\n" +
            "    \"status\": true\n" +
            "}";
    public static final String HOT_CHANNELS_JSON = "{\"status\":true,\"data\":{\"channels\":[{\"intro\":\"关于欧美流行音乐的一切，就在这里了\",\"name\":\"欧美\",\"song_num\":9224,\"creator\":{\"url\":\"http:\\/\\/site.douban.com\\/douban.fm\\/\",\"name\":\"豆瓣FM\",\"id\":1},\"banner\":\"http:\\/\\/img3.douban.com\\/img\\/fmadmin\\/chlBanner\\/26384.jpg\",\"cover\":\"http:\\/\\/img3.douban.com\\/img\\/fmadmin\\/icon\\/26384.jpg\",\"id\":2,\"hot_songs\":[\"The Show\",\"I'm Yours\",\"Free Loop\"]}],\"total\":23743}}";

    public static final String ROCK_CHANNELS_JSON = "{\"status\":true,\"data\":{\"channels\":[{\"intro\":\"Rock and Roll Ain't Noise Pollution，Welcome to the Jungle.\",\"name\":\"摇滚\",\"song_num\":8930,\"creator\":{\"url\":\"http:\\/\\/site.douban.com\\/douban.fm\\/\",\"name\":\"豆瓣FM\",\"id\":1},\"banner\":\"http:\\/\\/img5.douban.com\\/img\\/fmadmin\\/chlBanner\\/26389.jpg\",\"cover\":\"http:\\/\\/img5.douban.com\\/img\\/fmadmin\\/icon\\/26389.jpg\",\"id\":7,\"hot_songs\":[\"Hotel California\",\"Yellow\",\"It's My Life\"]}],\"total\":98}}";

}
