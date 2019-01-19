package co.roguestudios.spyfalloffline;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GameLocationAdapter extends ArrayAdapter {

    private Activity context;
    private ArrayList<String> locations;

    public GameLocationAdapter(Activity context, int resource, ArrayList<String> locations) {
        super(context, resource, locations);
        this.context = context;
        this.locations = locations;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        String currentString = locations.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        view = inflater.inflate(R.layout.string_cell, null);
        TextView stringText = (TextView) view.findViewById(R.id.stringText);
        stringText.setText(currentString);

        return view;
    }
}
