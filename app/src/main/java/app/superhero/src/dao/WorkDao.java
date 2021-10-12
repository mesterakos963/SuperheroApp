package app.superhero.src.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface WorkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWork(Work works);

    @Delete
    void delete(Work work);

    @Query("SELECT * FROM Work WHERE workId = :id")
    Work getWork(int id);
}
