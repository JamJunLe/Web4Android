package com.flyman.app.web4android.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.flyman.app.web4android.io.api.FAQsConstant;
import com.flyman.app.web4android.ui.fragment.FAQsItemListFragment;

import java.util.ArrayList;
import java.util.List;

public class FAQsAdapter extends FragmentPagerAdapter {

    private List<FAQsItemListFragment> mFAQsItemFragments;

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
            mFAQsItemFragments.add(FAQsItemListFragment.getInstance(i));
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return FAQsConstant.TITLE[position];
    }
}
