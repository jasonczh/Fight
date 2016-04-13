package com.example.jason.fight.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jason.fight.R;

/**
 * Created by Jason on 2016/4/9.
 */
public class RankContentFragment extends Fragment{

    /*
    * 放在以后写排行榜布局
    * */
    private Context mContext;
    public static RankContentFragment newInstance(Context mContext) {

        Bundle args = new Bundle();
        RankContentFragment fragment = new RankContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.rank_content,container,false);
        return view;
    }
}
