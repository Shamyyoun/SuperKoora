package net.smartinnovationtechnology.superkoora;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class H2HResultActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton mButtonIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h2h_result);

        initComponents();
    }

    /**
     * method, used to init components
     */
    private void initComponents() {
        mButtonIcon = (ImageButton) findViewById(R.id.button_back);

        // customize actionbar
        mButtonIcon.setImageResource(R.drawable.back_icon);
        mButtonIcon.setOnClickListener(this);

    }

    /**
     * overridden method, used to handle click listeners
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                onBackPressed();
                break;
        }
    }
}
