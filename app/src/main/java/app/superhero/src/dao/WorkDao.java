package app.superhero.src.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WorkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWorks(List<Work> works);

    @Delete
    void delete(Work work);

    @Query("SELECT * FROM work")
    List<Work> getAll();
}