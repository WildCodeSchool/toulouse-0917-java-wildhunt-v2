package fr.indianacroft.wildhunt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExpeditionListActivity extends NavigationDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_expedition_list, R.string.my_expeditions);

        FloatingActionButton fabCreateExpedition = findViewById(R.id.fab_create_expedition);
        fabCreateExpedition.setOnClickListener(view -> goToCreateExpedition());
    }

    @Override
    protected void onResume() {
        super.onResume();

        new Handler().post(this::populateExpeditions);
    }

    private void populateExpeditions() {

        RecyclerView rvExpeditions = findViewById(R.id.expeditions);
        rvExpeditions.setHasFixedSize(true);

        LinearLayoutManager expeditionLayoutManager = new LinearLayoutManager(this);
        rvExpeditions.setLayoutManager(expeditionLayoutManager);

        List<ExpeditionModel> expeditions = new ArrayList<>();
        UserModel user = FirebaseSingleton.getInstance().getUser();
        if (user != null) {
            Map<String, ExpeditionModel> userExpeditions = user.getCreated();
            if (userExpeditions != null && userExpeditions.values().size() > 0) {
                expeditions = new ArrayList<>(userExpeditions.values());
            } else {
                finish();
            }
        }
        ExpeditionAdapter expeditionAdapter = new ExpeditionAdapter(expeditions);
        rvExpeditions.setAdapter(expeditionAdapter);
    }

    private void goToCreateExpedition() {
        startActivity(new Intent(this, ExpeditionActivity.class));
    }
}
