package app.superhero.src.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface AppearanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAppearance(Appearance appearances);

    @Delete
    void delete(Appearance appearance);
}
