package coms309.chesssuitev1;

/**
 * Created by Abdelrahman on 10/8/2017.
 *
 * This class serves as a session manager for a single session
 */


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

public class SessionManager {

    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "ChessGame";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * set the user as logged in
     * @param isLoggedIn whether the user is already logged in
     */
    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    /**
     * check if the user is already logged in
     * @return true or false
     */
    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    /**
     * get default shared preferences
     * @return true or false
     */
    public SharedPreferences getDefaultPref (){
        return PreferenceManager.getDefaultSharedPreferences(_context);
    }


}
