package com.example.jason.fight.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.support.design.widget.*;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.*;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.fight.R;
import com.example.jason.fight.ui.adapter.BannerAdapter;
import com.example.jason.fight.ui.adapter.ContentPageAdapter;
import com.example.jason.fight.ui.adapter.NormalRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private CoordinatorLayout container;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavagationView;
    private TabLayout mTabLayout;
    private ViewPager vpContent;
    private ContentPageAdapter pageAdapter;
    private ViewPager vpPic;
    private FloatingActionButton fab;

    private View header;
    private CircleImageView cAvatarImg;
    private TextView navLoginTv;
    /*
    * ViewPager中的ImageView的容器
    * */
    private List<ImageView> imgContainer = null;

    /*
    * 上一个被选中的小圆点的索引，默认值0
    * */
    private int preDotPosition = 0;

    /*
    * Banner文字描述数组
    * */
    private String[] bannerTextDescArray = {
            "巩俐不低俗，我就不能低俗",
            "朴树又回来了，再唱经典老歌引万人大合唱",
            "揭秘北京电影如何升级",
            "乐视网TV版大派送", "热血屌丝的反杀"
    };
    /*
    * Banner滚动的线程是否销毁的标志，默认不销毁
    * */
    private boolean isStop = false;

    /*
    * Banner切换下一个page的间隔时间
    * */
    private long scrollTimeOffset = 5000;

    /*
    *Banner的文字描述显示控件
    * */
    private TextView tvBannerTextDesc;

    /*
    * 小圆点的父控件
    * */
    private LinearLayout llDotGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        iniNavHeaderView();
        initViewListener();
        initBannerView();
        initDrawerNav();
        initFloatingActionButton();
        initTabs();
        startBannerScrollThread();

    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.drawer_toolbar);
        vpPic = (ViewPager) findViewById(R.id.drawer_viewPager);
        vpContent= (ViewPager) findViewById(R.id.drawer_content_vp);
        llDotGroup = (LinearLayout) findViewById(R.id.drawer_viewPage_dot_group);
        fab= (FloatingActionButton) findViewById(R.id.drawer_faBtn);

        tvBannerTextDesc = (TextView) findViewById(R.id.drawer_viewPager_tv);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.drawer_ctlayout);
        collapsingToolbarLayout.setTitle(" ");
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //container= (CoordinatorLayout) findViewById(R.id.drawer_viewPage_dot_group);
    }

    private void iniNavHeaderView(){
        header=View.inflate(MainActivity.this,R.layout.header_nav,null);
        cAvatarImg= (CircleImageView) header.findViewById(R.id.nav_header_avatar);
        navLoginTv= (TextView) header.findViewById(R.id.nav_header_tv);
        //跳转至登入页面
        navLoginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                //finish();
            }
        });
    }

    private void initBannerView() {
        imgContainer = new ArrayList<ImageView>();
        int[] imageIDs = new int[]{
                R.mipmap.a,
                R.mipmap.b,
                R.mipmap.c,
                R.mipmap.d,
                R.mipmap.e,
        };
        ImageView imageView;
        View dot;
        LinearLayout.LayoutParams params;
        for (int id : imageIDs) {
            imageView = new ImageView(this);
            imageView.setBackgroundResource(id);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imgContainer.add(imageView);

            //每循环一次就添加一个点到线行布局中
            dot = new View(this);
            dot.setBackgroundResource(R.drawable.dot_bd_selector);
            params = new LinearLayout.LayoutParams(10, 10);
            params.leftMargin = 15;
            dot.setEnabled(false);
            dot.setLayoutParams(params);
            //向线性布局添加点
            llDotGroup.addView(dot);
        }
        vpPic.setAdapter(new BannerAdapter(MainActivity.this, imgContainer));

        //选中第一个图片、文件描述
        tvBannerTextDesc.setText(bannerTextDescArray[0]);
        llDotGroup.getChildAt(0).setEnabled(true);
        vpPic.setCurrentItem(0);
    }



    private void initViewListener() {
        vpPic.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //取余后的索引，得到新的page索引
                int newPosition = position % imgContainer.size();
                //根据索引设置图片的描述
                tvBannerTextDesc.setText(bannerTextDescArray[newPosition]);
                //把上一个点设置为被选中
                llDotGroup.getChildAt(preDotPosition).setEnabled(false);
                //根据索引设置那个点被选中
                llDotGroup.getChildAt(newPosition).setEnabled(true);
                //新索引赋值给上一个索引的位置
                preDotPosition = newPosition;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //隐藏fab
                if(position==1){
                    fab.setVisibility(View.INVISIBLE);
                }else{
                    fab.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFabDialog();
            }
        });

    }

    private void initDrawerNav() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        //设置navigation点击事件
        mNavagationView = (NavigationView) findViewById(R.id.navigation);

        mNavagationView.addHeaderView(header);//添加navHeader
        setupDrawerContent(mNavagationView);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.nav_header_tv:
                        ShowToast("hehe");
                        break;
                }
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_item_example:
                        //switchToExample();
                        break;
                    case R.id.navigation_item_blog:
                        //switchToBlog();
                        break;
                    case R.id.navigation_item_about:
                        // switchToAbout();
                        break;
                    case R.id.nav_header_tv:
                        ShowToast("hehe");
                        break;
                }
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void initFloatingActionButton(){

    }
    private void initTabs() {
        mTabLayout= (TabLayout) findViewById(R.id.drawer_content_tab);
        mTabLayout.addTab(mTabLayout.newTab().setText("约战"));
        mTabLayout.addTab(mTabLayout.newTab().setText("排行榜"));
        pageAdapter=new ContentPageAdapter(getSupportFragmentManager(),this);
        vpContent.setAdapter(pageAdapter);
        mTabLayout.setupWithViewPager(vpContent);
        //mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    }

    //开启滚动线程
    private void startBannerScrollThread(){
        Thread mThread=new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStop) {
                    SystemClock.sleep(scrollTimeOffset);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int nextIndex = vpPic.getCurrentItem() + 1;
                            vpPic.setCurrentItem(nextIndex);//每次调用这方法后，会回调PageChangeListenner
                        }
                    });
                }
            }
        });
        mThread.start();
    }

    private void showFabDialog(){
        new AlertDialog.Builder(MainActivity.this).setTitle("发帖")
                .setMessage("确定要发帖么？")
                .setIcon(R.drawable.ic_action_add)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,"正在打开编辑页面",Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }

    @Override
    protected void onDestroy() {
        isStop=true;
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

    }
}
