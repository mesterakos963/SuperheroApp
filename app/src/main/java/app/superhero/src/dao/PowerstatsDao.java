package app.superhero.src.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PowerstatsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPowerstats(List<Powerstats> powerstats);

    @Delete
    void delete(Powerstats powerstats);

    @Query("SELECT * FROM powerstats")
    List<Powerstats> getAll();
}
