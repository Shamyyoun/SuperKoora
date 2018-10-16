package net.smartinnovationtechnology.superkoora;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import adapters.ScorersAdapter;
import datamodels.Scorer;
import datamodels.Team1;

/**
 * Created by Shamyyoun on 10/25/2015.
 */
public class TopScorersFragment extends Fragment implements View.OnClickListener {
    private Activity mActivity;
    private View mLayoutPlayerName;
    private View mLayoutTeam;
    private View mLayoutGoals;
    private RecyclerView mRecyclerView;
    private List<Scorer> mTopScores;
    private ScorersAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_top_scorers, container, false);
        initComponents(rootView);

        return rootView;
    }

    /**
     * method, used to initialize components
     */
    private void initComponents(View rootView) {
        mActivity = getActivity();
        mLayoutPlayerName = rootView.findViewById(R.id.layout_playerName);
        mLayoutTeam = rootView.findViewById(R.id.layout_team);
        mLayoutGoals = rootView.findViewById(R.id.layout_goals);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_top_scorers);

        // customize recycler view
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);

        mTopScores = new ArrayList<>();
        Team1 team1 = new Team1("Barcelona", R.drawable.debug_barca);
        team1.setColor("#ff5522");
        Team1 team2 = new Team1("Real Madrid", R.drawable.debug_real_madrid);
        team2.setColor("#aaaaff");
        Team1 team3 = new Team1("AC Milan", R.drawable.debug_real_madrid);
        team3.setColor("#ffaaaa");

        for (int i = 0; i < 10; i++) {
            Scorer scorer1 = new Scorer();
            scorer1.setTeam(team1);
            scorer1.setTitle("Player " + (i + 1));
            scorer1.setGoals((i + 1) * 4);
            scorer1.setPosition(i);
            mTopScores.add(scorer1);

            Scorer scorer2 = new Scorer();
            scorer2.setTeam(team2);
            scorer2.setTitle("Player " + (i*5 + 1));
            scorer2.setGoals((i + 1) * 5);
            scorer2.setPosition(i);
            mTopScores.add(scorer2);

            Scorer scorer3 = new Scorer();
            scorer3.setTeam(team3);
            scorer3.setTitle("Player " + (i * 10 + 1));
            scorer3.setGoals((i + 1) * 6);
            scorer3.setPosition(i);
            mTopScores.add(scorer3);
        }

        mAdapter = new ScorersAdapter(mActivity, mTopScores, R.layout.recycler_top_scorers_item);
        mRecyclerView.setAdapter(mAdapter);

        // add click listeners
        mLayoutPlayerName.setOnClickListener(this);
        mLayoutTeam.setOnClickListener(this);
        mLayoutGoals.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Comparator<Scorer> comparator = null;

        switch (v.getId()) {
            case R.id.layout_playerName:
                // create player name comparator
                comparator = new Comparator<Scorer>() {
                    @Override
                    public int compare(Scorer lhs, Scorer rhs) {
                        return lhs.getTitle().compareToIgnoreCase(rhs.getTitle());
                    }
                };
                break;

            case R.id.layout_team:
                // create team comparator
                comparator = new Comparator<Scorer>() {
                    @Override
                    public int compare(Scorer lhs, Scorer rhs) {
                        return lhs.getTeam().getTitle().compareToIgnoreCase(rhs.getTeam().getTitle());
                    }
                };
                break;

            case R.id.button_sortByGoals:
                // create goals comparator
                comparator = new Comparator<Scorer>() {
                    @Override
                    public int compare(Scorer lhs, Scorer rhs) {
                        if (lhs.getGoals() > rhs.getGoals())
                            return -1;
                        else if (lhs.getGoals() < rhs.getGoals())
                            return 1;
                        else
                            return 0;
                    }
                };
                break;
        }

        if (comparator != null) {
            // sort scorers using comparator
            Collections.sort(mTopScores, comparator);
            mAdapter.notifyDataSetChanged();
        }
    }
}
