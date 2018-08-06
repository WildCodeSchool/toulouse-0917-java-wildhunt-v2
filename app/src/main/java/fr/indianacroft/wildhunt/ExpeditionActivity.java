package fr.indianacroft.wildhunt;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ExpeditionActivity extends NavigationDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_expedition, R.string.expedition_create);

        Button cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(view -> finish());

        Button save = findViewById(R.id.create);
        save.setOnClickListener(view -> onCreate());
    }

    private void onCreate() {
        boolean hasErrors = false;
        EditText etTitle = findViewById(R.id.title);
        String title = etTitle.getText().toString();
        if (title.length() < 6) {
            hasErrors = true;
            etTitle.setError(getString(R.string.error_expedition_title_length));
        }
        EditText etDescription = findViewById(R.id.description);
        String description = etDescription.getText().toString();
        if (description.length() < 12) {
            hasErrors = true;
            etDescription.setError(getString(R.string.error_expedition_description_length));
        }
        if (!hasErrors) {
            Button save = findViewById(R.id.create);
            save.setEnabled(false);
            FirebaseSingleton.getInstance().createExpedition(title, description, success -> {
                if (success) {
                    Toast.makeText(this, "Success !", Toast.LENGTH_SHORT).show();
                } else {
                    save.setEnabled(true);
                    Toast.makeText(this, "Error !", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
