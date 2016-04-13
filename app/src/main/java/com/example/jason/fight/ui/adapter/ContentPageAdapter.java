package com.example.jason.fight.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;

import com.example.jason.fight.ui.fragment.DateContentFragment;
import com.example.jason.fight.ui.fragment.RankContentFragment;

import java.util.Date;

/**
 * Created by Jason on 2016/4/9.
 */
public class ContentPageAdapter extends FragmentPagerAdapter{

    final int PAGE_COUNT=2;
    private String tabTitle[]=new String []{"约战","排行榜"};
    private Context mContext;
    public ContentPageAdapter(FragmentManager fm,Context context){
        super(fm);
        this.mContext=context;
    }
    @Override
    public Fragment getItem(int position) {
        if(position==0)
        {
            return DateContentFragment.newInstance(mContext);
        }else {

            return RankContentFragment.newInstance(mContext);
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}
