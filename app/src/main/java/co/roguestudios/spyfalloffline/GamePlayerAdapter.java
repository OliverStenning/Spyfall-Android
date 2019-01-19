package co.roguestudios.spyfalloffline;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GamePlayerAdapter extends ArrayAdapter {

    private Activity context;
    private ArrayList<Player> players;

    public GamePlayerAdapter(Activity context, int string, ArrayList<Player> players) {
        super(context, string, players);
        this.context = context;
        this.players = players;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        Player currentPlayer = players.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        view = inflater.inflate(R.layout.string_cell, null);
        TextView stringText = view.findViewById(R.id.stringText);
        stringText.setTextSize(22);
        stringText.setText(currentPlayer.getName());

        return view;
    }

}
