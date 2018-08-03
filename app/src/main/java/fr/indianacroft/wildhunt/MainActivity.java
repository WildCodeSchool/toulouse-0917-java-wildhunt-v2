package fr.indianacroft.wildhunt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class MainActivity extends AppCompatActivity {

    private final FirebaseSingleton mFirebaseSingleton = FirebaseSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO : check if user account exists and if user is logged in, return to login otherwise
        Button button = findViewById(R.id.disconnect);
        button.setOnClickListener(view -> mFirebaseSingleton.disconnect(this));
    }

    @Override
    protected void onResume() {
        super.onResume();

        GoogleSignInAccount account = mFirebaseSingleton.getGoogleSignInAccount(this);
        if (account == null || account.isExpired()) {
            mFirebaseSingleton.setGoogleSignInAccount(null);
            finish();
        }
    }
}
