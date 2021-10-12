package app.superhero.src.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface AppearanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAppearance(Appearance appearances);

    @Delete
    void delete(Appearance appearance);

    @Query("SELECT * FROM Appearance WHERE appearanceId = :id")
    Appearance getAppearance(int id);
}
