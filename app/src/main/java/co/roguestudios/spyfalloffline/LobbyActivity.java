package co.roguestudios.spyfalloffline;

import android.content.Intent;
import android.preference.PreferenceFragment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class LobbyActivity extends AppCompatActivity {

    private ArrayList<String> players;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        prefManager = new PrefManager(this);

        players = new ArrayList<>();
        if (prefManager.getAddRecentPlayers()) {
            for (int i = 0; i < 8; i++) {
                String name = prefManager.getRecentPlayer(i);
                if (!name.equals("")) {
                    players.add(name);
                }
            }
        }
        if (players.size() < 8) {
            players.add("");
        }

        LobbyAdapter playerAdapter = new LobbyAdapter(this, 0, players);
        ListView playerList = findViewById(R.id.playerList);
        playerList.setAdapter(playerAdapter);
    }

    public void clickStart(View view) {

        if (players.size() > 4) {
            if (players.get(players.size() - 1) == "") {
                players.remove(players.size() - 1);
            }

            for (int i = 0; i < 8; i++) {
                if (i < players.size()) {
                    prefManager.setRecentPlayer(players.get(i), i);
                } else {
                    prefManager.setRecentPlayer("", i);
                }
            }

            Match match = new Match(this, players, "spyfallpack");
            Intent intent = new Intent(this, GameActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("match", match);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.players_required, Toast.LENGTH_LONG).show();
        }

    }


}
