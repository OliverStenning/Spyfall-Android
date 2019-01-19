package co.roguestudios.spyfalloffline;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    // Shared preferences name
    private static final String PREF_NAME = "user-preferences";

    // Shared preferences keys
    private static final String FIRST_TIME_LAUNCH = "first-time-launch";
    private static final String GAME_LENGTH = "game-length";
    private static final String ADD_RECENT_PLAYERS = "add-recent-players";
    private static final String TIMER_ALERT_NOISE = "timer-alert-noise";
    private static final String RECENT_PLAYER = "recent-player-";

    public PrefManager(Context context) {
        this.context = context;
        preferences = this.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void clearPreferences() {
        editor.clear();
        editor.commit();
    }

    public void setFirstTimeLaunch(boolean isFirstTimeLaunch) {
        editor.putBoolean(FIRST_TIME_LAUNCH, isFirstTimeLaunch);
        editor.commit();
    }
    public boolean isFirstTimeLaunch() {
        return preferences.getBoolean(FIRST_TIME_LAUNCH, true);
    }

    public void setGameLength(int gameLength) {
        editor.putInt(GAME_LENGTH, gameLength);
        editor.commit();
    }
    public int getGameLength() {
        return preferences.getInt(GAME_LENGTH, 8);
    }

    public void setAddRecentPlayers(boolean addRecentPlayers) {
        editor.putBoolean(ADD_RECENT_PLAYERS, addRecentPlayers);
        editor.commit();
    }
    public boolean getAddRecentPlayers() {
        return preferences.getBoolean(ADD_RECENT_PLAYERS, true);
    }

    public void setTimerAlertNoise(boolean timerAlertNoise) {
        editor.putBoolean(TIMER_ALERT_NOISE, timerAlertNoise);
        editor.commit();
    }
    public boolean getTimerAlertNoise() {
        return preferences.getBoolean(TIMER_ALERT_NOISE, true);
    }

    public void setRecentPlayer(String name, int pos) {
        editor.putString(RECENT_PLAYER + pos, name);
        editor.commit();
    }

    public String getRecentPlayer(int pos) {
        return preferences.getString(RECENT_PLAYER + pos, "");
    }

}
