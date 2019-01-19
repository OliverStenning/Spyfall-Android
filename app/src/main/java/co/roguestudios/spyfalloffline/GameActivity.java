package co.roguestudios.spyfalloffline;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class GameActivity extends AppCompatActivity {

    CoordinatorLayout baseLayout;
    TextView timerText;
    GridView playerGrid;
    GridView locationGrid;
    GamePlayerAdapter playerAdapter;
    GameLocationAdapter locationAdapter;
    Button quitButton;
    Button startButton;
    AdView gameBanner;


    private Match match;
    private PrefManager prefManager;
    private boolean playing;
    private CountDownTimer countDownTimer;
    private long totalTime;
    private Ringtone ringtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Link user interface components
        baseLayout = findViewById(R.id.baseLayout);
        timerText = findViewById(R.id.timerText);
        playerGrid = findViewById(R.id.gamePlayerGrid);
        locationGrid = findViewById(R.id.gameLocationGrid);
        quitButton = findViewById(R.id.quitButton);
        startButton = findViewById(R.id.startButton);
        gameBanner = findViewById(R.id.gameBanner);

        //Load bottom banner
        gameBanner.loadAd(new AdRequest.Builder().addTestDevice("04E641F3C4A58533F53CF34CBBA149F5").build());

        //Get players array from lobby activity
        Bundle bundle = getIntent().getExtras();
        match = bundle.getParcelable("match");

        //Get total time of game from settings
        prefManager = new PrefManager(this);
        totalTime = prefManager.getGameLength() * 60000;
        timerText.setText(formatTime(totalTime));

        //Creating adaptors for and setting them
        playerAdapter = new GamePlayerAdapter(GameActivity.this, 0, match.getPlayers());
        locationAdapter = new GameLocationAdapter(GameActivity.this, 0, match.getPotentialLocations());
        playerGrid.setAdapter(playerAdapter);
        locationGrid.setAdapter(locationAdapter);

        playerGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                createPlayerDialog(position);
            }
        });

        //Dynamically changes height of players box depending on amount of players
        ViewGroup.LayoutParams playerGridLayoutParams = playerGrid.getLayoutParams();
        double amount = match.getPlayers().size();
        int rows = (int) Math.ceil(amount / 2);
        playerGridLayoutParams.height =  (playerGridLayoutParams.height / 4) * rows;
        playerGrid.setLayoutParams(playerGridLayoutParams);



        createSnackbar("Tap a player name to see role and location.", 2);
    }

    private void createSnackbar(String message, int duration) {
        duration = duration * 1000;
        Snackbar snackbar = Snackbar.make(baseLayout, message, duration);
        snackbar.show();
    }

    private void createPlayerDialog(int id) {

        //Create a dialog and the view for it
        LayoutInflater inflater = LayoutInflater.from(this);
        View playerDialogView = inflater.inflate(R.layout.player_info_dialog, null);
        final AlertDialog playerDialog = new AlertDialog.Builder(this).create();
        playerDialog.setView(playerDialogView);

        //Get components from view
        TextView locationText = playerDialogView.findViewById(R.id.dialogLocationText);
        TextView roleText = playerDialogView.findViewById(R.id.dialogRoleText);
        TextView helpText = playerDialogView.findViewById(R.id.helpText);
        Button closeButton = playerDialogView.findViewById(R.id.dialogCloseButton);

        //Adapt view depending on player id
        int role = match.getPlayers().get(id).getRole();

        if(role == 7) {
            locationText.setText(getString(R.string.spy_location_text));
            roleText.setText(getString(R.string.spy_role_text));
            helpText.setText(getString(R.string.spy_help));
        }
        else {
            locationText.setText(match.getLocation().getName());
            roleText.setText(match.getLocation().getRole(role));
            helpText.setText(getString(R.string.role_help));
        }

        playerDialog.show();

        //Set button listener
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerDialog.dismiss();
            }
        });

    }

    public String formatTime(long time) {
        String minutes = String.format("%02d", time / 60000);
        String seconds = String.format("%02d", (time % 60000) / 1000);
        return (minutes + ":" + seconds);
    }

    public void playPause(View view) {

        if(!playing) {

            // Update whether game is playing
            playing = true;

            // Update button text
            startButton.setText(getString(R.string.pause_button));

            // Create and start timer
            countDownTimer = new CountDownTimer(totalTime, 200) {
                @Override
                public void onTick(long millisUntilFinished) {
                    totalTime = millisUntilFinished;
                    timerText.setText(formatTime(millisUntilFinished));
                }

                @Override
                public void onFinish() {
                    if (prefManager.getTimerAlertNoise()) {
                        try {
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
                            ringtone.play();
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ringtone.stop();
                                }
                            }, 5000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            countDownTimer.start();

        }
        else {

            // Update whether game is playing
            playing = false;

            // Update button text
            startButton.setText(getString(R.string.continue_button));

            // Stop the timer
            countDownTimer.cancel();
        }

    }

    public void quitGame(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
