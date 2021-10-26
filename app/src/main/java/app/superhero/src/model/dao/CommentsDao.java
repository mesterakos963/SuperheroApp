package app.superhero.src.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface CommentsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertComments(Comments comment);

    @Delete
    void delete(Comments comment);

    @Query("SELECT * FROM Comments WHERE commentsId = :id")
    Comments getComment(int id);
}
