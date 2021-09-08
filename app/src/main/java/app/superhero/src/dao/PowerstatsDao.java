package app.superhero.src.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import app.superhero.src.entities.Powerstats;

public interface PowerstatsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPowerstats(List<Powerstats> powerstats);

    @Delete
    void delete(Powerstats powerstats);

    @Query("SELECT * FROM powerstats")
    List<Powerstats> getAll();
}
