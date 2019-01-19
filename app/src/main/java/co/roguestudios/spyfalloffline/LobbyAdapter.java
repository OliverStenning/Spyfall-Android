package co.roguestudios.spyfalloffline;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LobbyAdapter extends ArrayAdapter {

    private Activity context;
    private ArrayList<String> players;

    public LobbyAdapter(Activity context, int resource, ArrayList<String> players) {
        super(context, resource, players);
        this.context = context;
        this.players = players;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;
        final ViewHolder holder;

        if(view == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.player_cell, null);

            holder = new ViewHolder();
            holder.playerName = view.findViewById(R.id.nameText);
            holder.nameInput = view.findViewById(R.id.addPlayerText);
            holder.actionButton = view.findViewById(R.id.actionButton);

            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        final String currentPlayer = players.get(position);

        if(currentPlayer == "") {

            // Switch which text is visible
            holder.playerName.setVisibility(View.INVISIBLE);
            holder.nameInput.setVisibility(View.VISIBLE);

            // Change icon image
            holder.actionButton.setImageResource(R.drawable.ic_add_24dp);

            // Change icon color
            holder.actionButton.setColorFilter(ContextCompat.getColor(context, R.color.colorTint));

            // Clear input box
            holder.nameInput.setText("");

            // Add button action
            holder.actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = holder.nameInput.getText().toString();

                    name = name.trim();
                    if(validateName(name)) {
                        addPlayer(name);
                        notifyDataSetChanged();
                    }
                    else {
                        Toast.makeText(context, R.string.invalid_name, Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else {

            // Switch which text is visible
            holder.playerName.setVisibility(View.VISIBLE);
            holder.nameInput.setVisibility(View.INVISIBLE);

            // Change icon image
            holder.actionButton.setImageResource(R.drawable.ic_kick_24dp);

            // Change icon color
            holder.actionButton.setColorFilter(ContextCompat.getColor(context, R.color.colorKick));

            // Display player name
            holder.playerName.setText(currentPlayer);

            // Kick button action
            holder.actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    players.remove(position);
                    notifyDataSetChanged();
                }
            });
        }
        return view;
    }

    public void addPlayer(String name) {
        players.remove(players.size() - 1);
        players.add(name);

        if(players.size() < 8) {
            players.add("");
        }
    }

    public boolean validateName(String name) {

        name = name.toLowerCase();
        boolean valid = true;
        int i = 0;
        while(i < players.size() && valid == true) {
            if(players.get(i).toLowerCase().equals(name)) {
                valid = false;
            }
            i += 1;
        }
        return valid;
    }

    static class ViewHolder {

        TextView playerName;
        EditText nameInput;
        ImageButton actionButton;

    }

}