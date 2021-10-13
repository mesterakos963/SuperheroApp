package app.superhero.src.database;

import android.app.Application;

import androidx.room.Room;

import org.androidannotations.annotations.EBean;

@EBean(scope = EBean.Scope.Singleton)
public class DatabaseModule {
    public static SuperheroDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application, SuperheroDatabase.class, "superheroDatabase")
                .fallbackToDestructiveMigration()
                .build();
    }
}
