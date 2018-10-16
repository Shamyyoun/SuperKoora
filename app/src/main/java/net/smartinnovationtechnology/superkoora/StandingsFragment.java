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

import adapters.TeamsGroupsAdapter;
import datamodels.TeamGroup;
import datamodels.Standing;

/**
 * Created by Shamyyoun on 10/25/2015.
 */
public class StandingsFragment extends Fragment {
    private Activity mActivity;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_standings, container, false);
        initComponents(rootView);

        return rootView;
    }

    /**
     * method, used to initialize components
     */
    private void initComponents(View rootView) {
        mActivity = getActivity();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_teamsGroups);

        // customize recycler view
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);

        List<Standing> standings = new ArrayList<>();
        for (int i = 0; i <= 4; i++) {
            Standing standing = new Standing(R.drawable.debug_brazil_flag_round, "Brazil", 1, 2, 3, 4, 5, 6, 7, 8);
            standings.add(standing);
        }

        List<TeamGroup> teamGroups = new ArrayList<>();
        teamGroups.add(new TeamGroup("A", standings));
        teamGroups.add(new TeamGroup("B", standings));
        teamGroups.add(new TeamGroup("C", standings));
        teamGroups.add(new TeamGroup("D", standings));
        teamGroups.add(new TeamGroup("E", standings));

        TeamsGroupsAdapter adapter = new TeamsGroupsAdapter(mActivity, teamGroups, R.layout.recycler_teams_groups_item);
        mRecyclerView.setAdapter(adapter);
    }
}
