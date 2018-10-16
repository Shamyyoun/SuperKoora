package net.smartinnovationtechnology.superkoora;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;

import datamodels.Constants;
import utils.ViewUtil;

public class SliderActivity extends AppCompatActivity {
    private static final int SLIDES_COUNT = 5;

    private ViewPager mViewPager;
    private ViewPager mVirtualViewPager; // used to remove indicator from actual view pager
    private CirclePageIndicator mIndicator;
    private TextView mTextSignUp;

    private boolean lastSlide; // flag to indicate if user reached last slide or not

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mVirtualViewPager = new ViewPager(this);
        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mTextSignUp = (TextView) findViewById(R.id.text_sign_up);

        // set actual view pager adapter
        SliderPagerAdapter adapter = new SliderPagerAdapter();
        mViewPager.setAdapter(adapter);
        mIndicator.setViewPager(mViewPager);

        // set virtual adapter for virtual view pager
        mVirtualViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return null;
            }

            @Override
            public int getCount() {
                return 0;
            }
        });

        // add listeners
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                // check position
                if (position == SLIDES_COUNT - 1) {
                    // hide pager indicator and show sign up text
                    ViewUtil.fadeView(mIndicator, false);
                    ViewUtil.fadeView(mTextSignUp, true);
                    lastSlide = true;
                    mIndicator.setViewPager(mVirtualViewPager);
                } else if (lastSlide) { // if only reached last slide and return from it
                    // show pager indicator and hide sign up text
                    ViewUtil.fadeView(mIndicator, true);
                    ViewUtil.fadeView(mTextSignUp, false);
                    lastSlide = false;
                    mIndicator.setViewPager(mViewPager);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mTextSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open sign up activity
                Intent intent = new Intent(SliderActivity.this, SignUpActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_scale_fade_enter, R.anim.no_anim);
                finish();
            }
        });
    }

    /**
     * overridden method
     */
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.no_anim, R.anim.activity_scale_fade_exit);
    }


    /*
     * ---Slider Pager Adapter---
     */
    private class SliderPagerAdapter extends FragmentPagerAdapter {
        public SliderPagerAdapter() {
            super(getSupportFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.KEY_INDEX, position);

            SliderPagerFragment fragment = new SliderPagerFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return SLIDES_COUNT;
        }
    }

    /**
     * ---Slider Pager Fragment---
     */
    public static class SliderPagerFragment extends Fragment {
        private ImageView mImageBackground;
        private TextView mTextTitle;
        private TextView mTextDesc;
        private int mIndex;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.pager_slider, container, false);
            initComponents(rootView);
            return rootView;
        }

        private void initComponents(View rootView) {
            mIndex = getArguments().getInt(Constants.KEY_INDEX);
            mImageBackground = (ImageView) rootView.findViewById(R.id.image_background);
            mTextTitle = (TextView) rootView.findViewById(R.id.text_title);
            mTextDesc = (TextView) rootView.findViewById(R.id.text_desc);

            switch (mIndex) {

                case 1:
                    mImageBackground.setImageResource(R.drawable.slider_image2);
                    mTextTitle.setText(R.string.slider_title2);
                    mTextDesc.setText(R.string.slider_desc2);
                    break;

                case 2:
                    mImageBackground.setImageResource(R.drawable.slider_image3);
                    mTextTitle.setText(R.string.slider_title3);
                    mTextDesc.setText(R.string.slider_desc3);
                    break;

                case 3:
                    mImageBackground.setImageResource(R.drawable.slider_image4);
                    mTextTitle.setText(R.string.slider_title4);
                    mTextDesc.setText(R.string.slider_desc4);
                    break;

                case 4:
                    mImageBackground.setImageResource(R.drawable.slider_image5);
                    mTextTitle.setText(R.string.slider_title5);
                    mTextDesc.setText(R.string.slider_desc5);
                    break;

                default:
                    mImageBackground.setImageResource(R.drawable.slider_image1);
                    mTextTitle.setText(R.string.slider_title1);
                    mTextDesc.setText(R.string.slider_desc1);
            }
        }
    }
}
