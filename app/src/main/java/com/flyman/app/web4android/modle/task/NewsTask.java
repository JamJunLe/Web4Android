package com.flyman.app.web4android.modle.task;

/**
 * @author Flyman
 * @ClassName NewsTask
 * @description 任务
 * @date 2017-4-14 19:52
 */
public class NewsTask extends BaseTask {
    private int newsAmount;//文章总数
    private int pageIndex;//请求的分页
    private String url;

    public NewsTask(int taskId) {
        super(taskId);
    }

    public NewsTask(int taskId, int pageIndex, int newsAmount) {
        super(taskId);
        this.pageIndex = pageIndex;
        this.newsAmount = newsAmount;
    }

    public NewsTask(int taskId, int newsAmount, int pageIndex, String url) {
        super(taskId);
        this.newsAmount = newsAmount;
        this.pageIndex = pageIndex;
        this.url = url;
    }

    public int getNewsAmount() {
        return newsAmount;
    }

    public void setNewsAmount(int newsAmount) {
        this.newsAmount = newsAmount;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public interface Type {
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
