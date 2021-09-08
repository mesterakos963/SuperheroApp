package app.superhero.src.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import app.superhero.src.dao.SuperheroDao;
import app.superhero.src.utils.Converters;

@Database(entities = {}, version = 1)
@TypeConverters({Converters.class})
public abstract class SuperheroDatabase extends RoomDatabase {
    public abstract SuperheroDao superheroDao();
}
