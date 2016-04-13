package com.example.jason.fight.ui.adapter;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.fight.R;

/**
 * Created by Jason on 2016/4/9.
 */
public class NormalRecyclerAdapter extends RecyclerView.Adapter<NormalRecyclerAdapter.NormalTextViewHolder>{


    private final LayoutInflater mLayoutInflater;
    private CoordinatorLayout container;
    private View view;
    private final Context mContext;
    private  String []mTitles;

    public NormalRecyclerAdapter(Context context){
        mTitles=context.getResources().getStringArray(R.array.title);
        mContext=context;
        mLayoutInflater=LayoutInflater.from(context);
        view=mLayoutInflater.inflate(R.layout.activity_main, null);
        //container= (CoordinatorLayout) view.findViewById(R.id.container);
    }

    @Override
    public NormalTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalTextViewHolder(mLayoutInflater.inflate(R.layout.date_content_main_item,parent,false));
    }


    @Override
    public void onBindViewHolder(NormalTextViewHolder holder, int position) {
        holder.tv.setText(mTitles[position]);

    }



    @Override
    public int getItemCount() {
        return mTitles==null? 0 :mTitles.length;
    }

    public  class NormalTextViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public NormalTextViewHolder(final View itemView) {
            super(itemView);
            tv= (TextView) itemView.findViewById(R.id.tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "onClick--->position: " + getPosition(), Toast.LENGTH_SHORT).show();
                    //Intent intent=new Intent(mContext,SecondActivity.class);
                    //mContext.startActivity(intent);
                }
            });
        }
    }
}

