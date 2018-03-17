package com.smartbeijing.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.smartbeijing.R;
import com.smartbeijing.utils.PrefUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页
 */
public class GuideActivity extends AppCompatActivity {

    private Button btn_start;
    int[] mImageIds = new int[]{R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};
    List<ImageView> imageViewList;
    LinearLayout llContainer;
    private ImageView ivRedPoint;   //小红点
    ViewPager mViewPager;
    int mPointDis; //两个小圆点之间的距离

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        mViewPager = findViewById(R.id.vp_guide);
        btn_start = findViewById(R.id.btn_start);
        llContainer = findViewById(R.id.ll_container);
        ivRedPoint = findViewById(R.id.iv_red_point);
        initData();
        mViewPager.setAdapter(new GuideAdapter());

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //当某个页面滑动时的回调
//                Log.e("GuideActivity", "当前位置:" + position + ",滑动的百分比:" + positionOffset + ",滑动的像素:" + positionOffsetPixels);
                //更新小红点的位置
                int leftMargin= (int) (mPointDis*positionOffset)+position*mPointDis; //计算当前小红点的距离
                RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) ivRedPoint.getLayoutParams();
                params.leftMargin= leftMargin;//修改左边距
                //重新设置布局参数
                ivRedPoint.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                //被选中的页面
//                Log.e("onPageSelected", position + "");
                if (position== imageViewList.size()-1){
                    btn_start.setVisibility(View.VISIBLE);
                }else{
                    btn_start.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //页面状态发生变化的回调
//                Log.e("onPageScrollStateChange", state + "");
                //0:静止  1:滑动中 2:松掉鼠标时
            }
        });

        //计算两个圆点的距离
        //移动距离=第二个圆点left值-第一个圆点left值
        //measure——>layout（确定位置）——>draw（Activity的onCreate方法结束之后才会走此流程）
//        mPointDis= llContainer.getChildAt(1).getLeft()-llContainer.getChildAt(0).getLeft();
//        Log.d("移动距离",mPointDis+"");

        //监听layout方法的结束事件，位置确定好后再确定圆点的位置
        ivRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ivRedPoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);    //取消监听，避免重复计算
                mPointDis= llContainer.getChildAt(1).getLeft()-llContainer.getChildAt(0).getLeft();
                Log.d("移动距离",mPointDis+"");
            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtil.setBoolean(GuideActivity.this,"is_first_enter",false);
                startActivity(new Intent(GuideActivity.this,MainActivity.class));
                finish();
            }
        });
    }

    //初始化数据
    private void initData() {
        imageViewList = new ArrayList<>();
        ImageView view;
        for (int i = 0; i < mImageIds.length; i++) {
            view = new ImageView(this);
            view.setBackgroundResource(mImageIds[i]); //通过设置背景填充布局
            imageViewList.add(view);

            //初始化小圆点
            ImageView iv = new ImageView(this);
            iv.setImageResource(R.drawable.shape_point_gray);

            //初始化布局参数，父控件是谁，就声明谁的布局参数
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i > 0) {
                //从第二个点开始设置左边距
                params.leftMargin = 10;
            }
            iv.setLayoutParams(params);
            llContainer.addView(iv);
        }
    }

    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //初始化item
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = imageViewList.get(position);
            container.addView(view);
            return view;
        }

        //销毁item
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
