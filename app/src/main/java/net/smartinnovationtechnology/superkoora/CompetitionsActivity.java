package net.smartinnovationtechnology.superkoora;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import adapters.CompetitionsAdapter;
import datamodels.Competition;
import datamodels.Constants;
import datamodels.Continent;

public class CompetitionsActivity extends AppCompatActivity {
    private Continent mContinent;
    private ImageButton mButtonIcon;
    private ImageView mImageHeaderBackground;
    private ImageView mImageHeaderLogo;
    private ListView mListCompetitions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competitions);

        initComponents();
    }

    /**
     * method, used to init components
     */
    private void initComponents() {
        mContinent = (Continent) getIntent().getSerializableExtra(Constants.KEY_CONTINENT);
        mButtonIcon = (ImageButton) findViewById(R.id.button_back);
        mImageHeaderBackground = (ImageView) findViewById(R.id.image_headerBackground);
        mImageHeaderLogo = (ImageView) findViewById(R.id.image_headerLogo);
        mListCompetitions = (ListView) findViewById(R.id.listView);

        // customize header
        mImageHeaderBackground.setImageResource(mContinent.getBackgroundResId());
        mImageHeaderLogo.setImageResource(mContinent.getLogoResId());
        mButtonIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // customize list view
        final List<Competition> competitions = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Competition competition = new Competition(mContinent.getBackgroundResId(), R.drawable.debug_brazil_com_logo, "Competition " + (i + 1));
            competitions.add(competition);
        }
        CompetitionsAdapter adapter = new CompetitionsAdapter(this, R.layout.list_competitions_item, competitions);
        mListCompetitions.setAdapter(adapter);
        mListCompetitions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // start competition details activitu
                Competition competition = competitions.get(position);
                Intent intent = new Intent(CompetitionsActivity.this, CompetitionDetailsActivity.class);
                intent.putExtra(Constants.KEY_COMPETITION, competition);
                startActivity(intent);
            }
        });
    }
}
