package com.example.jason.fight.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Jason on 2016/4/9.
 */
public class BannerAdapter extends PagerAdapter  {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private List<ImageView> imgContainer;
    public BannerAdapter(Context context,List<ImageView> imgContainer){
        mContext=context;
        this.imgContainer=imgContainer;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imgContainer.get(position% imgContainer.size()));
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view=imgContainer.get(position%imgContainer.size());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Page " + position % imgContainer.size() + "被点击了", Toast.LENGTH_SHORT).show();
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;//使得可以从最后一页到第一页
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
