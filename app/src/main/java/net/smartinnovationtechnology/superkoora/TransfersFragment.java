package net.smartinnovationtechnology.superkoora;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import adapters.TransfersAdapter;
import datamodels.Transfer;

/**
 * Created by Dahman on 9/15/2015.
 */
public class TransfersFragment extends Fragment implements View.OnClickListener {
    private MainActivity mActivity;

    private ImageButton mButtonSortByPlayerName;
    private ImageButton mButtonSortByNationality;
    private ImageButton mButtonSortByFromClub;
    private ImageButton mButtonSortByToClub;
    private ImageButton mButtonSortByFees;

    private RecyclerView mRecyclerView;
    private List<Transfer> mTransfers;
    private TransfersAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transfers, container, false);
        initComponents(rootView);

        return rootView;
    }

    /**
     * method, used to initialize components
     */
    private void initComponents(View rootView) {
        mActivity = (MainActivity) getActivity();
        mButtonSortByPlayerName = (ImageButton) rootView.findViewById(R.id.button_sortByPlayerName);
        mButtonSortByNationality = (ImageButton) rootView.findViewById(R.id.button_sortByNationality);
        mButtonSortByFromClub = (ImageButton) rootView.findViewById(R.id.button_sortByFromClub);
        mButtonSortByToClub = (ImageButton) rootView.findViewById(R.id.button_sortByToClub);
        mButtonSortByFees = (ImageButton) rootView.findViewById(R.id.button_sortByFees);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        // set activity title
        mActivity.setTitle(getString(R.string.transfers));

        // customize recycler view
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);

        mTransfers = new ArrayList<>();
        Transfer transfer1 = new Transfer();
        transfer1.setPlayerName("C.Ronaldo");
        transfer1.setNationalityImage(R.drawable.debug_portugal);
        transfer1.setNationalityTitle("Portugal");
        transfer1.setFromClubImage(R.drawable.debug_real_madrid);
        transfer1.setFromClubTitle("Real M.");
        transfer1.setToClubImage(R.drawable.debug_ajax);
        transfer1.setToClubTitle("Ajax");
        transfer1.setFees(20000000L);
        Transfer transfer2 = new Transfer();
        transfer2.setPlayerName("L.Messi");
        transfer2.setNationalityImage(R.drawable.debug_argentina);
        transfer2.setNationalityTitle("Argentina");
        transfer2.setFromClubImage(R.drawable.debug_barcelona);
        transfer2.setFromClubTitle("Barcelona");
        transfer2.setToClubImage(R.drawable.debug_porto);
        transfer2.setToClubTitle("Porto");
        transfer2.setFees(25000000L);
        Transfer transfer3 = new Transfer();
        transfer3.setPlayerName("W.Roney");
        transfer3.setNationalityImage(R.drawable.debug_england);
        transfer3.setNationalityTitle("England");
        transfer3.setFromClubImage(R.drawable.debug_manutd);
        transfer3.setFromClubTitle("Man Utd");
        transfer3.setToClubImage(R.drawable.debug_arsenal);
        transfer3.setToClubTitle("Arsenal");
        transfer3.setFees(250000);

        mTransfers.add(transfer1);
        mTransfers.add(transfer2);
        mTransfers.add(transfer3);
        mTransfers.add(transfer1);
        mTransfers.add(transfer2);
        mTransfers.add(transfer3);
        mTransfers.add(transfer1);
        mTransfers.add(transfer2);
        mTransfers.add(transfer3);
        mTransfers.add(transfer1);
        mTransfers.add(transfer2);
        mTransfers.add(transfer3);
        mTransfers.add(transfer1);
        mTransfers.add(transfer2);
        mTransfers.add(transfer3);
        mTransfers.add(transfer1);
        mTransfers.add(transfer2);
        mTransfers.add(transfer3);
        mTransfers.add(transfer1);
        mTransfers.add(transfer2);
        mTransfers.add(transfer3);
        mTransfers.add(transfer1);
        mTransfers.add(transfer2);
        mTransfers.add(transfer3);

        mAdapter = new TransfersAdapter(mActivity, mTransfers, R.layout.recycler_transfers_item);
        mRecyclerView.setAdapter(mAdapter);

        // add click listeners
        mButtonSortByPlayerName.setOnClickListener(this);
        mButtonSortByNationality.setOnClickListener(this);
        mButtonSortByFromClub.setOnClickListener(this);
        mButtonSortByToClub.setOnClickListener(this);
        mButtonSortByFees.setOnClickListener(this);
    }

    /**
     * overridden method, used to handle click listeners
     */
    @Override
    public void onClick(View v) {
        Comparator<Transfer> comparator = null;

        switch (v.getId()) {
            case R.id.button_sortByPlayerName:
                // create player name comparator
                comparator = new Comparator<Transfer>() {
                    @Override
                    public int compare(Transfer lhs, Transfer rhs) {
                        return lhs.getPlayerName().compareToIgnoreCase(rhs.getPlayerName());
                    }
                };
                break;

            case R.id.button_sortByNationality:
                // create nationality comparator
                comparator = new Comparator<Transfer>() {
                    @Override
                    public int compare(Transfer lhs, Transfer rhs) {
                        return lhs.getNationalityTitle().compareToIgnoreCase(rhs.getNationalityTitle());
                    }
                };
                break;

            case R.id.button_sortByFromClub:
                // create from club comparator
                comparator = new Comparator<Transfer>() {
                    @Override
                    public int compare(Transfer lhs, Transfer rhs) {
                        return lhs.getFromClubTitle().compareToIgnoreCase(rhs.getFromClubTitle());
                    }
                };
                break;

            case R.id.button_sortByToClub:
                // create to club comparator
                comparator = new Comparator<Transfer>() {
                    @Override
                    public int compare(Transfer lhs, Transfer rhs) {
                        return lhs.getToClubTitle().compareToIgnoreCase(rhs.getToClubTitle());
                    }
                };
                break;

            case R.id.button_sortByFees:
                // create fees comparator
                comparator = new Comparator<Transfer>() {
                    @Override
                    public int compare(Transfer lhs, Transfer rhs) {
                        if (lhs.getFees() > rhs.getFees())
                            return -1;
                        else if (lhs.getFees() < rhs.getFees())
                            return 1;
                        else
                            return 0;
                    }
                };
                break;
        }

        if (comparator != null) {
            // sort transfers using comparator
            Collections.sort(mTransfers, comparator);
            mAdapter.notifyDataSetChanged();
        }
    }
}
