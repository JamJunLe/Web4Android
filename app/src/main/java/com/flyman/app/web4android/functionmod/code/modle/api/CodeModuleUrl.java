package com.flyman.app.web4android.functionmod.code.modle.api;

import com.flyman.app.web4android.io.api.NetUrl;

public interface CodeModuleUrl {
    String URL = NetUrl.BASE_URL + "/plus/list.php?tid=31";//"代码"的网址
    String CODE_PAGE = "&TotalResult={0}&PageNo={1}";///plus/list.php?tid=27&TotalResult=61&PageNo=2 拿分页数据
}
