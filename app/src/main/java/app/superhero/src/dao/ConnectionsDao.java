package app.superhero.src.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ConnectionsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertConnection(Connections connections);

    @Delete
    void delete(Connections connections);

    @Query("SELECT * FROM Connections WHERE connectionsId = :id")
    Connections getConnections(int id);
}
