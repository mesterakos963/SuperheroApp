package app.superhero.src.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import app.superhero.src.entities.Connections;

@Dao
public interface ConnectionsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertConnections(List<Connections> connections);

    @Delete
    void delete(Connections connections);

    @Query("SELECT * FROM connections")
    List<Connections> getAll();
}
