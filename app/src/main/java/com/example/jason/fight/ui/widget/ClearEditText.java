package com.example.jason.fight.ui.widget;

/**
 * Created by Jason on 2016/4/10.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;



import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import com.example.jason.fight.R;

/**
 * Created by Jason on 2016/4/1.
 */public class ClearEditText extends EditText implements View.OnFocusChangeListener,TextWatcher {

    private Drawable mClearDrawable;
    private Context mContext;

    public ClearEditText(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            mClearDrawable = ContextCompat.getDrawable(mContext,R.drawable.clear_edit_text);
        }
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        setClearIconVisible(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }
    /*
    * 模拟点击事件
    * 做法一： 通过getGlobalVisibleRect(rect);
    * 做法二： 精确判断在x上的位置 如果点击的位置在edittext Width -padding -icon'width 和  edittext width- padding 之间就认为点击了
    *经测试 ： 第二种方法会更加精确，但没有在Y轴上限制，所以在单行模式下，这个方法确实好
    * */

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getCompoundDrawables()[2] != null && event.getAction() == MotionEvent.ACTION_UP) {
           /* int eventX= (int) event.getX();
            int eventY= (int) event.getY();
            Rect rect=new Rect();
            getGlobalVisibleRect(rect);
            rect.left=rect.right-mClearDrawable.getIntrinsicWidth();
            if(rect.contains(eventX,eventY))
            {
                setText("");
            }*/

            boolean touchable = event.getX() > (getWidth() - getPaddingRight() - mClearDrawable.getIntrinsicWidth()
            ) && event.getX() < (getWidth() - getPaddingRight());
            if (touchable) {
                setText("");
            }

        }
        return super.onTouchEvent(event);
    }

    /*
    * 当这个clearEditText 焦点发生变化的时候，判断里面字符串长度设置图标显示还是隐藏
    * */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        setClearIconVisible(getText().length() > 0);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /*
    * 设置清除图标的显示与隐藏 调用setCompoundDrawable为EditText绘制上去
    * */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1],
                right, getCompoundDrawables()[3]);
    }

    /*
    * 设置晃动动画
    * */
    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }

    /*
    * 晃动动画
    * 持续时间 1s
    * */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 0, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }
}
