package com.example.humungus.safaricare;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.humungus.safaricare.activities.LoginActivitry;
import com.example.humungus.safaricare.adapters.SliderAdapter;

public class SlideViewActivity extends AppCompatActivity {

    private ViewPager mviewPager;
    private LinearLayout mDotLayout;

    private TextView [] mDots;

    private SliderAdapter sliderAdapter;

    private Button mNextButon;
    private Button mBackButon;

    private int mCurrentPage = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_view);

        mviewPager = findViewById(R.id.slideViewPager);
        mDotLayout = findViewById(R.id.dots);

        mNextButon = findViewById(R.id.nxtbutton);
        mBackButon = findViewById(R.id.prevbutton);

        sliderAdapter = new SliderAdapter(this);

        mviewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        mviewPager.addOnPageChangeListener(viewListener);

        //onCLickListener
        mNextButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentPage < 2) {
                    mviewPager.setCurrentItem(mCurrentPage + 1);
                }else{
                    Intent intent = new Intent(getApplicationContext(), LoginActivitry.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                }
            }
        });

        mBackButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mviewPager.setCurrentItem(mCurrentPage -1);
            }
        });

    }

    public void addDotsIndicator(int position){

        mDots = new TextView[3];
        mDotLayout.removeAllViews();

        for (int i = 0;i<mDots.length;i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.whiteTransparent));

            mDotLayout.addView(mDots[i]);

        }

        if(mDots.length>0){
            mDots[position].setTextColor(getResources().getColor(R.color.white));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDotsIndicator(position);

            mCurrentPage = position;

            if(position == 0){
                mNextButon.setEnabled(true);
                mBackButon.setEnabled(false);
                mBackButon.setVisibility(View.INVISIBLE);

                mNextButon.setText("next");
                mBackButon.setText("");

            }else if (position == 1){
                mNextButon.setEnabled(true);
                mBackButon.setEnabled(true);
                mBackButon.setVisibility(View.VISIBLE);

                mNextButon.setText("next");
                mBackButon.setText("back");

            }else{
                mNextButon.setEnabled(true);
                mBackButon.setEnabled(true);
                mBackButon.setVisibility(View.VISIBLE);

                mNextButon.setText("finish");
                mBackButon.setText("back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}