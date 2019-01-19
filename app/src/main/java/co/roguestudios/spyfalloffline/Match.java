package co.roguestudios.spyfalloffline;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

public class Match implements Parcelable {

    private final Gson gson = new Gson();
    private Context context;
    private ArrayList<Player> players;
    private Location location;
    private ArrayList<String> potentialLocations;

    public Match(Context context, ArrayList<String> players, String pack) {
        this.context = context.getApplicationContext();
        this.players = new ArrayList<>();

        // Get locations from json
        ArrayList<Location> locations = new ArrayList<>();
        try {
            InputStream inputStream = this.context.getAssets().open("packs/" + pack + ".json");
            InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
            Type listType = new TypeToken<ArrayList<Location>>() {}.getType();
            locations = gson.fromJson(isr, listType);

        }
        catch (IOException error) {
            System.out.println(error);
        }

        // Pick random location from pack
        Random random = new Random();
        int locationChosen = random.nextInt(locations.size());
        this.location = locations.get(locationChosen);

        // Fill arraylist of potential locations
        potentialLocations = new ArrayList<>();
        for(int i = 0; i < locations.size(); i++) {
            this.potentialLocations.add(locations.get(i).getName());
        }

        // Create and fill arraylist of players
        ArrayList<Integer> usedRoles = new ArrayList<>();
        for(int i = 0; i < players.size(); i++) {

            boolean created = false;
            int role;

            while(created == false) {
                role = random.nextInt(8);
                boolean used = false;
                int j = 0;

                while(used == false && j < usedRoles.size()) {
                    if(usedRoles.get(j) == role) {
                        used = true;
                    }
                    j += 1;
                }

                if(used == false) {
                    this.players.add(new Player(i, players.get(i), role));
                    usedRoles.add(role);
                    created = true;
                }
            }
        }

        // Check if no one is spy and change roles if necessary
        boolean spy = false;
        int k = 0;

        while(spy == false && k < this.players.size()) {
            if(this.players.get(k).getRole() == 7) {
                spy = true;
            }
            k += 1;
        }
        if(spy == false) {
            this.players.get(random.nextInt(this.players.size())).setRole(7);
        }

    }

    private Match() {
        players = new ArrayList<>();
        potentialLocations = new ArrayList<>();
    }

    public Match(Parcel in) {
        this();
        in.readTypedList(getPlayers(), Player.CREATOR);
        setLocation((Location) in.readParcelable(Location.class.getClassLoader()));
        in.readStringList(getPotentialLocations());
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    public ArrayList<String> getPotentialLocations() {
        return potentialLocations;
    }
    public void setPotentialLocations(ArrayList<String> potentialLocations) {
        this.potentialLocations = potentialLocations;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeTypedList(getPlayers());
        out.writeParcelable(getLocation(), flags);
        out.writeStringList(getPotentialLocations());
    }

    public static final Parcelable.Creator<Match> CREATOR
            = new Parcelable.Creator<Match>() {
        public Match createFromParcel(Parcel in) {
            return new Match(in);
        }

        public Match[] newArray(int size) {
            return new Match[size];
        }
    };

}
