package com.flyman.app.web4android.functionmod.faqs.presenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.flyman.app.web4android.functionmod.faqs.modle.api.FAQsConstant;
import com.flyman.app.web4android.functionmod.faqs.view.FAQsItemFragment;

import java.util.ArrayList;
import java.util.List;

public class FAQsAdapter extends FragmentPagerAdapter {

    private List<FAQsItemFragment> mFAQsItemFragments;

    public FAQsAdapter(FragmentManager fm) {
        super(fm);
        initFragmets();
    }

    @Override
    public Fragment getItem(int position) {
        return mFAQsItemFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFAQsItemFragments.size();
    }

    private void initFragmets() {
        mFAQsItemFragments = new ArrayList<>();
        for (int i = 0; i < FAQsConstant.TITLE.length; i++) {
            mFAQsItemFragments.add(FAQsItemFragment.getInstance(i));
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return FAQsConstant.TITLE[position];
    }
}
