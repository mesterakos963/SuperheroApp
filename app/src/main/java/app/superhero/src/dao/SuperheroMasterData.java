package app.superhero.src.dao;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SuperheroMasterData {
    @PrimaryKey
    public int superheroId;

    public String name;

    public String url;

    public SuperheroMasterData(int superheroId, String name, String url) {
        this.superheroId = superheroId;
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
