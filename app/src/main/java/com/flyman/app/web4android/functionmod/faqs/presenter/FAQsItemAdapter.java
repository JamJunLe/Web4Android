package com.flyman.app.web4android.functionmod.faqs.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyman.app.util.glide.GlideHelper;
import com.flyman.app.web4android.R;
import com.flyman.app.web4android.functionmod.faqs.modle.bean.FAQs;
import com.flyman.app.web4android.io.api.NetUrl;

import java.util.List;

public class FAQsItemAdapter extends RecyclerView.Adapter {
    private List<FAQs> mFAQsList;
    private Context mContext;
    private String url = NetUrl.BASE_URL;
    private View.OnClickListener mOnClickListener;

    public FAQsItemAdapter(List<FAQs> articleList, Context mContext) {
        this.mFAQsList = articleList;
        this.mContext = mContext;
    }

    public FAQsItemAdapter(List<FAQs> articleList, Context mContext, View.OnClickListener mOnClickListener) {
        this.mFAQsList = articleList;
        this.mContext = mContext;
        this.mOnClickListener = mOnClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_ask, parent, false);
        return new HomePageChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HomePageChildViewHolder mHolder = (HomePageChildViewHolder) holder;
        mHolder.tv_ask_title.setText(mFAQsList.get(position).getTitle());
        mHolder.tv_ask_time.setText(mFAQsList.get(position).getTime());
        mHolder.tv_ask_eye_open.setText(mFAQsList.get(position).getEyeOpen());
        mHolder.tv_ask_comment.setText(mFAQsList.get(position).getComment());
        mHolder.tv_ask_member_name.setText(mFAQsList.get(position).getMemberName() + "发起提问");
        GlideHelper.loadCircleBitmap(mContext, mHolder.iv_ask_member_img, (mFAQsList.get(position).getMemberImg()).trim());
        mHolder.itemView.setTag(position);
        mHolder.itemView.setOnClickListener(mOnClickListener);
//        LogUtils.e("showPullRefreshData", "" + (mFAQsList.get(position).getTitle()));
//        LogUtils.e("showPullRefreshData", "" + (mFAQsList.get(position).getSimpleIntro()));
//        LogUtils.e("showPullRefreshData", "" + (mFAQsList.get(position).getEyeOpen()));
//        LogUtils.e("showPullRefreshData", "" + (mFAQsList.get(position).getTime()));


    }

    @Override
    public int getItemCount() {
        return mFAQsList == null || mFAQsList.size() == 0 ? 0 : mFAQsList.size();
    }


    class HomePageChildViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_ask_title;
        public TextView tv_ask_member_name;
        public ImageView iv_ask_member_img;
        public TextView tv_ask_eye_open;
        public TextView tv_ask_comment;
        public TextView tv_ask_time;


        public HomePageChildViewHolder(View itemView) {
            super(itemView);
            tv_ask_title = (TextView) itemView.findViewById(R.id.tv_ask_title);
            tv_ask_member_name = (TextView) itemView.findViewById(R.id.tv_ask_member_name);
            tv_ask_eye_open = (TextView) itemView.findViewById(R.id.tv_ask_eye_open);
            tv_ask_comment = (TextView) itemView.findViewById(R.id.tv_ask_comment);
            tv_ask_time = (TextView) itemView.findViewById(R.id.tv_ask_time);
            iv_ask_member_img = (ImageView) itemView.findViewById(R.id.iv_ask_member_img);

        }
    }
}
