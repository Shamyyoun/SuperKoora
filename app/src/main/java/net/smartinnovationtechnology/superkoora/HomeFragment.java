package net.smartinnovationtechnology.superkoora;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import views.PagerContainer;

/**
 * Created by Dahman on 9/15/2015.
 */
public class HomeFragment extends Fragment {
    private MainActivity mActivity;
    private PagerContainer mPagerContainer;

    private View mViewNewsItem1;
    private View mViewNewsItem2;
    private View mViewNewsItem3;
    private View mViewVideo1;
    private View mViewVideo2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initComponents(rootView);

        return rootView;
    }

    /**
     * method, used to initialize components
     */
    private void initComponents(View rootView) {
//        mActivity = (MainActivity) getActivity();
//        mPagerContainer = (PagerContainer) rootView.findViewById(R.id.pager_container);
//        mViewNewsItem1 = rootView.findViewById(R.id.view_newsItem1);
//        mViewNewsItem2 = rootView.findViewById(R.id.view_newsItem2);
//        mViewNewsItem3 = rootView.findViewById(R.id.view_newsItem3);
//        mViewVideo1 = rootView.findViewById(R.id.view_video1);
//        mViewVideo2 = rootView.findViewById(R.id.view_video2);
//
//        // set activity title
//        mActivity.setTitle(getString(R.string.super_koora));
//
//        // customize matches pager adapter
//        Match[] matches = new Match[6];
//        Team1 barcaTeam = new Team1("FC Barcelona", R.drawable.debug_barca);
//        barcaTeam.setColor("#a81d45");
//        Team1 madridTeam = new Team1("Real Madrid", R.drawable.debug_real_madrid);
//        madridTeam.setColor("#ffffff");
//        Team1 chelseaTeam = new Team1("Chelsea", R.drawable.debug_chealsa);
//        chelseaTeam.setColor("#183bbf");
//        Match match1 = new Match(barcaTeam, madridTeam, R.drawable.debug_liga, "22-10-2015 22:00");
//        matches[0] = match1;
//        Match match2 = new Match(chelseaTeam, madridTeam, R.drawable.debug_uefa_logo, "25-10-2015 21:45");
//        matches[1] = match2;
//        Match match3 = new Match(barcaTeam, madridTeam, R.drawable.debug_liga, "22-10-2015 22:00");
//        matches[2] = match3;
//        Match match4 = new Match(chelseaTeam, madridTeam, R.drawable.debug_uefa_logo, "25-10-2015 21:45");
//        matches[3] = match4;
//        Match match5 = new Match(barcaTeam, madridTeam, R.drawable.debug_liga, "22-10-2015 22:00");
//        matches[4] = match5;
//        Match match6 = new Match(chelseaTeam, madridTeam, R.drawable.debug_uefa_logo, "25-10-2015 21:45");
//        matches[5] = match6;
//
//        ViewPager viewPager = mPagerContainer.getViewPager();
//        MatchesPagerAdapter adapter = new MatchesPagerAdapter(matches);
//        viewPager.setAdapter(adapter);
//        viewPager.setOffscreenPageLimit(adapter.getCount());
//        viewPager.setPageMargin((int) getResources().getDimension(R.dimen.matches_pager_spacing));
//        viewPager.setClipChildren(false);
//
//        // set data
//        NewsItem newsItem1 = new NewsItem("Barcelona Signs Arda Turan From Atletico", R.drawable.debug_turan);
//        newsItem1.setFavorite(true);
//        newsItem1.setDetails(getString(R.string.lorem_ipsum_long));
//        newsItem1.setTag("Transfer News");
//        newsItem1.setDate("22/9/2015 16:10");
//        newsItem1.setSource("Gol.com");
//        newsItem1.setUrl("https://www.google.com.eg/");
//        NewsItem newsItem2 = new NewsItem("Juventus 4 - Milan 2", R.drawable.debug_juvi);
//        newsItem2.setDetails(getString(R.string.lorem_ipsum_long));
//        newsItem2.setTag("Transfer News");
//        newsItem2.setDate("22/9/2015 16:10");
//        newsItem2.setSource("Gol.com");
//        newsItem2.setUrl("https://www.google.com.eg/");
//        NewsItem newsItem3 = new NewsItem("Evona for Al Ahly", R.drawable.debug_real);
//        newsItem3.setDetails(getString(R.string.lorem_ipsum_long));
//        newsItem3.setTag("Transfer News");
//        newsItem3.setDate("22/9/2015 16:10");
//        newsItem3.setSource("Gol.com");
//        newsItem3.setUrl("https://www.google.com.eg/");
//        String videoUrl = "http://video.realmadrid.com/StaticFiles/Media/785/490/HOME_JERSEY_KEY VISUALS_ONLINE_1.mp4";
//        Video video1 = new Video("Man Utd back to UCL group stages", R.drawable.debug_mu);
//        video1.setFavorite(true);
//        video1.setUrl(videoUrl);
//        Video video2 = new Video("Al-ahly won Zamalek in Egypt cup", R.drawable.debug_ahly);
//        video2.setUrl(videoUrl);
//        setContentItemData(mViewNewsItem1, newsItem1);
//        setContentItemData(mViewNewsItem2, newsItem2);
//        setContentItemData(mViewNewsItem3, newsItem3);
//        setContentItemData(mViewVideo1, video1);
//        setContentItemData(mViewVideo2, video2);
    }

