package com.example.jason.fight.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jason.fight.R;
import com.example.jason.fight.ui.adapter.NormalRecyclerAdapter;

/**
 * Created by Jason on 2016/4/9.
 */
public class DateContentFragment extends Fragment {
    /*
    * 约战的布局
    * */
    private RecyclerView mRecylerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private static Context mContext;
    public static DateContentFragment newInstance(Context context) {

        mContext=context;
        Bundle args = new Bundle();
        DateContentFragment fragment = new DateContentFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.date_content_recyclerview,container,false);
        mRecylerView = (RecyclerView) view.findViewById(R.id.drawer_content_rv);
        mRecylerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(mContext);
        //mLayoutManager=new GridLayoutManager(this,2);//用线性宫格显示，类是gridView
        //mLayoutManager=new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);//同线性宫格显示，类瀑布流
        mRecylerView.setLayoutManager(mLayoutManager);
        //specify a adapter
        mRecylerView.setAdapter(new NormalRecyclerAdapter(mContext));
        return view;
    }
}
