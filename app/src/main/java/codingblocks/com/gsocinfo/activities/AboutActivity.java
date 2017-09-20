package codingblocks.com.gsocinfo.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;

import codingblocks.com.gsocinfo.R;
import codingblocks.com.gsocinfo.fragments.FaqFragment;
import codingblocks.com.gsocinfo.fragments.MainPageFragment;
import codingblocks.com.gsocinfo.fragments.OrganizationFragment;
import codingblocks.com.gsocinfo.fragments.ProjectFragment;

public class AboutActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    MainPageFragment mainPageFragment;
    OrganizationFragment organizationFragment;
    ProjectFragment projectFragment;
    Toolbar toolbar;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.about);
        FloatingActionButton fab = findViewById(R.id.fab);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_about, MainPageFragment.newInstance())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_about);
        }

        if (!sharedPreferences.getBoolean("main_activity_target", false)) {
            new TapTargetSequence(this)
                    .continueOnCancel(true)
                    .targets(
                            TapTarget.forToolbarNavigationIcon(toolbar, getString(R.string.nav_drawer_target))
                                    .targetCircleColor(R.color.colorAccent)
                                    .drawShadow(true)
                                    .tintTarget(true)
                                    .outerCircleAlpha(0.90f).id(1),
                            TapTarget.forToolbarMenuItem(toolbar, R.id.action_search, getString(R.string.search_target_title), getString(R.string.search_target_desc))
                                    .targetCircleColor(R.color.colorAccent)
                                    .drawShadow(true)
                                    .tintTarget(true)
                                    .outerCircleAlpha(0.90f).id(2),
                            TapTarget.forView(fab, getString(R.string.mentor_target_title), getString(R.string.mentor_target_desc))
                                    .drawShadow(true)
                                    .tintTarget(true)
                                    .targetCircleColor(R.color.colorAccent)
                                    .outerCircleAlpha(0.90f).id(3)

                    )
                    .listener(new TapTargetSequence.Listener() {
                        @Override
                        public void onSequenceFinish() {
                            sharedPreferences.edit().putBoolean("main_activity_target", true).apply();
                        }

                        @Override
                        public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                        }

                        @Override
                        public void onSequenceCanceled(TapTarget lastTarget) {
                        }
                    }).start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_about) {
            mainPageFragment = MainPageFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_about,
                            mainPageFragment,
                            "ABOUT")
                    .commit();
        } else if (id == R.id.nav_organizations) {
            organizationFragment = OrganizationFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_about,
                            organizationFragment,
                            "ORGANIZATION")
                    .commit();
        } else if (id == R.id.nav_projects) {
            projectFragment = ProjectFragment.newInstance("");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_about,
                            projectFragment,
                            "PROJECT")
                    .commit();
        } else if (id == R.id.nav_faq) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_about, FaqFragment.newInstance())
                    .commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
