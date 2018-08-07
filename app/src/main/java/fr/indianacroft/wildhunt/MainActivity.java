package fr.indianacroft.wildhunt;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends NavigationDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_main);
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
        ExpeditionAdapter expeditionAdapter = new ExpeditionAdapter(expeditions);
        rvExpeditions.setAdapter(expeditionAdapter);

        FirebaseSingleton.getInstance().getAllExpeditions(success -> {
            if (success == null) {
                // TODO show error to user
            } else {
                expeditions.clear();
                expeditions.addAll(success);
                expeditionAdapter.notifyDataSetChanged();
            }
        });
    }
}
