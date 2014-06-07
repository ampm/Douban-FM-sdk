package com.zzxhdzj.douban.db.tables;


/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 6/5/14
 * To change this template use File | Settings | File Templates.
 */
public enum ChannelTypes {
    Region(1, "区域&语言", "Region & Language", true),
    Ages(2, "年代", "Ages", true),
    Genre(3, "流派", "Genre", true),
    Special(4, "特别推荐", "Special", true),
    Brand(5, "品牌", "Brand", true),
    Artist(6, "艺术家", "Artist", true),
    Trending(7, "上升最快", "Trending", false),
    Hits(8, "热门兆赫", "Hits", false),
    Try(9, "试试这些", "Try", false),;
    private final int index;
    private final String zhName;
    private final String enName;
    private final boolean isStatic;

    ChannelTypes(int index, String zhName, String enName, boolean isPublic) {
        this.index = index;
        this.zhName = zhName;
        this.enName = enName;
        this.isStatic = isPublic;
    }

    public int getIndex() {
        return index;
    }

    public String getZhName() {
        return zhName;
    }

    public String getEnName() {
        return enName;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public static String queryIndexString(boolean isStatic){
        StringBuilder builder = new StringBuilder();
        for (ChannelTypes value:ChannelTypes.values()){
            if(value.isStatic==isStatic){
                builder.append(value.getIndex()).append(",");
            }
        }
        if(builder.length()>0){
            builder.deleteCharAt(builder.length()-1);
        }
        return builder.toString();
    }
}