//    /**
//     * method, used to find views in content item and set its data & listener
//     */
//    private void setContentItemData(View contentView, final ContentItem contentItem) {
//        // find views
//        ImageView imageImage = (ImageView) contentView.findViewById(R.id.image_image);
//        TextView textTitle = (TextView) contentView.findViewById(R.id.text_title);
//        ImageView imageFavorite = (ImageView) contentView.findViewById(R.id.image_favoriteIcon);
//
//        // set data
//        imageImage.setImageResource(contentItem.getImage());
//        textTitle.setText(contentItem.getTitle());
//        imageFavorite.setVisibility(contentItem.isFavorite() ? View.VISIBLE : View.GONE);
//
//        // add click listener
//        contentView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (contentItem instanceof NewsItem) {
//                    // open news item details activity
//                    Intent intent = new Intent(mActivity, NewsDetailsActivity.class);
//                    intent.putExtra(Constants.KEY_NEWS_ITEM, contentItem);
//                    startActivity(intent);
//                } else {
//                    // check internet connection
//                    if (InternetUtil.isConnected(mActivity)) {
//                        // open video activity
//                        Intent intent = new Intent(mActivity, VideoActivity.class);
//                        intent.putExtra(Constants.KEY_VIDEO, contentItem);
//                        startActivityForResult(intent, Constants.REQ_PLAY_VIDEO);
//                    } else {
//                        // show toast msg
//                        Toast.makeText(mActivity, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });
//    }
//
//    /**
//     * overridden method
//     */
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // check req code
//        if (requestCode == Constants.REQ_PLAY_VIDEO) {
//            // check result
//            if (resultCode == Activity.RESULT_CANCELED) {
//                // show error toast
//                Toast.makeText(mActivity, R.string.connection_error, Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//
//    /*
//     * ---Matches Pager Adapter---
//     */
//    private class MatchesPagerAdapter extends FragmentStatePagerAdapter {
//        private Match[] matches;
//
//        public MatchesPagerAdapter(Match[] matches) {
//            super(mActivity.getSupportFragmentManager());
//            this.matches = matches;
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            Match match = matches[position];
//            Bundle bundle = new Bundle();
//            bundle.putSerializable(Constants.KEY_MATCH, match);
//
//            MatchesPagerFragment fragment = new MatchesPagerFragment();
//            fragment.setArguments(bundle);
//            return fragment;
//        }
//
//        @Override
//        public int getCount() {
//            return matches.length;
//        }
//    }
//
//    /**
//     * ---Matches Pager Fragment---
//     */
//    public static class MatchesPagerFragment extends Fragment {
//        private Match mMatch;
//        private View mViewTopSeperator;
//        private View mViewBottomSeperator;
//        private ImageView mImageTeamLogo1;
//        private TextView mTextTeamTitle1;
//        private ImageView mImageTeamLogo2;
//        private TextView mTextTeamTitle2;
//        private ImageView mImageLeagueLogo;
//        private TextView mTextDate;
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.pager_matches_item, container, false);
//            initComponents(rootView);
//            return rootView;
//        }
//
//        private void initComponents(View rootView) {
//            mMatch = (Match) getArguments().getSerializable(Constants.KEY_MATCH);
//            mViewTopSeperator = rootView.findViewById(R.id.view_topSeperator);
//            mViewBottomSeperator = rootView.findViewById(R.id.view_bottomSeperator);
//            mImageTeamLogo1 = (ImageView) rootView.findViewById(R.id.image_teamLogo1);
//            mTextTeamTitle1 = (TextView) rootView.findViewById(R.id.text_teamTitle1);
//            mImageTeamLogo2 = (ImageView) rootView.findViewById(R.id.image_teamLogo2);
//            mTextTeamTitle2 = (TextView) rootView.findViewById(R.id.text_teamTitle2);
//            mImageLeagueLogo = (ImageView) rootView.findViewById(R.id.image_leagueLogo);
//            mTextDate = (TextView) rootView.findViewById(R.id.text_matchDate);
//
//            // set data
//            mViewTopSeperator.setBackgroundColor(Color.parseColor(mMatch.getTeam1().getColor()));
//            mViewBottomSeperator.setBackgroundColor(Color.parseColor(mMatch.getTeam1().getColor()));
//            mImageTeamLogo1.setImageResource(mMatch.getTeam1().getLogo());
//            mTextTeamTitle1.setText(mMatch.getTeam1().getTitle());
//            mImageTeamLogo2.setImageResource(mMatch.getTeam2().getLogo());
//            mTextTeamTitle2.setText(mMatch.getTeam2().getTitle());
//            mImageLeagueLogo.setImageResource(mMatch.getLeagueLogo());
//            mTextDate.setText(mMatch.getDate());
//        }
//    }
}
