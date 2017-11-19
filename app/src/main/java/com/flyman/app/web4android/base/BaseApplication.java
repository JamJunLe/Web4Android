package com.flyman.app.web4android.base;

import android.app.Application;
import android.content.Context;

import com.flyman.app.util.log.LogUtils;
import com.squareup.leakcanary.LeakCanary;


/**
 * Created by Administrator on 2017/3/29.
 */

public class BaseApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        /**
         * lBuilder = new LogUtils.Builder()
         .setLogSwitch(BuildConfig.DEBUG)// 设置log总开关，默认开
         .setGlobalTag("CMJ")// 设置log全局标签，默认为空
         // 当全局标签不为空时，我们输出的log全部为该tag，
         // 为空时，如果传入的tag为空那就显示类名，否则显示tag
         .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
         .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
         .setLogFilter(LogUtils.V);// log过滤器，和logcat过滤器同理，默认Verbose
         */
        LogUtils.Builder builder = new LogUtils.Builder(this);
        mContext = getApplicationContext();

        //使用leakcanary检测内存泄漏
        LeakCanary.install(this);
    }

    /**
     *获取Context 供全局使用
     *
     *  @return Context
     *  @param
     */
    public static Context getAppContext() {
        return mContext;
    }
}
