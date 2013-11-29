Douban FM API
---
Usage:

install

	mvn install

import

pre dependency:

	<dependency>
	    <groupId>com.zzxhdzj.http</groupId>
	    <artifactId>HiHttp</artifactId>
	    <version>1.0-SNAPSHOT</version>
	</dependency>

SDK dependency:

	<dependency>
	    <groupId>com.zzxhdzj.douban</groupId>
	    <artifactId>DoubanFM-sdk</artifactId>
	    <version>1.0-SNAPSHOT</version>
	</dependency>

TODO:

	· 查询频道信息
	· 登录后的操作
#公共API

无需登录授权,url get 参数需要urlencode
##获取验证码id

API:

	http://douban.fm/j/new_captcha

Request:

	Method:GET
	Content-Type:application/x-www-form-urlencoded


Response:

	Server:nginx
	Content-Type:application/json; charset=utf-8
	Set-Cookie:bid="3O4V/vRO+uE"; domain=.douban.fm; path=/; expires=Mon, 24-Nov-2014 14:25:20 GMT,ac="1385303119"; path=/; domain=.douban.fm
	Body:
	"8Z9w6tODHEukHkAmBz52dWg4:en"

##获取验证码图片

API:

	http://douban.fm/misc/captcha


Request:
	
	Method:GET
	Content-Type:application/x-www-form-urlencoded
	Cookie：ac="1385303119"; bid="3O4V/vRO+uE"; ck="deleted"; dbcl2="deleted"; flag="ok"; start=

GET Params:

	size:m
	id:获取到的验证码id

Response：

	Content-Type:image/jpeg
##登录

API

	http://douban.fm/j/login

Request:
	
	Method:POST
	Content-Type:application/x-www-form-urlencoded


POST Params:

	remember:on/off
	source:radio
	captcha_solution:cheese
	alias:xxxx%40gmail.com
	form_password:password
	captcha_id:jOtEZsPFiDVRR9ldW3ELsy57%3en

Response：

	Content-Type:application/json; charset=utf-8
	ue="xxxx@gmail.com"; bid="ErJySOFkyp4"; path=/; domain=.douban.com; expires=Tue, 25-Nov-2014 18:02:37 GMT,dbcl2="69077079:YhfWsJoFZ00"; path=/; domain=.douban.fm; expires=Wed, 25-Dec-2013 18:02:37 GMT; httponly,ck="10se"; path=/; domain=.douban.fm
	OK_Body:
	{
		"user_info":
		{
		"ck":"10se",
		"play_record":{"fav_chls_count":2,"liked":58,"banned":44,"played":1715},"is_new_user":0,"uid":"69077079","third_party_info":null,"url":"http:\/\/www.douban.com\/people\/69077079\/","is_dj":false,"id":"69077079","is_pro":false,"name":"hijack"
		},
		"r":0
	}
	Failed_Body：{"err_no":1011,"r":1,"err_msg":"验证码不正确|xxx|xxx"}
	
#获取频道列表


###热门兆赫：
API

	/j/explore/hot_channels

GET:

	start=1&limit=6

	Body:
	{
	    "data": {
	        "channels": [
	            {
	                "banner": "http://img3.douban.com/img/fmadmin/chlBanner/27675.jpg",
	                "cover": "http://img3.douban.com/img/fmadmin/icon/27675.jpg",
	                "creator": {
	                    "id": 48254923,
	                    "name": "Bin's",
	                    "url": "http://www.douban.com/people/48254923/"
	                },
	                "hot_songs": [
	                    "\u539f\u8c05",
	                    "Better Me (\u56fd\u8bed)",
	                    "\u9b54\u9b3c\u4e2d\u7684\u5929\u4f7f"
	                ],
	                "id": 1000382,
	                "intro": "\u7ec6\u542c\u7740\u522b\u4eba\u7684\u6b4c\u58f0\uff0c\u8bc9\u8bf4\u7740\u81ea\u5df1\u7684\u6545\u4e8b\u3002",
	                "name": "\u4f60\u7684\u6b4c\u8bcd\u6211\u7684\u6545\u4e8b",
	                "song_num": 384
	            }
	        ],
	        "total": 23743
	    },
	    "status": true
	}
###上升最快    

API
	
	/j/explore/up_trending_channels

