package app.superhero.src.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SuperheroMasterDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSuperheros(List<SuperheroMasterData> superheroMasterData);

    @Query("SELECT * FROM SuperheroMasterData WHERE name LIKE :name")
    List<SuperheroMasterData> getSuperheroesByName(String name);

    @Delete
    void delete(SuperheroMasterData superheroMasterData);

}
