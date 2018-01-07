package com.flyman.app.web4android.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.flyman.app.util.common.InputMethodManagerLeakHelper;
import com.flyman.app.util.log.LogUtils;
import com.flyman.app.web4android.R;
import com.flyman.app.web4android.base.MVPBaseActivity;
import com.flyman.app.web4android.io.api.NetUrl;
import com.flyman.app.web4android.modle.bean.About;
import com.flyman.app.web4android.modle.constant.GlobalConstant;
import com.flyman.app.web4android.presenter.MainPresenter;
import com.flyman.app.web4android.ui.fragment.CodeListFragment;
import com.flyman.app.web4android.ui.fragment.FAQsFragment;
import com.flyman.app.web4android.ui.fragment.HomePageFragment;
import com.flyman.app.web4android.ui.fragment.TopicListFragment;
import com.flyman.app.web4android.ui.view.IMainView;
import com.flyman.app.web4android.widget.FragmentRadioGroupAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.flyman.app.web4android.R.id.menu_main_about;

public class MainActivity extends MVPBaseActivity<IMainView, MainPresenter> implements IMainView, FragmentRadioGroupAdapter.OnRgsExtraCheckedChangedListener, View.OnClickListener {

    private RadioGroup rg_main;
    private MainPresenter presenter;
    private Toolbar mToolbar;
    private FragmentRadioGroupAdapter fragmentRadioGroupAdapter;
    private List<Fragment> list;
    private View rootView;
    private LinearLayout aboutContainer;
    private List<About> mAboutList;
    private MaterialDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        setContentView(rootView);
        findWidget();
        initVariable();
        presenter.doTask(null);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void findWidget() {
        rg_main = (RadioGroup) findViewById(R.id.rg_main);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mToolbar.setTitle(getResources().getString(R.string.main_tab_1));
        mToolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(mToolbar);
    }

    /**
     * 初始化变量
     */
    @Override
    protected void initVariable() {
        HomePageFragment homePage = new HomePageFragment();
        CodeListFragment codeFragment = new CodeListFragment();
        TopicListFragment topicFragment = new TopicListFragment();
        FAQsFragment faQsFragment = new FAQsFragment();
        list = new ArrayList();
        list.add(homePage);
        list.add(codeFragment);
        list.add(topicFragment);
        list.add(faQsFragment);
        fragmentRadioGroupAdapter = new FragmentRadioGroupAdapter(this, list, R.id.tab_container, rg_main);
        fragmentRadioGroupAdapter.setOnRgsExtraCheckedChangedListener(this);
        //"关于"
        mAboutList = new ArrayList<>();
    }


    @Override
    public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
        switch (index) {
            case 0: {
                mToolbar.setTitle(getResources().getString(R.string.main_tab_1));
                break;
            }
            case 1: {
                mToolbar.setTitle(getResources().getString(R.string.main_tab_2));
                break;
            }
            case 2: {
                mToolbar.setTitle(getResources().getString(R.string.main_tab_3));
                break;
            }
            case 3: {
                mToolbar.setTitle(getResources().getString(R.string.main_tab_4));
                break;
            }
            default: {

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LogUtils.e("" + item.getItemId());
        int id = item.getItemId();
        switch (id) {
            case menu_main_about: {
                //显示关于信息
                showAboutInfo();
                break;
            }
            default: {

            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAboutInfo() {
        createAboutInfo();
        mDialog.show();
    }

    /**
     * 显示关于信息
     *
     * @param
     * @return nothing
     */
    private void createAboutInfo() {
        if (mAboutList.size() == 0||aboutContainer == null) {
            //用于设置后续新建button的LayoutParams
            aboutContainer = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.about_container, null, false);
            TextView tv_gank_io = (TextView) aboutContainer.findViewById(R.id.tv_gank_io);
            TextView tv_gank_address = (TextView) aboutContainer.findViewById(R.id.tv_gank_address);
            tv_gank_io.setOnClickListener(this);
            tv_gank_address.setOnClickListener(this);
            tv_gank_io.setText(Html.fromHtml(getResources().getString(R.string.text_about_data_from) + " <font color='#d22222'><a href=" + ">泡在网上的日子</a>"));
            tv_gank_address.setText(Html.fromHtml(getResources().getString(R.string.text_about_data_from_github) + " <font color='#d22222'><a href=" + ">Web4Android</a>"));
            String[] titles = getResources().getStringArray(R.array.open_source_framework_title);
            String[] urls = getResources().getStringArray(R.array.open_source_framework_url);
            for (int i = 0; i < titles.length; i++) {
                mAboutList.add(new About(titles[i], urls[i]));
                TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.item_about_txtview, null, false);
                tv.setText(Html.fromHtml("<font color='#d22222'><a href=>" + titles[i] + "</a>"));
                aboutContainer.addView(tv);
                tv.setTag(i);
                tv.setOnClickListener(this);
            }
        }
        if (mDialog == null) {
            mDialog = new MaterialDialog.Builder(this)
                    .title(getResources().getString(R.string.action_about))
                    .customView(aboutContainer, true)
                    .positiveText(getResources().getString(R.string.action_about_close))
                    .build();

        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String url;
        switch (id) {
            case R.id.tv_gank_io: {
                url = NetUrl.BASE_URL;
                break;
            }
            case R.id.tv_gank_address: {
                url = NetUrl.GITHUB_HOMEPAGE;
                break;
            }
            default: {
                int position = (int) v.getTag();
                url = mAboutList.get(position).getUrl();
            }
        }
        Intent mIntent = new Intent(this, ArticleDetails.class);
        mIntent.putExtra(GlobalConstant.IntentExtraKeyConstant.KEY_LIST_2_DETAILS, url);
        startActivity(mIntent);

    }


    @Override
    protected MainPresenter createPresenter() {
        return presenter= new MainPresenter();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        InputMethodManagerLeakHelper.fixFocusedViewLeak(getApplication());
    }

}
