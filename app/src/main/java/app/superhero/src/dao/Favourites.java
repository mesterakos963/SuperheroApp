package app.superhero.src.dao;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Favourites {
    @PrimaryKey
    int superHeroID;
}
