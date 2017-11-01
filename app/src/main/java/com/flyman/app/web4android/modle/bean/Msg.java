package com.flyman.app.web4android.modle.bean;

/**
 * @author Flyman
 * @ClassName MsgConstant
 * @description 错误消息描述
 * @date 2017-4-16 15:34
 */
public class Msg {
    private int msgId;
    private String msgInfo;

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public Msg(int msgId) {
        this.msgId = msgId;
    }

    public Msg(int msgId, String msgInfo) {
        this.msgId = msgId;
        this.msgInfo = msgInfo;
    }

    public String getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(String msgInfo) {
        this.msgInfo = msgInfo;
    }

    public Msg(String msgInfo) {

        this.msgInfo = msgInfo;

    }
}
