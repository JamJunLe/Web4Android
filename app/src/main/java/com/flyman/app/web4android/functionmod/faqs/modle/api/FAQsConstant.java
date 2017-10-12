package com.flyman.app.web4android.functionmod.faqs.modle.api;

import com.flyman.app.web4android.io.api.NetUrl;

public interface FAQsConstant {
    String TYPE_ALL_FAQS = "所有问题";
    String TYPE_TO_BE_SOLVED = "待解决";
    String TYPE_ALREAD_SOLVED = "已解决";

    String[] TITLE = {TYPE_ALL_FAQS, TYPE_TO_BE_SOLVED, TYPE_ALREAD_SOLVED};
    //args
    String ARGS_FAQS = "args";

    String FAQS_ITEM = NetUrl.BASE_URL + "/ask/";
    String URL_ALL_FAQS = FAQS_ITEM;//http://www.jcodecraeer.com/ask/所有问题
    String URL_TO_BE_SOLVED = FAQS_ITEM + "?type=0";//http://www.jcodecraeer.com/ask/?type=0 已解决
    String URL_ALREAD_SOLVED = FAQS_ITEM + "?type=1";//http://www.jcodecraeer.com/ask/?type=1 未解决

    String FAQs_PAGE = "?type=-1&TotalResult={0}&PageNo={1}";//http://www.jcodecraeer.com/ask/?type=-1&TotalResult=99&PageNo=3 拿分页数据
}
