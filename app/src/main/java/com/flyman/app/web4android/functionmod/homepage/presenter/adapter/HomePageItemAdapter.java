package com.flyman.app.web4android.functionmod.homepage.presenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyman.app.util.common.Dp2Pixels;
import com.flyman.app.util.glide.GlideHelper;
import com.flyman.app.util.log.LogUtils;
import com.flyman.app.web4android.R;
import com.flyman.app.web4android.functionmod.homepage.modle.bean.Article;
import com.flyman.app.web4android.io.api.NetUrl;

import java.util.List;

public class HomePageItemAdapter extends RecyclerView.Adapter {
    private List<Article> articleList;
    private Context mContext;
    private String url = NetUrl.BASE_URL;
    private View.OnClickListener mOnClickListener;
    private LinearLayout.LayoutParams mParams;//用于设置当前item缺失图片时内容往右移的margin
    private float leftMargin;


    public HomePageItemAdapter(List<Article> articleList, Context mContext, View.OnClickListener mOnClickListener) {
        this.articleList = articleList;
        this.mContext = mContext;
        this.mOnClickListener = mOnClickListener;
        leftMargin = Dp2Pixels.convertDpToPixel(mContext.getResources().getDimension(R.dimen.pic_text_margin), mContext);
        LogUtils.e("showPullRefreshData", "16dp转换为像素 = " + leftMargin);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_page_child, parent, false);
        return new HomePageChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HomePageChildViewHolder mHolder = (HomePageChildViewHolder) holder;
        mHolder.tv_article_title.setText(articleList.get(position).getTitle());
//        mHolder.tv_article_simple_intro.setText(articleList.get(position).getSimpleIntro());
        mHolder.tv_article_time.setText(articleList.get(position).getTime());
        mHolder.tv_article_eye_open.setText(articleList.get(position).getEyeOpen() + "浏览");
//        mHolder.tv_article_user.setText(articleList.get(position).getListUser());
//        mHolder.tv_article_comment.setText(articleList.get(position).getComment());
//        mHolder.tv_article_bookmark.setText(articleList.get(position).getBookmark());
        mParams = (LinearLayout.LayoutParams) mHolder.ll_home_page_content.getLayoutParams();
        if (articleList.get(position).getImg().trim() == null || "".equals(articleList.get(position).getImg().trim())) {
            mHolder.iv_article_image.setVisibility(View.GONE);
            mParams.leftMargin = 0;
            mHolder.ll_home_page_content.setLayoutParams(mParams);

        } else {
            mHolder.iv_article_image.setVisibility(View.VISIBLE);
            mParams.leftMargin = (int) leftMargin;
            mHolder.ll_home_page_content.setLayoutParams(mParams);
            GlideHelper.loadBitmap(mContext, mHolder.iv_article_image, (url + articleList.get(position).getImg()).trim());
        }
        mHolder.itemView.setTag(position);
        mHolder.itemView.setOnClickListener(mOnClickListener);
        LogUtils.e("showPullRefreshData", "leftMargin = " + mParams.leftMargin);

//        LogUtils.e("showPullRefreshData", "" + (articleList.get(position).getTitle()));
//        LogUtils.e("showPullRefreshData", "" + (articleList.get(position).getSimpleIntro()));
//        LogUtils.e("showPullRefreshData", "" + (articleList.get(position).getEyeOpen()));
//        LogUtils.e("showPullRefreshData", "" + (articleList.get(position).getTime()));


    }

    @Override
    public int getItemCount() {
        return articleList == null || articleList.size() == 0 ? 0 : articleList.size();
    }


    class HomePageChildViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_article_title;
        public TextView tv_article_simple_intro;
        //        public TextView tv_article_user;
        public TextView tv_article_eye_open;
        //        public TextView tv_article_comment;
//        public TextView tv_article_bookmark;
        public TextView tv_article_time;
        public ImageView iv_article_image;
        public LinearLayout ll_home_page_content;

        public HomePageChildViewHolder(View itemView) {
            super(itemView);
            tv_article_title = (TextView) itemView.findViewById(R.id.tv_article_title);
//            tv_article_simple_intro = (TextView) itemView.findViewById(R.id.tv_article_simple_intro);
            tv_article_eye_open = (TextView) itemView.findViewById(R.id.tv_article_eye_open);
            tv_article_time = (TextView) itemView.findViewById(R.id.tv_article_time);
            iv_article_image = (ImageView) itemView.findViewById(R.id.iv_article_img);
            ll_home_page_content = (LinearLayout) itemView.findViewById(R.id.ll_home_page_content);

        }
    }
}
