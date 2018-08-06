package fr.indianacroft.wildhunt;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseError;

public class SplashActivity extends AppCompatActivity {

    private final FirebaseSingleton mFirebaseSingleton = FirebaseSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().post(this::route);
    }

    private void route() {
        GoogleSignInAccount account = mFirebaseSingleton.getGoogleSignInAccount(this);
        if (account != null) {
            mFirebaseSingleton.getUser(account.getId(),
                    user -> goToHome(),
                    error -> {
                        // TODO : show an error to the user
                    });
        } else {
            goToLogin();
        }
    }

    private void goToHome() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
    }

    private void goToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
