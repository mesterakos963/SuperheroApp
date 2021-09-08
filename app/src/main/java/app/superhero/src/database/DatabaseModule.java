package app.superhero.src.database;

import android.app.Application;

import androidx.room.Room;

import org.androidannotations.annotations.EBean;

import app.superhero.src.utils.StringArrayConverter;

@EBean(scope = EBean.Scope.Singleton)
public class DatabaseModule {

    public StringArrayConverter converter;

    public static SuperheroDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application, SuperheroDatabase.class, "superheroDatabase")
                .addTypeConverter(new StringArrayConverter())
                .fallbackToDestructiveMigration().build();
    }
}
