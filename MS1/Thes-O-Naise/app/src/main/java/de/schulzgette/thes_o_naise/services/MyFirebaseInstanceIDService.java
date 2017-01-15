package de.schulzgette.thes_o_naise.services;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Enrico and Jessica on 10.12.2016.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        SharedPreferences sharedPreferences = getSharedPreferences("einstellungen", MODE_PRIVATE);
        String firebase = sharedPreferences.getString("firebase", "");

        if(firebase.equals("true")){
            Intent intent = new Intent(getApplicationContext(), RegistrationIntentService.class);
            intent.putExtra("register", "true");
            getApplicationContext().startService(intent);
        }
    }
    // [END refresh_token]

}
