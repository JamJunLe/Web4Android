package com.flyman.app.web4android.io.api;

public interface HomePageChildConstant {
    String HOME_PAGE = NetUrl.BASE_URL + "/essence/";//首页
    String HOME_PAGE_ITEM = NetUrl.BASE_URL + "/plus/list.php?tid=";//首页
    //
    String HOME_COMPREHENSIVE_INFO = HOME_PAGE_ITEM + "4";//http://www.jcodecraeer.com/plus/list.php?tid=4综合资讯
    String CODE_DESIGN = HOME_PAGE_ITEM + "6";//http://www.jcodecraeer.com/plus/list.php?tid=6 程序设计
    String ANDROID_DEV = HOME_PAGE_ITEM + "16";//http://www.jcodecraeer.com/plus/list.php?tid=16 安卓开发
    String WEB_DEV = HOME_PAGE_ITEM + "5";//http://http://www.jcodecraeer.com/plus/list.php?tid=5 前端开发
    String IOS_DEV = HOME_PAGE_ITEM + "27";//http://www.jcodecraeer.com/plus/list.php?tid=27 iOS开发
    String DATABASE_DEV = HOME_PAGE_ITEM + "14";//http://www.jcodecraeer.com/plus/list.php?tid=14 数据库
    String DEV_LOG = HOME_PAGE_ITEM + "15";//http://www.jcodecraeer.com/plus/list.php?tid=15 开发日志


    String PAGE = "&TotalResult={0}&PageNo={1}";///plus/list.php?tid=27&TotalResult=61&PageNo=2 拿分页数据






}
