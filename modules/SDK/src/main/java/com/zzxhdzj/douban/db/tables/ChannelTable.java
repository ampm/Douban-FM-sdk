package com.zzxhdzj.douban.db.tables;

import android.net.Uri;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 6/5/14
 * To change this template use File | Settings | File Templates.
 */
public class ChannelTable extends DbTable {

        public static final String TABLE_NAME = "fm_channels";
        public static final Uri CONTENT_URI = Uri
                .parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static String getInitializeDataSQL() {
            String initializeData = "insert into "+ TABLE_NAME +"("+
                    _ID+ ","+
                    Columns.CHANNEL_ID+ ","+
                    Columns.SONG_NUM+ ","+
                    Columns.NAME+ ","+
                    Columns.BANNER+ ","+
                    Columns.INTRO+ ","+
                    Columns.HOT_SONGS+ ","+
                    Columns.COVER+ ","+
                    Columns.GENRE+ ","+//有网络时，即时按genre搜索，无网络时可离线检索数据库中的缓存频道。
                    Columns.CATEGORY_ID+ ")"+
                    " values "+
                    "(1,1, null,\"华语\",null,null,null,null,null,"+ ChannelTypes.Region.getIndex()+"),"+
                    "(2,6, null,\"粤语\",null,null,null,null,null,"+ ChannelTypes.Region.getIndex()+"),"+
                    "(3,2, null,\"欧美\",null,null,null,null,null,"+ ChannelTypes.Region.getIndex()+"),"+
                    "(4,22,null,\"法语\",null,null,null,null,null,"+ ChannelTypes.Region.getIndex()+"),"+
                    "(5,17,null,\"日语\",null,null,null,null,null,"+ ChannelTypes.Region.getIndex()+"),"+
                    "(6,18,null,\"韩语\",null,null,null,null,null,"+ ChannelTypes.Region.getIndex()+"),"+

                    "(7,3,null,\"70年代\",null,null,null,null,null,"+ ChannelTypes.Ages.getIndex()+"),"+
                    "(8,4,null,\"80年代\",null,null,null,null,null,"+ ChannelTypes.Ages.getIndex()+"),"+
                    "(9,5,null,\"90年代\",null,null,null,null,null,"+ ChannelTypes.Ages.getIndex()+"),"+

                    "(10,8,null,\"民谣\",null,null,null,null,null,"+ ChannelTypes.Genre.getIndex()+"),"+
                    "(11,7,null,\"摇滚\",null,null,null,null,null,"+ ChannelTypes.Genre.getIndex()+"),"+
                    "(12,13,null,\"爵士\",null,null,null,null,null,"+ ChannelTypes.Genre.getIndex()+"),"+
                    "(13,27,null,\"古典\",null,null,null,null,null,"+ ChannelTypes.Genre.getIndex()+"),"+
                    "(14,14,null,\"电子\",null,null,null,null,null,"+ ChannelTypes.Genre.getIndex()+"),"+
                    "(15,16,null,\"R&B\",null,null,null,null,null,"+ ChannelTypes.Genre.getIndex()+"),"+
                    "(16,15,null,\"说唱\",null,null,null,null,null,"+ ChannelTypes.Genre.getIndex()+"),"+
                    "(17,10,null,\"电影原声\",null,null,null,null,null,"+ ChannelTypes.Genre.getIndex()+"),"+

                    "(18,20,null,\"女声\",null,null,null,null,null,"+ ChannelTypes.Special.getIndex()+"),"+
                    "(19,88,null,\"动漫\",null,null,null,null,null,"+ ChannelTypes.Special.getIndex()+"),"+
                    "(20,32,null,\"咖啡\",null,null,null,null,null,"+ ChannelTypes.Special.getIndex()+"),"+
                    "(21,67,null,\"东京事变\",null,null,null,null,null,"+ ChannelTypes.Special.getIndex()+"),"+

                    "(22,52,null,\"乐混翻唱\",null,null,null,null,null,"+ ChannelTypes.Brand.getIndex()+"),"+
                    "(23,58,null,\"陆虎揽胜运动\",null,null,null,null,null,"+ ChannelTypes.Brand.getIndex()+"),"+

                    "(24,26,null,\"豆瓣音乐人\",null,null,null,null,null,"+ ChannelTypes.Artist.getIndex()+"),"+
                    "(25,\"dj\",null,\"DJ兆赫\",null,null,null,null,null,"+ ChannelTypes.Artist.getIndex()+");";

            return initializeData;
        }


        public static class Columns {
            public static final String CHANNEL_ID = "channel_id";
            public static final String SONG_NUM = "song_num";
            public static final String NAME = "name";
            public static final String BANNER = "banner";
            public static final String INTRO = "intro";
            public static final String HOT_SONGS = "hot_songs";
            public static final String COVER = "cover";
            public static final String CATEGORY_ID = "category";
            public static final String GENRE = "genre";
        }
        public static String getCreateSQL() {
            String createString = TABLE_NAME +
                    "("+_ID+" INT PRIMARY KEY," +
                    Columns.CHANNEL_ID	+" TEXT," +
                    Columns.SONG_NUM +" INT," +
                    Columns.NAME	+" TEXT," +
                    Columns.BANNER	+" TEXT," +
                    Columns.INTRO	+" TEXT," +
                    Columns.HOT_SONGS +" TEXT," +
                    Columns.COVER +" TEXT," +
                    Columns.GENRE+" TEXT,"+
                    Columns.CATEGORY_ID+" INT)";
            return "CREATE TABLE " + createString;
        }

        public static String getDropSQL() {
            return "DROP TABLE " + TABLE_NAME;
        }

        public static String getCreateIndexSQL() {
            String createIndexSQL = "CREATE INDEX " + TABLE_NAME + "_idx ON "
                    + TABLE_NAME + " ( " + Columns.CHANNEL_ID + " );";
            return createIndexSQL;
        }
}
