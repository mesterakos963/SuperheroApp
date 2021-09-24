package app.superhero.src.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface PowerstatsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPowerstats(Powerstats powerstats);

    @Delete
    void delete(Powerstats powerstats);

    @Query("SELECT * FROM Powerstats WHERE powerstatsId = :id")
    Powerstats getPowerstats(int id);
}
