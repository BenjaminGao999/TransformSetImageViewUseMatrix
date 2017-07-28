package com.gaos.imageviewmatrix;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.gaos.imageviewmatrix.view.TransformSetImageView;


public class MainActivity extends AppCompatActivity {


    private ViewPager viewPager;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_v2);


        viewPager = (ViewPager) findViewById(R.id.vpager);


        MyPagerAdapter myPagerAdapter = new MyPagerAdapter();
        viewPager.setAdapter(myPagerAdapter);


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            private int prePosition;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }


            /**
             *
             * 新页面完全开启。
             * @param position
             */
            @Override
            public void onPageSelected(int position) {


                TransformSetImageView view = (TransformSetImageView) viewPager.getChildAt(prePosition);

                if (view != null) {

                    view.reset();

                    view.setPosition(position);
                }

                prePosition = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    private class MyPagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            TransformSetImageView view = new TransformSetImageView(container.getContext(), null);
            view.setImageResource(R.drawable.me2);

            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);

            if (container.indexOfChild((View) object) != -1) {

                container.removeView((View) object);
            }
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }


    }
}

