package app.superhero.src.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import app.superhero.src.entities.Superhero;
import app.superhero.src.entities.SuperheroMasterData;

@Dao
public interface SuperheroDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSuperheros(List<SuperheroMasterData> superheroMasterData);

    @Delete
    void delete(SuperheroMasterData superheroMasterData);

    @Query("SELECT * FROM SuperheroMasterData")
    public List<Superhero> getSuperhero();

    @Query("SELECT * FROM SuperheroMasterData WHERE superheroId = :id")
    Superhero findBySuperheroId(int id);
}
