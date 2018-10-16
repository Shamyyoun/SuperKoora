package views;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.smartinnovationtechnology.superkoora.R;

public abstract class SwipeProgressFragment extends ProgressFragment {
    private static final int SWIPE_PROGRESS_MIN_DURATION = 2000;
    private SwipeRefreshLayout swipeLayout;
    private long swipeProgressStart; // used to hold time when swipe layout start progress

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        // customize swipe layout
        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeLayout);
        swipeLayout.setColorSchemeResources(
                R.color.primary_dark,
                R.color.primary,
                R.color.gray_text);

        // add action listeners
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SwipeProgressFragment.this.onRefresh();
            }
        });

        return rootView;
    }

    /*
    * overridden method used to show main viewState
    */
    @Override
    protected void showMain() {
        // do super method
        super.showMain();
        // stop swipe layout refreshing if it is
        if (swipeLayout.isRefreshing()) {
            // check swipe progress start time
            if (System.currentTimeMillis() >= (swipeProgressStart + SWIPE_PROGRESS_MIN_DURATION)) {
                // exceeded min duration >> stop it
                swipeLayout.setRefreshing(false);
            } else {
                // didn't exceed min duration >> stop it after time difference
                swipeLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeLayout.setRefreshing(false);
                    }
                }, (swipeProgressStart + SWIPE_PROGRESS_MIN_DURATION) - System.currentTimeMillis());
            }
        }
    }

    /*
     * overridden method used to show progress if it is possible
     */
    @Override
    protected void showProgress() {
        // do super method
        super.showProgress();
        // check view state
        if (viewState == VIEW_STATE_MAIN) {
            // show swipe layout progress if not visible
            if (!swipeLayout.isRefreshing())
                swipeLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeLayout.setRefreshing(true);
                        swipeProgressStart = System.currentTimeMillis(); // save start time of swipe progress
                    }
                }, 100);
        }
    }

    /*
     * overridden method, used to show the suitable error msg
     */
    @Override
    protected void showTheError() {
        // do super method
        super.showTheError();
        // check if main view is visible
        if (viewState == VIEW_STATE_MAIN) {
            // visible, so stop swipe layout refreshing if it is
            if (swipeLayout.isRefreshing()) {
                // check swipe progress start time
                if (System.currentTimeMillis() >= (swipeProgressStart + SWIPE_PROGRESS_MIN_DURATION)) {
                    // exceeded min duration >> stop it
                    swipeLayout.setRefreshing(false);
                } else {
                    // didn't exceed min duration >> stop it after time difference
                    swipeLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeLayout.setRefreshing(false);
                        }
                    }, (swipeProgressStart + SWIPE_PROGRESS_MIN_DURATION) - System.currentTimeMillis());
                }
            }
        }
    }
}
