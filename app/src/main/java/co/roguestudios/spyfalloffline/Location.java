package co.roguestudios.spyfalloffline;

import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Parcelable {

    private String name;
    private String[] roles;

    public Location(String name, String[] roles) {
        setName(name);
        setRoles(roles);
    }

    private Location() {
        roles = new String[7];
    }

    public Location(Parcel in) {
        this();
        setName(in.readString());
        in.readStringArray(roles);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getRole(int pos) {
        return roles[pos];
    }
    public String[] getRoles() {
        return roles;
    }
    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel in, int flags) {
        in.writeString(getName());
        in.writeStringArray(getRoles());
    }

    public static final Creator CREATOR = new Creator() {
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

}