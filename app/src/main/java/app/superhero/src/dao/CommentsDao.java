package app.superhero.src.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface CommentsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertComments(Comments comment);

    @Delete
    void delete(Comments comment);
}
