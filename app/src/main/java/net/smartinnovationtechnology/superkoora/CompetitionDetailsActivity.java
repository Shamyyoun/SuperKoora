package net.smartinnovationtechnology.superkoora;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import datamodels.Competition;
import datamodels.Constants;
import views.SlidingTabLayout;

public class CompetitionDetailsActivity extends AppCompatActivity {
    private Competition mCompetition;
    private ImageButton mButtonIcon;
    private ImageView mImageHeaderBackground;
    private ImageView mImageHeaderLogo;
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private String[] mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition_details);

        initComponents();
    }

    /**
     * method, used to init components
     */
    private void initComponents() {
        mCompetition = (Competition) getIntent().getSerializableExtra(Constants.KEY_COMPETITION);
        mButtonIcon = (ImageButton) findViewById(R.id.button_back);
        mImageHeaderBackground = (ImageView) findViewById(R.id.image_headerBackground);
        mImageHeaderLogo = (ImageView) findViewById(R.id.image_headerLogo);
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.slidingTabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mTabs = getResources().getStringArray(R.array.competition_details_tabs);

        // customize header
        mImageHeaderBackground.setImageResource(mCompetition.getBackgroundResId());
        mImageHeaderLogo.setImageResource(mCompetition.getLogo());
        mButtonIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // customize view pager
        CompetitionDetailsAdapter adapter = new CompetitionDetailsAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

        // customize tab sliding layout
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(android.R.color.white);
            }
        });
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    /**
     * --Competition Details Adapter--
     */
    public class CompetitionDetailsAdapter extends FragmentStatePagerAdapter {
        public CompetitionDetailsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            switch (position) {
                case 0:
                    fragment = new StandingsFragment();
                    break;

                case 1:
                    fragment = new ScheduleFragment();
                    break;

                case 2:
                    fragment = new TopScorersFragment();
                    break;

                default:
                    fragment = new ChampionsFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabs[position];
        }

        @Override
        public int getCount() {
            return mTabs.length;
        }
    }
}
