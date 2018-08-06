package fr.indianacroft.wildhunt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

public class MainActivity extends NavigationDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_main, 0);

        FloatingActionButton fabCreateExpedition = findViewById(R.id.fab_create_expedition);
        fabCreateExpedition.setOnClickListener(view -> goToCreateExpedition());
    }

    private void goToCreateExpedition() {
        startActivity(new Intent(this, ExpeditionActivity.class));
    }
}
