package app.superhero.src.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface BiographyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBiography(Biography biography);

    @Delete
    void delete(Biography biography);

    @Query("SELECT * FROM Biography WHERE biographyId = :id")
    Biography getBiography(int id);
}
