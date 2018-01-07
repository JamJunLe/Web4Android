package com.flyman.app.web4android.modle.task;

/**
 * @author Flyman
 * @ClassName NewsTask
 * @description 任务
 * @date 2017-4-14 19:52
 */
public class NewsTask extends BaseTask{
    private int totalCodes;//文章总数
    private int pageNum;//请求的分页

    public NewsTask(int taskId) {
        super(taskId);
    }

    public NewsTask(int taskId, int pageNum, int totalCodes) {
        super(taskId);
        this.pageNum = pageNum;
        this.totalCodes = totalCodes;
    }

    public int getTotalCodes() {
        return totalCodes;
    }

    public void setTotalCodes(int totalCodes) {
        this.totalCodes = totalCodes;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public interface Id {
        int PULL_REFRESH = 1;
        int PULL_REFRESH_SUCESS = 2;
        int PULL_REFRESH_FAIL = 3;
        int PUSH_LOAD_MORE_REFRESH = 4;
        int PUSH_LOAD_MORE_REFRESH_SUCESS = 5;
        int PUSH_LOAD_MORE_REFRESH_FAIL = 6;
        int PUSH_LOAD_MORE_REFRESH_FINISH = 7;//没有更多的数据了
        int HTMML_PARSE_ERROR = 8;//网页解析失败
    }

    public interface Message {
        String MSG_PUSH_LOAD_MORE_REFRESH_FINISH = "没有更多数据了";
        String MSG_PULL_REFRESH_FAIL = "刷新失败";
        String MSG_PUSH_LOAD_MORE_REFRESH_FAIL = "上拉刷新失败";
        String MSG_HTMML_PARSE_ERROR = "网页解析失败";
        String MSG_NET_ERROR = "网络错误";
    }
}
