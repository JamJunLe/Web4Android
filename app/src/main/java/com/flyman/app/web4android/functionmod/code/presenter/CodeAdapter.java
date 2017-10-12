package com.flyman.app.web4android.functionmod.code.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyman.app.util.glide.GlideHelper;
import com.flyman.app.web4android.R;
import com.flyman.app.web4android.functionmod.code.modle.bean.CodeArticle;

import java.util.ArrayList;


public class CodeAdapter extends RecyclerView.Adapter {
    private View.OnClickListener mOnClickListener;
    private ArrayList<CodeArticle> codeList;
    private Context mContext;

    public CodeAdapter(ArrayList<CodeArticle> codeList, Context mContext, View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
        this.codeList = codeList;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_code, parent, false);
        return new CodeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CodeViewHolder mCodeViewHolder = (CodeViewHolder) holder;
        mCodeViewHolder.itemView.setTag(position);
        mCodeViewHolder.tv_code_eye_open.setText(codeList.get(position).getEyeOpen());
        mCodeViewHolder.tv_code_time.setText(codeList.get(position).getTime());
        mCodeViewHolder.tv_code_simple_intro.setText(codeList.get(position).getSimpleIntro());
        mCodeViewHolder.tv_code_title.setText(codeList.get(position).getTitle());
        GlideHelper.loadBitmap(mContext, mCodeViewHolder.iv_code_img, codeList.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        return codeList == null || codeList.size() == 0 ? 0 : codeList.size();
    }

    public class CodeViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_code_simple_intro;
        public TextView tv_code_eye_open;
        public TextView tv_code_time;
        public ImageView iv_code_img;
        public TextView tv_code_title;

        public CodeViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(mOnClickListener);
            tv_code_simple_intro = (TextView) itemView.findViewById(R.id.tv_code_simple_intro);
            tv_code_eye_open = (TextView) itemView.findViewById(R.id.tv_code_eye_open);
            tv_code_time = (TextView) itemView.findViewById(R.id.tv_code_time);
            iv_code_img = (ImageView) itemView.findViewById(R.id.iv_code_img);
            tv_code_title = (TextView) itemView.findViewById(R.id.tv_code_title);
        }


    }
}
