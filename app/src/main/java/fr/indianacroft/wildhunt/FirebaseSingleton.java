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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class FirebaseSingleton {
    private static final FirebaseSingleton ourInstance = new FirebaseSingleton();
    private final static String TEMP_REF = "temp/";
    private FirebaseDatabase mDatabase;
    private GoogleSignInClient mGoogleSignInClient = null;
    private GoogleSignInAccount mGoogleSignInAccount = null;
    private UserModel mUser = null;
    private List<ExpeditionModel> mExpeditions = null;

    private FirebaseSingleton() {
        mDatabase = FirebaseDatabase.getInstance();
        mDatabase.setPersistenceEnabled(true);
    }

    static FirebaseSingleton getInstance() {
        return ourInstance;
    }

    public void getUser(String id, Consumer<UserModel> user) {

        if (mUser != null) {
            user.accept(mUser);
            return;
        }
        DatabaseReference userRef = mDatabase.getReference(TEMP_REF + "user").child(id);
        userRef.keepSynced(true);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mUser = dataSnapshot.getValue(UserModel.class);
                    user.accept(mUser);
                } else if (mGoogleSignInAccount != null
                        && id.equals(mGoogleSignInAccount.getId())) {
                    // create current user into database
                    String photoUrl = mGoogleSignInAccount.getPhotoUrl() == null ? "" :
                            mGoogleSignInAccount.getPhotoUrl().toString();
                    mUser = new UserModel(mGoogleSignInAccount.getId(),
                            mGoogleSignInAccount.getDisplayName(),
                            photoUrl);
                    userRef.setValue(mUser)
                            .addOnSuccessListener(aVoid -> user.accept(mUser))
                            .addOnCanceledListener(() -> user.accept(null))
                            .addOnFailureListener(e -> {
                                // TODO log error
                                user.accept(null);
                            });
                } else {
                    user.accept(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // TODO log error
                user.accept(null);
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
        mGoogleSignInClient = GoogleSignIn.getClient(context.getApplicationContext(), gso);
        return mGoogleSignInClient;
    }

    public GoogleSignInAccount getGoogleSignInAccount(Context context) {
        if (mGoogleSignInAccount != null) {
            return mGoogleSignInAccount;
        }
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        mGoogleSignInAccount = GoogleSignIn.getLastSignedInAccount(context.getApplicationContext());
        return mGoogleSignInAccount;
    }

    public void setGoogleSignInAccount(GoogleSignInAccount googleSignInAccount) {
        mGoogleSignInAccount = googleSignInAccount;
    }

    public void disconnect(Activity activity) {
        GoogleSignInClient googleSignInClient = getGoogleSignInClient(activity);
        googleSignInClient.signOut()
                .addOnCompleteListener(activity,
                        task -> clearHistory(activity));
    }

    public void clearHistory(Activity activity) {
        mGoogleSignInClient = null;
        mGoogleSignInAccount = null;
        mUser = null;
        mExpeditions = null;
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
    }

    public void createExpedition(String title, String description, Consumer<Boolean> success) {

        if (mUser == null) {
            success.accept(false);
            return;
        }
        DatabaseReference userExpeditionRef = mDatabase.getReference(TEMP_REF + "user")
                .child(mUser.getKey()).child("expedition");
        userExpeditionRef.keepSynced(true);
        String key = userExpeditionRef.push().getKey();
        if (key == null) {
            success.accept(false);
            return;
        }
        ExpeditionModel expedition = new ExpeditionModel(key, title, description);
        userExpeditionRef.child(key).setValue(expedition)
                .addOnSuccessListener(aVoid -> {
                    expedition.addUser(mUser);
                    DatabaseReference expeditionRef
                            = mDatabase.getReference(TEMP_REF + "expedition").child(key);
                    expeditionRef.setValue(expedition)
                            .addOnSuccessListener(aVoid1 -> success.accept(true))
                            .addOnCanceledListener(() -> success.accept(false))
                            .addOnFailureListener(e -> {
                                // TODO log error
                                success.accept(false);
                            });
                })
                .addOnCanceledListener(() -> success.accept(false))
                .addOnFailureListener(e -> {
                    // TODO log error
                    success.accept(false);
                });
    }

    public void getExpeditions(Consumer<List<ExpeditionModel>> result) {

        if (mUser == null) {
            result.accept(null);
            return;
        }
        if (mExpeditions == null) {
            mExpeditions = new ArrayList<>();
            DatabaseReference expeditionsRef = mDatabase.getReference(TEMP_REF + "expedition");
            expeditionsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mExpeditions.clear();
                    for (DataSnapshot expeditionSnapshot : dataSnapshot.getChildren()) {
                        ExpeditionModel expedition = expeditionSnapshot.getValue(ExpeditionModel.class);
                        // do not show users own expeditions
                        if (expedition != null && !mUser.getKey().equals(expedition.getUserKey())) {
                            mExpeditions.add(expedition);
                        }
                    }
                    Collections.reverse(mExpeditions);
                    result.accept(mExpeditions);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    result.accept(null);
                }
            });
        } else {
            result.accept(mExpeditions);
        }
    }
}
