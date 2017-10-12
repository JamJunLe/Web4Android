package com.flyman.app.web4android.moudle;

public interface Constant {
    interface MsgConstant {
        int ID_NET_ERROR_ = 1;//网络错误
        int ID_OTHER_ERROR_ = 2;//其他错误
        int ID_PARSE_ERROR = 2;//网页解析错误
        String MSG_NET_ERROR = "网络有问题";
        String MSG_PARSE_ERROR = "网页解析失败";
        String MSG_OTHER_ERROR = "其他未知错误";

    }

    interface HomePageChildConstant {
        String TYPE_COMPREHENSIVE_INFO = "综合资讯";
        String TYPE_CODE_DESIGN = "程序设计";
        String TYPE_ANDROID_DEV = "Android开发";
        String TYPE_WEB_DEV = "前端开发";
        String TYPE_IOS_DEV = "iOS开发";
        String TYPE_DATABASE_DEV = "数据库";
        String TYPE_DEV_LOG = "开发日志";
        String[] TITLE = {TYPE_COMPREHENSIVE_INFO, TYPE_CODE_DESIGN, TYPE_ANDROID_DEV, TYPE_WEB_DEV, TYPE_IOS_DEV, TYPE_DATABASE_DEV, TYPE_DEV_LOG};
        //args
        String ARGS_HOMEPAGE_CHILD_FRAGMENT = "ARGS_HOMEPAGE_CHILD_FRAGMENT";
    }

    interface IntentExtraKeyConstant {
        String KEY_LIST_2_DETAILS = "KEY_LIST_2_DETAILS";

    }


}
