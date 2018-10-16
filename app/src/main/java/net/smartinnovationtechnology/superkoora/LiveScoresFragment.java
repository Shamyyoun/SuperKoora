package net.smartinnovationtechnology.superkoora;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import adapters.CompetitionMatchesAdapter;
import datamodels.CompetitionMatches;
import datamodels.Match;
import datamodels.Team1;

/**
 * Created by Dahman on 9/15/2015.
 */
public class LiveScoresFragment extends Fragment implements View.OnClickListener {
    private MainActivity mActivity;
    private View mViewSwitch;
    private View mViewSwitchLeft;
    private ImageView mImageSwitchCenter;
    private View mViewSwitchRight;
    private View mViewDate;
    private TextView mTextDate;
    private RecyclerView mRecyclerView;

    private DatePickerDialog mDateDialog;
    private Calendar mCalendar;
    private int mDay;
    private int mMonth;
    private int mYear;

    private boolean switchLeft = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_live_scores, container, false);
        initComponents(rootView);

        return rootView;
    }

    /**
     * method, used to initialize components
     */
    private void initComponents(View rootView) {
        mActivity = (MainActivity) getActivity();
        mViewSwitch = rootView.findViewById(R.id.view_switch);
        mViewSwitchLeft = rootView.findViewById(R.id.view_switchLeft);
        mViewSwitchRight = rootView.findViewById(R.id.view_switchRight);
        mImageSwitchCenter = (ImageView) rootView.findViewById(R.id.image_switchCenter);
        mViewDate = rootView.findViewById(R.id.view_date);
        mTextDate = (TextView) rootView.findViewById(R.id.text_date);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_competitions);

        // set activity title
        mActivity.setTitle(getString(R.string.live_scores));

        // set current date
        mCalendar = Calendar.getInstance(Locale.getDefault());
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        mMonth = mCalendar.get(Calendar.MONTH);
        mYear = mCalendar.get(Calendar.YEAR);
        String dayStr = "" + mDay;
        if (dayStr.length() == 1)
            dayStr = "0" + dayStr;

        String monthStr = "" + (mMonth + 1);
        if (monthStr.length() == 1)
            monthStr = "0" + monthStr;
        mTextDate.setText(dayStr + "/" + monthStr);

        // customize recycler view
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);

        Team1 team1 = new Team1("Juvintus", R.drawable.debug_juvi_flag);
        Team1 team2 = new Team1("Al-Ahly", R.drawable.debug_ahly_flag);
        Match match = new Match(team1, team2, R.drawable.debug_uefa_logo, "22:45");
        List<Match> matches = new ArrayList<>();
        matches.add(match);
        matches.add(match);
        matches.add(match);
        matches.add(match);
        matches.add(match);

        CompetitionMatches competitionMatches = new CompetitionMatches("UEFA Champions League", R.drawable.debug_uefa_logo, 5, matches);
        List<CompetitionMatches> competitionMatchesList = new ArrayList<>();
        competitionMatchesList.add(competitionMatches);
        competitionMatchesList.add(competitionMatches);
        competitionMatchesList.add(competitionMatches);
        competitionMatchesList.add(competitionMatches);
        competitionMatchesList.add(competitionMatches);
        competitionMatchesList.add(competitionMatches);
        competitionMatchesList.add(competitionMatches);

        CompetitionMatchesAdapter adapter = new CompetitionMatchesAdapter(mActivity, competitionMatchesList, R.layout.recycler_competitions_item);
        mRecyclerView.setAdapter(adapter);

        // add listeners
        mViewSwitch.setOnClickListener(this);
        mViewDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_switch:
                // make it right or left
                mViewSwitchLeft.setBackgroundColor(getResources().getColor(switchLeft ? R.color.live_scores_switch_gray : R.color.live_scores_switch_blue));
                mViewSwitchRight.setBackgroundColor(getResources().getColor(switchLeft ? R.color.live_scores_switch_blue : R.color.live_scores_switch_gray));
                mImageSwitchCenter.setImageResource(switchLeft ? R.drawable.live_scores_switch_right : R.drawable.live_scores_switch_left);

                switchLeft = !switchLeft;
                break;

            case R.id.view_date:
                if (mDateDialog == null) {
                    // create it
                    mDateDialog = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            mDay = dayOfMonth;
                            mMonth = monthOfYear;

                            // update ui
                            String dayStr = "" + mDay;
                            if (dayStr.length() == 1)
                                dayStr = "0" + dayStr;

                            String monthStr = "" + (mMonth + 1);
                            if (monthStr.length() == 1)
                                monthStr = "0" + monthStr;
                            mTextDate.setText(dayStr + "/" + monthStr);
                        }
                    }, mYear, mMonth, mDay);
                } else {
                    // set date
                    mDateDialog.getDatePicker().init(mYear, mMonth, mDay, null);
                }
                mDateDialog.show();
                break;
        }
    }
}
