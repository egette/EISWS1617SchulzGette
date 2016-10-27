package  de.schulzgette.thes_o_naise.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Authentifizierungsklasse, speichert Token in den SharedPreferences
 */
public class Authentication extends Application {

    String PREFS_NAME = "UserData";
    String DEFAULT = "";
    Context context;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static String token;
//    public static String token = "token=934d4686647982d4fee1b77e891bd0e3";

    public Authentication() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        token = sharedPreferences.getString("Cookie", DEFAULT);
    }

    // speichert Token in den Shared Preferences
    public static void setToken(String token) {
        Log.d("Cookie", token);
        editor.putString("Cookie", token);
        editor.apply();
    }

}
