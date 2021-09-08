package app.superhero.src.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import app.superhero.src.entities.Biography;

@Dao
public interface BiographyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBiographies(List<Biography> biographies);

    @Delete
    void delete(Biography biography);

    @Query("SELECT * FROM biography")
    List<Biography> getAll();
}
