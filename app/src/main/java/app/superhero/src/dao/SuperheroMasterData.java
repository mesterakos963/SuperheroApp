package app.superhero.src.dao;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SuperheroMasterData {
    @PrimaryKey
    public int superheroId;

    @ColumnInfo
    public String name;
}
