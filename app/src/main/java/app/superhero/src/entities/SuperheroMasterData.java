package app.superhero.src.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SuperheroMasterData {
    @PrimaryKey
    int superheroId;

    @ColumnInfo
    String name;
}