GET:

	start=1&limit=6

	Body:
	{
	    "data": {
	        "channels": [
	            {
	                "banner": "http://img3.douban.com/img/fmadmin/chlBanner/27675.jpg",
	                "cover": "http://img3.douban.com/img/fmadmin/icon/27675.jpg",
	                "creator": {
	                    "id": 48254923,
	                    "name": "Bin's",
	                    "url": "http://www.douban.com/people/48254923/"
	                },
	                "hot_songs": [
	                    "\u539f\u8c05",
	                    "Better Me (\u56fd\u8bed)",
	                    "\u9b54\u9b3c\u4e2d\u7684\u5929\u4f7f"
	                ],
	                "id": 1000382,
	                "intro": "\u7ec6\u542c\u7740\u522b\u4eba\u7684\u6b4c\u58f0\uff0c\u8bc9\u8bf4\u7740\u81ea\u5df1\u7684\u6545\u4e8b\u3002",
	                "name": "\u4f60\u7684\u6b4c\u8bcd\u6211\u7684\u6545\u4e8b",
	                "song_num": 384
	            }
	        ],
	        "total": 23743
	    },
	    "status": true
	}

###品牌兆赫    
API：
	http://douban.fm/#

Resp：Html解析

	window.com_channels_json = [
		{"intro":"每首歌曲都是一个遥远的地址，就让心灵掌舵，朝着闪念的方向，音符会带你向前，声音会为你指路。",
		"name":"朗境·闪念的声音","song_num":100,
		"creator":{"url":"http:\/\/site.douban.com\/douban.fm\/","name":"豆瓣FM","id":1},
		"banner":"http:\/\/img3.douban.com\/img\/fmadmin\/chlBanner\/27470.jpg",
		"cover":"http:\/\/img3.douban.com\/img\/fmadmin\/icon\/27470.jpg","id":159,
		"hot_songs":["皇后大道东","Casablanca","Tokyo"]
		}
	];

###已知固定频道
channel=0 私人兆赫  type=s

####Region&Lang

    channel=1 公共兆赫【地区 语言】：华语MHZ
    channel=6 公共兆赫【地区 语言】：粤语MHZ
    channel=2 公共兆赫【地区 语言】：欧美MHZ
    channel=22 公共兆赫【地区 语言】：法语MHZ
    channel=17 公共兆赫【地区 语言】：日语MHZ
    channel=18 公共兆赫【地区 语言】：韩语MHZ

####Ages

    channel=3  公共兆赫【年代】：70年代MHZ
    channel=4  公共兆赫【年代】：80年代MHZ
    channel=5  公共兆赫【年代】： 90年代MHZ

###Genre
    channel=8 公共兆赫【流派】：民谣MHZ
    channel=7 公共兆赫【流派】：摇滚MHZ
    channel=13 公共兆赫【流派】：爵士MHZ
    channel=27 公共兆赫【流派】：古典MHZ
    channel=14 公共兆赫【流派】：电子MHZ
    channel=16 公共兆赫【流派】：R&BMHZ
    channel=15 公共兆赫【流派】：说唱MHZ
    channel=10 公共兆赫【流派】：电影原声MHZ

####Special

    channel=20 公共兆赫【特辑】：女声MHZ
    channel=28 公共兆赫【特辑】：动漫MHZ
    channel=32 公共兆赫【特辑】：咖啡MHZ
    channel=67 公共兆赫【特辑】：东京事变MHZ

####Com

    channel=52 公共兆赫【品牌】：乐混翻唱MHZ
    channel=58 公共兆赫【品牌】：路虎揽胜运动MHZ

####Artist

    channel=26 公共兆赫：豆瓣音乐人MHZ
    channel=dj DJ兆赫

###根据流派获取频道

API：

http://douban.fm/j/explore/genre?gid=326&start=0&limit=20

已知流派：

	<ul class="fm-side-taglist clearfix">
            <li data-genre_id="335">摇滚</li>
            <li data-genre_id="326">古典</li>
            <li data-genre_id="327">爵士</li>
            <li data-genre_id="337">民谣/乡村</li>
            <li data-genre_id="331">流行</li>
            <li data-genre_id="325">电子</li>
            <li data-genre_id="328">原声配乐</li>
            <li data-genre_id="332">轻音乐</li>
            <li data-genre_id="334">说唱</li>
            <li data-genre_id="330">雷鬼</li>
            <li data-genre_id="329">拉丁</li>
            <li data-genre_id="333">世界音乐</li>
            <li data-genre_id="324">布鲁斯</li>
            <li data-genre_id="336">放克/灵歌/R&amp;B</li>
    </ul>		

