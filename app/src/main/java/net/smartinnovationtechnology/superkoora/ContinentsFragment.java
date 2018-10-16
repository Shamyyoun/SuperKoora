package net.smartinnovationtechnology.superkoora;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import datamodels.Constants;
import datamodels.Continent;

/**
 * Created by Dahman on 9/15/2015.
 */
public class ContinentsFragment extends Fragment implements View.OnClickListener {
    private MainActivity mActivity;
    private View mViewFifa;
    private View mViewCaf;
    private View mViewAfc;
    private View mViewConcacaf;
    private View mViewConembol;
    private View mViewOfc;
    private View mViewUefa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_continents, container, false);
        initComponents(rootView);

        return rootView;
    }

    /**
     * method, used to initialize components
     */
    private void initComponents(View rootView) {
        mActivity = (MainActivity) getActivity();
        mViewFifa = rootView.findViewById(R.id.view_fifa);
        mViewCaf = rootView.findViewById(R.id.view_caf);
        mViewAfc = rootView.findViewById(R.id.view_afc);
        mViewConcacaf = rootView.findViewById(R.id.view_concacaf);
        mViewConembol = rootView.findViewById(R.id.view_conembol);
        mViewOfc = rootView.findViewById(R.id.view_ofc);
        mViewUefa = rootView.findViewById(R.id.view_uefa);

        // set activity title
        mActivity.setTitle(getString(R.string.competitions));

        // add click listeners
        mViewFifa.setOnClickListener(this);
        mViewCaf.setOnClickListener(this);
        mViewAfc.setOnClickListener(this);
        mViewConcacaf.setOnClickListener(this);
        mViewConembol.setOnClickListener(this);
        mViewOfc.setOnClickListener(this);
        mViewUefa.setOnClickListener(this);
    }

    /**
     * overridden method, used to handle click listeners
     */
    @Override
    public void onClick(View v) {
        Continent continent = null;
        switch (v.getId()) {
            case R.id.view_fifa:
                continent = new Continent(Constants.CONTINENT_FIFA, R.drawable.fifa_bg, R.drawable.fifa_logo);
                break;
            case R.id.view_caf:
                continent = new Continent(Constants.CONTINENT_CAF, R.drawable.caf_bg, R.drawable.caf_logo);
                break;
            case R.id.view_afc:
                continent = new Continent(Constants.CONTINENT_AFC, R.drawable.afc_bg, R.drawable.afc_logo);
                break;
            case R.id.view_concacaf:
                continent = new Continent(Constants.CONTINENT_CONCACAF, R.drawable.concacaf_bg, R.drawable.concacaf_logo);
                break;
            case R.id.view_conembol:
                continent = new Continent(Constants.CONTINENT_CONEMBOL, R.drawable.conembol_bg, R.drawable.conembol_logo);
                break;
            case R.id.view_ofc:
                continent = new Continent(Constants.CONTINENT_OFC, R.drawable.ofc_bg, R.drawable.ofc_logo);
                break;
            case R.id.view_uefa:
                continent = new Continent(Constants.CONTINENT_UEFA, R.drawable.uefa_bg, R.drawable.uefa_logo);
                break;
        }

        // check continent
        if (continent != null) {
            // open competitions activity
            Intent intent = new Intent(mActivity, CompetitionsActivity.class);
            intent.putExtra(Constants.KEY_CONTINENT, continent);
            startActivity(intent);
        }
    }
}
