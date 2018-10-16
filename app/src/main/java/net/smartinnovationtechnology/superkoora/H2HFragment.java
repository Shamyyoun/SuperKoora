package net.smartinnovationtechnology.superkoora;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapters.SpinnerAdapter;
import datamodels.Country;
import datamodels.Team1;

/**
 * Created by Dahman on 9/15/2015.
 */
public class H2HFragment extends Fragment implements View.OnClickListener {
    private MainActivity mActivity;
    private Spinner mSpinnerTTCountryA;
    private Spinner mSpinnerTTTeamA;
    private Spinner mSpinnerTTCountryB;
    private Spinner mSpinnerTTTeamB;
    private TextView mTextTTSubmit;
    private Spinner mSpinnerNNCountryA;
    private Spinner mSpinnerNNTeamA;
    private Spinner mSpinnerNNCountryB;
    private Spinner mSpinnerNNTeamB;
    private TextView mTextNNSubmit;
    private Spinner mSpinnerTNTCountryA;
    private Spinner mSpinnerTNTCountryB;
    private Spinner mSpinnerTNTTeamB;
    private TextView mTextTNTSubmit;
    private Spinner mSpinnerNTNTCountryA;
    private Spinner mSpinnerNTNTCountryB;
    private TextView mTextNTNTSubmit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_h2h, container, false);
        initComponents(rootView);

        return rootView;
    }

    /**
     * method, used to initialize components
     */
    private void initComponents(View rootView) {
        mActivity = (MainActivity) getActivity();
        mSpinnerTTCountryA = (Spinner) rootView.findViewById(R.id.spinner_ttCountryA);
        mSpinnerTTTeamA = (Spinner) rootView.findViewById(R.id.spinner_ttTeamA);
        mSpinnerTTCountryB = (Spinner) rootView.findViewById(R.id.spinner_ttCountryB);
        mSpinnerTTTeamB = (Spinner) rootView.findViewById(R.id.spinner_ttTeamB);
        mTextTTSubmit = (TextView) rootView.findViewById(R.id.text_ttSubmit);
        mSpinnerNNCountryA = (Spinner) rootView.findViewById(R.id.spinner_nnCountryA);
        mSpinnerNNTeamA = (Spinner) rootView.findViewById(R.id.spinner_nnTeamA);
        mSpinnerNNCountryB = (Spinner) rootView.findViewById(R.id.spinner_nnCountryB);
        mSpinnerNNTeamB = (Spinner) rootView.findViewById(R.id.spinner_nnTeamB);
        mTextNNSubmit = (TextView) rootView.findViewById(R.id.text_nnSubmit);
        mSpinnerTNTCountryA = (Spinner) rootView.findViewById(R.id.spinner_tntCountryA);
        mSpinnerTNTCountryB = (Spinner) rootView.findViewById(R.id.spinner_tntCountryB);
        mSpinnerTNTTeamB = (Spinner) rootView.findViewById(R.id.spinner_tntTeamB);
        mTextTNTSubmit = (TextView) rootView.findViewById(R.id.text_tntSubmit);
        mSpinnerNTNTCountryA = (Spinner) rootView.findViewById(R.id.spinner_ntntCountryA);
        mSpinnerNTNTCountryB = (Spinner) rootView.findViewById(R.id.spinner_ntntCountryB);
        mTextNTNTSubmit = (TextView) rootView.findViewById(R.id.text_ntntSubmit);

        // set activity title
        mActivity.setTitle(getString(R.string.head2head));

        // set spinner adapters
        Team1 defaultTeamA = new Team1();
        defaultTeamA.setTitle(getString(R.string.select_team_a));
        Team1 defaultTeamB = new Team1();
        defaultTeamB.setTitle(getString(R.string.select_team_b));
        Country defaultCountryA = new Country(-1, getString(R.string.select_country_a), defaultTeamA);
        Country defaultCountryB = new Country(-1, getString(R.string.select_country_b), defaultTeamB);

        final List<Country> countries = new ArrayList<>();
        List<Team1> egTeams = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Team1 team = new Team1();
            team.setTitle("Egypt Team " + (i + 1));
            egTeams.add(team);
        }
        Country country1 = new Country(1, "Egypt", egTeams);

        List<Team1> moTeams = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Team1 team = new Team1();
            team.setTitle("Morooco Team " + (i + 1));
            moTeams.add(team);
        }
        Country country2 = new Country(2, "Morooco", moTeams);

        List<Team1> suTeams = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Team1 team = new Team1();
            team.setTitle("Sudan Team " + (i + 1));
            suTeams.add(team);
        }
        Country country3 = new Country(3, "Sudan", suTeams);
        countries.add(country1);
        countries.add(country2);
        countries.add(country3);

        final List<Country> countriesA = new ArrayList<>(countries);
        countriesA.add(0, defaultCountryA);
        SpinnerAdapter<Country> countriesAAdapter = new SpinnerAdapter(mActivity, R.layout.spinner_item, countriesA);
        mSpinnerTTCountryA.setAdapter(countriesAAdapter);
        mSpinnerNNCountryA.setAdapter(countriesAAdapter);
        mSpinnerTNTCountryA.setAdapter(countriesAAdapter);
        mSpinnerNTNTCountryA.setAdapter(countriesAAdapter);

        final List<Country> countriesB = new ArrayList<>(countries);
        countriesB.add(0, defaultCountryB);
        SpinnerAdapter<Country> countriesBAdapter = new SpinnerAdapter(mActivity, R.layout.spinner_item, countriesB);
        mSpinnerTTCountryB.setAdapter(countriesBAdapter);
        mSpinnerNNCountryB.setAdapter(countriesBAdapter);
        mSpinnerTNTCountryB.setAdapter(countriesBAdapter);
        mSpinnerNTNTCountryB.setAdapter(countriesBAdapter);

        // add spinner listeners
        mSpinnerTTCountryA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // change teamsA
                SpinnerAdapter<Team1> teamsAdapter = new SpinnerAdapter(mActivity, R.layout.spinner_item, countriesA.get(position).getTeams());
                mSpinnerTTTeamA.setAdapter(teamsAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        mSpinnerTTCountryB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // change teamsB
                SpinnerAdapter<Team1> teamsAdapter = new SpinnerAdapter(mActivity, R.layout.spinner_item, countriesB.get(position).getTeams());
                mSpinnerTTTeamB.setAdapter(teamsAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        mSpinnerNNCountryA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // change teamsA
                SpinnerAdapter<Team1> teamsAdapter = new SpinnerAdapter(mActivity, R.layout.spinner_item, countriesA.get(position).getTeams());
                mSpinnerNNTeamA.setAdapter(teamsAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        mSpinnerNNCountryB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // change teamsB
                SpinnerAdapter<Team1> teamsAdapter = new SpinnerAdapter(mActivity, R.layout.spinner_item, countriesB.get(position).getTeams());
                mSpinnerNNTeamB.setAdapter(teamsAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        mSpinnerTNTCountryB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // change teamsB
                SpinnerAdapter<Team1> teamsAdapter = new SpinnerAdapter(mActivity, R.layout.spinner_item, countriesB.get(position).getTeams());
                mSpinnerTNTTeamB.setAdapter(teamsAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // add click listeners
        mTextTTSubmit.setOnClickListener(this);
        mTextNNSubmit.setOnClickListener(this);
        mTextTNTSubmit.setOnClickListener(this);
        mTextNTNTSubmit.setOnClickListener(this);
    }

    /**
     * overridden method, used to handle click listeners
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mActivity, H2HResultActivity.class);
        switch (v.getId()) {
            case R.id.text_ttSubmit:
            case R.id.text_nnSubmit:
            case R.id.text_tntSubmit:
            case R.id.text_ntntSubmit:
                startActivity(intent);
                break;
        }
    }
}
