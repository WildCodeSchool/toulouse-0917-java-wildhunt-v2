package fr.indianacroft.wildhunt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends NavigationDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_main);

        FloatingActionButton fabCreateExpedition = findViewById(R.id.fab_create_expedition);
        fabCreateExpedition.setOnClickListener(view -> goToCreateExpedition());
    }

    @Override
    protected void onResume() {
        super.onResume();

        populateExpeditions();
    }

    private void populateExpeditions() {

        RecyclerView rvExpeditions = findViewById(R.id.expeditions);
        rvExpeditions.setHasFixedSize(true);

        LinearLayoutManager expeditionLayoutManager = new LinearLayoutManager(this);
        rvExpeditions.setLayoutManager(expeditionLayoutManager);

        List<ExpeditionModel> expeditions = new ArrayList<>();
        ExpeditionAdapter expeditionAdapter = new ExpeditionAdapter(expeditions);
        rvExpeditions.setAdapter(expeditionAdapter);

        FirebaseSingleton.getInstance().getExpeditions(success -> {
            if (success == null) {
                // TODO show error to user
            } else {
                expeditions.clear();
                expeditions.addAll(success);
                expeditionAdapter.notifyDataSetChanged();
            }
        });
    }

    private void goToCreateExpedition() {
        startActivity(new Intent(this, ExpeditionActivity.class));
    }
}
