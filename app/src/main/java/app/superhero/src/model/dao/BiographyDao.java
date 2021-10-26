package app.superhero.src.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface BiographyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBiography(Biography biography);

    @Delete
    void delete(Biography biography);
}
