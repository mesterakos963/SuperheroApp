package app.superhero.src.dao;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SuperheroMasterData implements Parcelable {
    @PrimaryKey
    int superheroId;

    String name;

    String url;

    boolean isFavourite;

    public SuperheroMasterData(int superheroId, String name, String url) {
        this.superheroId = superheroId;
        this.name = name;
        this.url = url;
    }

    protected SuperheroMasterData(Parcel in) {
        superheroId = in.readInt();
        name = in.readString();
        url = in.readString();
        isFavourite = in.readByte() != 0;
    }

    public static final Creator<SuperheroMasterData> CREATOR = new Creator<SuperheroMasterData>() {
        @Override
        public SuperheroMasterData createFromParcel(Parcel in) {
            return new SuperheroMasterData(in);
        }

        @Override
        public SuperheroMasterData[] newArray(int size) {
            return new SuperheroMasterData[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getId() {
        return superheroId;
    }

    public boolean getIsFavourite() { return isFavourite; }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(superheroId);
        parcel.writeString(name);
        parcel.writeString(url);
        parcel.writeByte((byte) (isFavourite ? 1 : 0));
    }
}
