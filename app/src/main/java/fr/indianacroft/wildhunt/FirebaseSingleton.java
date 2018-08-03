package fr.indianacroft.wildhunt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

class FirebaseSingleton {
    private static final FirebaseSingleton ourInstance = new FirebaseSingleton();
    private GoogleSignInClient mGoogleSignInClient = null;
    private GoogleSignInAccount mGoogleSignInAccount = null;

    private FirebaseSingleton() {
    }

    static FirebaseSingleton getInstance() {
        return ourInstance;
    }

    public GoogleSignInClient getGoogleSignInClient(Context context) {
        if (mGoogleSignInClient != null) {
            return mGoogleSignInClient;
        }
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
        return mGoogleSignInClient;
    }

    public GoogleSignInAccount getGoogleSignInAccount(Context context) {
        if (mGoogleSignInAccount != null) {
            return mGoogleSignInAccount;
        }
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        mGoogleSignInAccount = GoogleSignIn.getLastSignedInAccount(context);
        return mGoogleSignInAccount;
    }

    public void setGoogleSignInAccount(GoogleSignInAccount googleSignInAccount) {
        mGoogleSignInAccount = googleSignInAccount;
    }

    public void disconnect(Activity activity) {
        GoogleSignInClient googleSignInClient = getGoogleSignInClient(activity);
        googleSignInClient.signOut()
                .addOnCompleteListener(activity,
                        task -> {
                            setGoogleSignInAccount(null);
                            activity.startActivity(new Intent(activity, LoginActivity.class));
                        });
    }
}
