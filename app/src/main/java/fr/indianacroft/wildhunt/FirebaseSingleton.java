package fr.indianacroft.wildhunt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.annimon.stream.function.Consumer;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

class FirebaseSingleton {
    private static final FirebaseSingleton ourInstance = new FirebaseSingleton();
    private final static String TEMP_REF = "temp/";
    private GoogleSignInClient mGoogleSignInClient = null;
    private GoogleSignInAccount mGoogleSignInAccount = null;
    private FirebaseDatabase mDatabase = null;
    private UserModel mUser = null;

    private FirebaseSingleton() {
        mDatabase = FirebaseDatabase.getInstance();
        mDatabase.setPersistenceEnabled(true);
    }

    static FirebaseSingleton getInstance() {
        return ourInstance;
    }

    public void getUser(String id, Consumer<UserModel> user, Consumer<DatabaseError> error) {

        DatabaseReference userRef = mDatabase.getReference(TEMP_REF + "user").child(id);
        userRef.keepSynced(true);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mUser = dataSnapshot.getValue(UserModel.class);
                } else if (mGoogleSignInAccount != null
                        && id.equals(mGoogleSignInAccount.getId())) {
                    // create current user into database
                    String photoUrl = mGoogleSignInAccount.getPhotoUrl() == null ? "" :
                            mGoogleSignInAccount.getPhotoUrl().toString();
                    mUser = new UserModel(mGoogleSignInAccount.getId(),
                            mGoogleSignInAccount.getDisplayName(),
                            photoUrl);
                    userRef.setValue(mUser);
                }
                user.accept(mUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                error.accept(databaseError);
            }
        });
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
