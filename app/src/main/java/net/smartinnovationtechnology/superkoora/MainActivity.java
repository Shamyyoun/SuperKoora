package net.smartinnovationtechnology.superkoora;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapters.MenuDrawerAdapter;
import datamodels.Constants;
import datamodels.MenuItem;

public class MainActivity extends AppCompatActivity {
    public static final int MENU_DRAWER_GRAVITY = GravityCompat.START;

    private ImageButton mButtonIcon;
    private TextView mTextTitle;
    private DrawerLayout mMenuDrawer;
    private ListView mListMenu;
    private int lastSelectedId; // used to hold last selected item in menu drawer to skip if user clicks twice

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
    }

    /**
     * method, used to init components
     */
    private void initComponents() {
        mButtonIcon = (ImageButton) findViewById(R.id.button_icon);
        mTextTitle = (TextView) findViewById(R.id.text_title);
        mMenuDrawer = (DrawerLayout) findViewById(R.id.menuDrawer);
        mListMenu = (ListView) findViewById(R.id.listView);

        // customize menu drawer
        mMenuDrawer.setDrawerShadow(R.drawable.md_shadow, MENU_DRAWER_GRAVITY);
        MenuItem menuItem1 = new MenuItem(Constants.MENU_ITEM_HOME, getString(R.string.home));
        MenuItem menuItem2 = new MenuItem(Constants.MENU_ITEM_NEWS, getString(R.string.news));
        MenuItem menuItem3 = new MenuItem(Constants.MENU_ITEM_VIDEOS, getString(R.string.videos));
        MenuItem menuItem4 = new MenuItem(Constants.MENU_ITEM_H2H, getString(R.string.head2head));
        MenuItem menuItem5 = new MenuItem(Constants.MENU_ITEM_TRANSFERS, getString(R.string.transfers));
        MenuItem menuItem6 = new MenuItem(Constants.MENU_ITEM_LIVE_SCORES, getString(R.string.live_scores));
        MenuItem menuItem7 = new MenuItem(Constants.MENU_ITEM_COMPETITIONS, getString(R.string.competitions));
        final List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(menuItem1);
        menuItems.add(menuItem2);
        menuItems.add(menuItem3);
        menuItems.add(menuItem4);
        menuItems.add(menuItem5);
        menuItems.add(menuItem6);
        menuItems.add(menuItem7);
        MenuDrawerAdapter menuDrawerAdapter = new MenuDrawerAdapter(this, R.layout.list_menu_drawer_item, menuItems);
        mListMenu.setAdapter(menuDrawerAdapter);
        mListMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // get selected it
                MenuItem selectedItem = menuItems.get(position);

                // ensure not same choice in menu drawer
                if (selectedItem.getId() != lastSelectedId) {
                    // prepare fm and ft
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Fragment fragment = null;
                    Bundle bundle = new Bundle();

                    if (selectedItem.getId() == Constants.MENU_ITEM_HOME) {
                        fragment = new HomeFragment();
                    } else if (selectedItem.getId() == Constants.MENU_ITEM_NEWS) {
                        fragment = new NewsFragment();
                    } else if (selectedItem.getId() == Constants.MENU_ITEM_VIDEOS) {
                        fragment = new VideosFragment();
                    } else if (selectedItem.getId() == Constants.MENU_ITEM_H2H) {
                        fragment = new H2HFragment();
                    } else if (selectedItem.getId() == Constants.MENU_ITEM_TRANSFERS) {
                        fragment = new TransfersFragment();
                    } else if (selectedItem.getId() == Constants.MENU_ITEM_LIVE_SCORES) {
                        fragment = new LiveScoresFragment();
                    } else if (selectedItem.getId() == Constants.MENU_ITEM_COMPETITIONS) {
                        fragment = new ContinentsFragment();
                    }

                    // load fragment with bundle
                    fragment.setArguments(bundle);
                    ft.replace(R.id.container, fragment);

                    // commit transaction
                    ft.commit();

                    // save selected item id
                    lastSelectedId = selectedItem.getId();
                }

                // close menu drawer
                mMenuDrawer.closeDrawer(MENU_DRAWER_GRAVITY);
            }
        });

        // load home fragment
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, new HomeFragment());
        ft.commit();
        lastSelectedId = Constants.MENU_ITEM_HOME;

        // add listeners
        mButtonIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMenuDrawer.isDrawerOpen(MENU_DRAWER_GRAVITY)) {
                    mMenuDrawer.closeDrawer(MENU_DRAWER_GRAVITY);
                } else {
                    mMenuDrawer.openDrawer(MENU_DRAWER_GRAVITY);
                }
            }
        });
    }

    /**
     * protected method, used to set activity title in actionBar
     */
    protected void setTitle(String title) {
        mTextTitle.setText(title);
    }

    /**
     * overridden method
     */
    @Override
    public void onBackPressed() {
        if (mMenuDrawer.isDrawerOpen(MENU_DRAWER_GRAVITY)) {
            mMenuDrawer.closeDrawer(MENU_DRAWER_GRAVITY);
        } else {
            super.onBackPressed();
        }
    }
}
