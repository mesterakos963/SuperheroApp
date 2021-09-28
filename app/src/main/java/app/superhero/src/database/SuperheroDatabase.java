package app.superhero.src.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import app.superhero.src.dao.Appearance;
import app.superhero.src.dao.AppearanceDao;
import app.superhero.src.dao.Biography;
import app.superhero.src.dao.BiographyDao;
import app.superhero.src.dao.Comments;
import app.superhero.src.dao.CommentsDao;
import app.superhero.src.dao.Connections;
import app.superhero.src.dao.ConnectionsDao;
import app.superhero.src.dao.Powerstats;
import app.superhero.src.dao.PowerstatsDao;
import app.superhero.src.dao.SuperheroDao;
import app.superhero.src.dao.SuperheroMasterData;
import app.superhero.src.dao.SuperheroMasterDataDao;
import app.superhero.src.dao.Work;
import app.superhero.src.dao.WorkDao;
import app.superhero.src.utils.StringArrayConverter;

@Database(entities = {SuperheroMasterData.class, Appearance.class,
        Biography.class, Connections.class, Powerstats.class,
        Work.class, Comments.class}, version = 6, exportSchema = false)
@TypeConverters({StringArrayConverter.class})
public abstract class SuperheroDatabase extends RoomDatabase {
    public abstract SuperheroMasterDataDao superheroMasterDataDao();

    public abstract AppearanceDao appearanceDao();

    public abstract BiographyDao biographyDao();

    public abstract ConnectionsDao connectionsDao();

    public abstract PowerstatsDao powerstatsDao();

    public abstract WorkDao workDao();

    public abstract SuperheroDao superheroDao();

    public abstract CommentsDao commentDao();
}
