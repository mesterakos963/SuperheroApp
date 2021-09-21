package app.superhero.src.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public interface SuperheroDao {
    @Transaction
    @Query("SELECT * FROM SuperheroMasterData WHERE superheroId = :id")
    Superhero getSuperhero(int id);
}
