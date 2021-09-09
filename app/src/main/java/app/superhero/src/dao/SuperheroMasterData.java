package app.superhero.src.dao;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SuperheroMasterData {
    @PrimaryKey
    public int superheroId;

    public String name;

    public String imageUrl;

    public SuperheroMasterData(int superheroId, String name, String imageUrl) {
        this.superheroId = superheroId;
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
