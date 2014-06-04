package com.zzxhdzj.douban.db;

import android.net.Uri;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy@snda.com
 * Date: 6/2/14
 * To change this template use File | Settings | File Templates.
 */
public class DbTables {
    public static final String _ID = "_id";
    public static final String AUTHORITY = "com.zzxhdzj.douban";

    public static class ChannelTable {

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
                    Columns.TYPE+ ","+
                    Columns.CATEGORY+ ")"+
                    " values "+
                    "(1,1, null,\"华语\",null,null,null,null,\"公共兆赫\",\"Region & Language\"),"+
                    "(2,6, null,\"粤语\",null,null,null,null,\"公共兆赫\",\"Region & Language\"),"+
                    "(3,2, null,\"欧美\",null,null,null,null,\"公共兆赫\",\"Region & Language\"),"+
                    "(4,22,null,\"法语\",null,null,null,null,\"公共兆赫\",\"Region & Language\"),"+
                    "(5,17,null,\"日语\",null,null,null,null,\"公共兆赫\",\"Region & Language\"),"+
                    "(6,18,null,\"韩语\",null,null,null,null,\"公共兆赫\",\"Region & Language\");";
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
            public static final String TYPE = "type";
            public static final String CATEGORY = "category";
        }
        public static String getCreateSQL() {
            String createString = TABLE_NAME +
                    "("+_ID+" INT PRIMARY KEY," +
                    Columns.CHANNEL_ID	+" INT," +
                    Columns.SONG_NUM +" INT," +
                    Columns.NAME	+" TEXT," +
                    Columns.BANNER	+" TEXT," +
                    Columns.INTRO	+" TEXT," +
                    Columns.HOT_SONGS +" TEXT," +
                    Columns.COVER +" TEXT," +
                    Columns.TYPE+" TEXT,"+
                    Columns.CATEGORY+" TEXT)";
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
}
