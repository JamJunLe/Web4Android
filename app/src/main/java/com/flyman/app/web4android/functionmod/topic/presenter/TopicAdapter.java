package com.flyman.app.web4android.functionmod.topic.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyman.app.util.glide.GlideHelper;
import com.flyman.app.web4android.R;
import com.flyman.app.web4android.functionmod.topic.modle.bean.Topic;

import java.util.ArrayList;


public class TopicAdapter extends RecyclerView.Adapter {
    private View.OnClickListener mOnClickListener;
    private ArrayList<Topic> mTopicList;
    private Context mContext;

    public TopicAdapter(ArrayList<Topic> codeList, Context mContext, View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
        this.mTopicList = codeList;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_topic, parent, false);
        return new CodeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CodeViewHolder mCodeViewHolder = (CodeViewHolder) holder;
        mCodeViewHolder.itemView.setTag(position);
        mCodeViewHolder.tv_topic_eye_open.setText(mTopicList.get(position).getEyeOpen());
        mCodeViewHolder.tv_topic_time.setText(mTopicList.get(position).getTime());
        mCodeViewHolder.tv_topic_member_name.setText(mTopicList.get(position).getMemberName() + " 分享了");
        String totalTags = mTopicList.get(position).getTotalTags();
        if (totalTags.equals("")) {
            mCodeViewHolder.tv_topic_tags.setText("无相关标签");
        } else {
            mCodeViewHolder.tv_topic_tags.setText(totalTags);
        }
        mCodeViewHolder.tv_topic_title.setText(mTopicList.get(position).getTitle());
        GlideHelper.loadCircleBitmap(mContext, mCodeViewHolder.iv_topic_member_img, mTopicList.get(position).getMemberImg());
    }

    @Override
    public int getItemCount() {
        return mTopicList == null || mTopicList.size() == 0 ? 0 : mTopicList.size();
    }

    public class CodeViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_topic_title;
        public TextView tv_topic_tags;
        public TextView tv_topic_time;
        public TextView tv_topic_eye_open;
        public ImageView iv_topic_member_img;
        public TextView tv_topic_member_name;

        public CodeViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(mOnClickListener);
            tv_topic_title = (TextView) itemView.findViewById(R.id.tv_topic_title);
            tv_topic_tags = (TextView) itemView.findViewById(R.id.tv_topic_tags);
            tv_topic_time = (TextView) itemView.findViewById(R.id.tv_topic_time);
            tv_topic_member_name = (TextView) itemView.findViewById(R.id.tv_topic_member_name);
            tv_topic_eye_open = (TextView) itemView.findViewById(R.id.tv_topic_eye_open);

            iv_topic_member_img = (ImageView) itemView.findViewById(R.id.iv_topic_member_img);
        }


    }
}
