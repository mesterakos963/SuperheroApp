package app.superhero.src.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Powerstats {
    @PrimaryKey
    int powerstatsId;

    @ColumnInfo
    int intelligence;

    @ColumnInfo
    int strength;

    @ColumnInfo
    int speed;

    @ColumnInfo
    int durability;

    @ColumnInfo
    int power;

    @ColumnInfo
    int combat;
}
