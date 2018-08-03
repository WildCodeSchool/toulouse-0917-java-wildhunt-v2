package fr.indianacroft.wildhunt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 2031;
    private static final String TAG = "DEBUG_LA";
    private final FirebaseSingleton mFirebaseSingleton = FirebaseSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        updateUI(mFirebaseSingleton.getGoogleSignInAccount(this));

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(view -> signIn());
    }

    private void updateUI(GoogleSignInAccount account) {
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        if (account == null) {
            signInButton.setVisibility(View.VISIBLE);
        } else {
            signInButton.setVisibility(View.GONE);
            // TODO : check if user account exists and creates it otherwise
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private void signIn() {
        Intent signInIntent = mFirebaseSingleton.getGoogleSignInClient(this)
                .getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            mFirebaseSingleton.setGoogleSignInAccount(account);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
}
