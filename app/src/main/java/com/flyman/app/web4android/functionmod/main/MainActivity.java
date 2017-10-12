package com.flyman.app.web4android.functionmod.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import com.flyman.app.web4android.R;
import com.flyman.app.web4android.base.BaseActivity;
import com.flyman.app.web4android.functionmod.code.view.widget.CodeFragment;
import com.flyman.app.web4android.functionmod.faqs.view.FAQsFragment;
import com.flyman.app.web4android.functionmod.homepage.view.HomePageFragment;
import com.flyman.app.web4android.functionmod.main.contract.MainContract;
import com.flyman.app.web4android.functionmod.topic.view.TopicFragment;
import com.flyman.app.web4android.moudle.bean.BaseTask;
import com.flyman.app.web4android.widget.FragmentRadioGroupAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements MainContract.View, FragmentRadioGroupAdapter.OnRgsExtraCheckedChangedListener {

    private RadioGroup rg_main;
    private MainPresenter presenter;
    private Toolbar mToolbar;
    private FragmentRadioGroupAdapter fragmentRadioGroupAdapter;
    private List<Fragment> list;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        setContentView(rootView);
        findWidget();
        initVariable();
        presenter.start(new BaseTask());
    }

    /**
     * 初始化控件
     */
    @Override
    protected void findWidget() {
        rg_main = (RadioGroup) findViewById(R.id.rg_main);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mToolbar.setTitle("主页");
        mToolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(mToolbar);
    }

    /**
     * 初始化变量
     */
    @Override
    protected void initVariable() {
        HomePageFragment homePage = new HomePageFragment();
        CodeFragment codeFragment = new CodeFragment();
        TopicFragment topicFragment = new TopicFragment();
        FAQsFragment faQsFragment = new FAQsFragment();
        list = new ArrayList();
        list.add(homePage);
        list.add(codeFragment);
        list.add(topicFragment);
        list.add(faQsFragment);
        fragmentRadioGroupAdapter = new FragmentRadioGroupAdapter(this, list, R.id.tab_container, rg_main);
        fragmentRadioGroupAdapter.setOnRgsExtraCheckedChangedListener(this);
        presenter = new MainPresenter(this);
    }

    @Override
    public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
        switch (index) {
            case 0: {
                mToolbar.setTitle("主页");
                break;
            }
            case 1: {
                mToolbar.setTitle("代码");
                break;
            }
            case 2: {
                mToolbar.setTitle("话题");
                break;
            }
            case 3: {
                mToolbar.setTitle("问答");
                break;
            }
            default: {

            }
        }

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        LogUtils.e(""+item.getItemId());
//        mPopupWindow.showAtLocation(rootView, Gravity.CENTER,0,0);
//        return super.onOptionsItemSelected(item);
//    }
}