Resp：

	{
	    "data": {
	        "channels": [
	            {
	                "banner": "http://img3.douban.com/img/fmadmin/chlBanner/27675.jpg",
	                "cover": "http://img3.douban.com/img/fmadmin/icon/27675.jpg",
	                "creator": {
	                    "id": 48254923,
	                    "name": "Bin's",
	                    "url": "http://www.douban.com/people/48254923/"
	                },
	                "hot_songs": [
	                    "\u539f\u8c05",
	                    "Better Me (\u56fd\u8bed)",
	                    "\u9b54\u9b3c\u4e2d\u7684\u5929\u4f7f"
	                ],
	                "id": 1000382,
	                "intro": "\u7ec6\u542c\u7740\u522b\u4eba\u7684\u6b4c\u58f0\uff0c\u8bc9\u8bf4\u7740\u81ea\u5df1\u7684\u6545\u4e8b\u3002",
	                "name": "\u4f60\u7684\u6b4c\u8bcd\u6211\u7684\u6545\u4e8b",
	                "song_num": 384
	            }
	        ],
	        "total": 23743
	    },
	    "status": true
	}

###查询频道信息
	API:

		http://douban.fm/j/explore/channel_detail?channel_id=159

	###搜索频道

	API

		/j/explore/search

	Get Params
	
		query=urlencodedParams
	Resp

		Body:
		{"status":true,"data":{"channels":
		[
			{"intro":"古典乐也能听得轻松随性",
			"name":"古典",
			"song_num":4189,
			"creator":{"url":"http:\/\/site.douban.com\/douban.fm\/","name":"豆瓣FM","id":1},
			"banner":"http:\/\/img3.douban.com\/img\/fmadmin\/chlBanner\/26382.jpg",
			"cover":"http:\/\/img3.douban.com\/img\/fmadmin\/icon\/26382.jpg",
			"id":27,
			"hot_songs":["Prelude","Antonin Dvorak, Humoresque","Canon and Gigue in D: Canon"]},
			....
		}
		],"total":5}}


#歌单
##根据频道获取歌曲列表
API

	http://douban.fm/j/mine/playlist

Request:

	Method:GET
	Request headers 
	User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36
	Content-Type: text/plain; charset=utf-8 
	Accept: */*
	Accept-Encoding: gzip,deflate,sdch
	Accept-Language: en-US,en;q=0.8

Get Params:

	from=mainsite&channel=1&kbps=128&type=n

Response:

	Server: nginx 
	Content-Type: application/json; charset=utf-8 
	Body:	
	{"r":0,"warning":"user_is_ananymous",
	"song":[
		{"album":"\/subject\/1427374\/","picture":"http:\/\/img3.douban.com\/mpic\/s1441645.jpg","ssid":"6eae","artist":"陈绮贞","url":"http:\/\/mr3.douban.com\/201311260130\/5b668f132f85c33265963e21db190b76\/view\/song\/small\/p191887.mp3","company":"Avex","title":"旅行的意义","rating_avg":4.47197,"length":258,"subtype":"","public_time":"2005","sid":"191887","aid":"1427374","sha256":"1fde4cd188ed5e26e7d2165d74061a74feadf0264160f666ff937b56de89e35d","kbps":"64","albumtitle":"华丽的冒险","like":0},
		{"album":"\/subject\/2142526\/","picture":"http:\/\/img3.douban.com\/mpic\/s4698431.jpg","ssid":"19ab","artist":"卢巧音 \/ 王力宏","url":"http:\/\/mr4.douban.com\/201311260130\/388359d544874dae23510dcbd8b0676e\/view\/song\/small\/p479453.mp3","company":"EMI","title":"好心分手 – DUET WITH 王力宏","rating_avg":4.30213,"length":181,"subtype":"","public_time":"2007","sid":"479453","aid":"2142526","sha256":"967129b2724ca3bdfcc4a16c22f362ef7c01a1b69351e4481ebced0b08b7a32d","kbps":"64","albumtitle":"不能不愛... 盧巧...","like":0}
		]
	}

#私人API

需要登录

##红心

##取消红心

##不再播放

##PRO用户