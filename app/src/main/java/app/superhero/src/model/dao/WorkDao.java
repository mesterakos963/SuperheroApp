package app.superhero.src.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface WorkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWork(Work works);

    @Delete
    void delete(Work work);
}
