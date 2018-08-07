package fr.indianacroft.wildhunt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.Map;

public abstract class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final FirebaseSingleton mFirebaseSingleton = FirebaseSingleton.getInstance();
    private String mAccountId;
    private boolean mHasExpeditions = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_drawer);

        initNavigation();
    }

    /**
     * @param layout layout id to inject in the navigation drawer container
     */
    public void setContentLayout(int layout) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View inflatedLayout = inflater.inflate(layout, null, false);

        LinearLayout container = findViewById(R.id.content);
        container.addView(inflatedLayout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
    }

    /**
     * @param layout layout id to inject in the navigation drawer container
     * @param title  string id of the activity title
     */
    public void setContentLayout(int layout, int title) {
        setContentLayout(layout);
        if (title != 0) {
            setTitle(title);
        } else {
            setTitle(R.string.app_name);
        }
    }

    private void initNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        GoogleSignInAccount account = mFirebaseSingleton.getGoogleSignInAccount(this);
        if (account != null) {
            mAccountId = account.getId();
            View header = navigationView.getHeaderView(0);
            TextView name = header.findViewById(R.id.name);
            String personName = account.getDisplayName() != null ?
                    account.getDisplayName() : account.getEmail();
            name.setText(personName);
            ImageView avatar = header.findViewById(R.id.avatar);
            GlideApp.with(this)
                    .load(account.getPhotoUrl())
                    .fallback(R.drawable.ic_face_black_24dp)
                    .apply(RequestOptions.circleCropTransform()).into(avatar);
        } else {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkUserAccountExists();
    }

    private void checkUserAccountExists() {
        GoogleSignInAccount account = mFirebaseSingleton.getGoogleSignInAccount(this);
        if (account == null) {
            mFirebaseSingleton.clearHistory(this);
        } else if (!mAccountId.equals(account.getId())) {
            finish();
        } else {
            UserModel user = mFirebaseSingleton.getUser();
            if (user != null) {
                Map<String, ExpeditionModel> userExpeditions = user.getCreated();
                if (userExpeditions != null && userExpeditions.values().size() > 0) {
                    mHasExpeditions = true;
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()) {
            case R.id.my_expeditions:
                if (mHasExpeditions) {
                    startActivity(new Intent(this, ExpeditionListActivity.class));
                } else {
                    startActivity(new Intent(this, ExpeditionActivity.class));
                }
                break;
            case R.id.nav_disconnect:
                mFirebaseSingleton.disconnect(this);
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
