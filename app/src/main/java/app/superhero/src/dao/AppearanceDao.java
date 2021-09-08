package app.superhero.src.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import app.superhero.src.entities.Appearance;

@Dao
public interface AppearanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAppearances(List<Appearance> appearances);

    @Delete
    void delete(Appearance appearance);

    @Query("SELECT * FROM appearance")
    List<Appearance> getAll();
}
