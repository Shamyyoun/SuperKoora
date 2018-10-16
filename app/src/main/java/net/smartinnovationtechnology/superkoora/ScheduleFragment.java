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
import java.util.List;

import adapters.MatchesGroupsAdapter;
import datamodels.Match;
import datamodels.MatchesGroup;
import datamodels.Team1;

/**
 * Created by Shamyyoun on 10/25/2015.
 */
public class ScheduleFragment extends Fragment {
    private Activity mActivity;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);
        initComponents(rootView);

        return rootView;
    }

    /**
     * method, used to initialize components
     */
    private void initComponents(View rootView) {
        mActivity = getActivity();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_matchesGroups);

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

        List<MatchesGroup> matchesGroups = new ArrayList<>();
        matchesGroups.add(new MatchesGroup("11/10/2015", matches));
        matchesGroups.add(new MatchesGroup("12/10/2015", matches));
        matchesGroups.add(new MatchesGroup("13/10/2015", matches));
        matchesGroups.add(new MatchesGroup("14/10/2015", matches));

        MatchesGroupsAdapter adapter = new MatchesGroupsAdapter(mActivity, matchesGroups, R.layout.recycler_matches_groups_item);
        mRecyclerView.setAdapter(adapter);
    }
}
