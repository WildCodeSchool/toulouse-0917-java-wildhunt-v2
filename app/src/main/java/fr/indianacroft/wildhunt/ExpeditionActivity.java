package fr.indianacroft.wildhunt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ExpeditionActivity extends NavigationDrawerActivity {

    public static final String EXPEDITION_KEY = "EXPEDITION_KEY";
    private String mExpeditionKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        mExpeditionKey = intent.getStringExtra(EXPEDITION_KEY);
        changeStatus();
    }

    private void changeStatus() {
        boolean isUpdated = mExpeditionKey != null;
        setContentLayout(R.layout.activity_expedition,
                isUpdated ? R.string.expedition_update : R.string.expedition_create);

        Button cancel = findViewById(R.id.cancel);
        Button save = findViewById(R.id.create);
        cancel.setOnClickListener(view -> finish());
        save.setOnClickListener(view -> onCreate());

        if (isUpdated) {
            cancel.setVisibility(View.GONE);
            save.setText(R.string.update);
        } else {
            cancel.setVisibility(View.VISIBLE);
            save.setText(R.string.save);
        }
    }

    private void onCreate() {
        boolean isUpdated = mExpeditionKey != null;

        if (!hasErrors()) {
            EditText etTitle = findViewById(R.id.title);
            String title = etTitle.getText().toString();
            EditText etDescription = findViewById(R.id.description);
            String description = etDescription.getText().toString();
            Button save = findViewById(R.id.create);
            save.setEnabled(false);
            if (isUpdated) {
                FirebaseSingleton.getInstance().updateExpedition(mExpeditionKey, title, description);
            } else {
                FirebaseSingleton.getInstance().createExpedition(title, description, this::onSave);
            }
        }
    }

    private void onSave(String key) {
        Button save = findViewById(R.id.create);
        save.setEnabled(true);
        if (key != null) {
            mExpeditionKey = key;
            changeStatus();
        } else {
            // TODO show error to user
        }
    }

    private boolean hasErrors() {
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
        return hasErrors;
    }
}
