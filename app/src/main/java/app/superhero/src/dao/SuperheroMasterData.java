package app.superhero.src.dao;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class SuperheroMasterData implements Serializable {
    @PrimaryKey
    int superheroId;

    String name;

    String url;

    boolean isFavourite;

    public SuperheroMasterData(int superheroId, String name, String url, boolean isFavourite) {
        this.superheroId = superheroId;
        this.name = name;
        this.url = url;
        this.isFavourite = isFavourite;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getId() {
        return superheroId;
    }

    public boolean getIsFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }


}
