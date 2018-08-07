package fr.indianacroft.wildhunt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

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
                    user -> {
                        if (user == null) {
                            // TODO show error to user
                        } else {
                            goToHome();
                        }
                    }
            );
        } else {
            goToLogin();
        }
    }

    private void goToHome() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    private void goToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
