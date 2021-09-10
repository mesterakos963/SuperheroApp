package app.superhero.src.dao;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Powerstats {
    @PrimaryKey
    int powerstatsId;

    int intelligence;
    int strength;
    int speed;
    int durability;
    int power;
    int combat;
}
