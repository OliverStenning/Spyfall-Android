package co.roguestudios.spyfalloffline;

import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Parcelable {

    private int id; //number from 0-7
    private String name;
    private int role; //number from 0-7 (7 = spy)

    public Player(int id, String name, int role) {
        setId(id);
        setName(name);
        setRole(role);
    }

    public Player(Parcel in) {
        setId(in.readInt());
        setName(in.readString());
        setRole(in.readInt());
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getRole() {
        return role;
    }
    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel in, int flags) {
        in.writeInt(getId());
        in.writeString(getName());
        in.writeInt(getRole());
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Player createFromParcel(Parcel source) {
            return new Player(source);
        }

        public Player[] newArray(int size) {
            return new Player[size];
        }
    };


}
