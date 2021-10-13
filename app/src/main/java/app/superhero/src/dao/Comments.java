package app.superhero.src.dao;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Comments {
    @PrimaryKey
    int commentsId;

    String comment;

    public Comments(int commentsId, String comment) {
        this.commentsId = commentsId;
        this.comment = comment;
    }

    public int getCommentsId() {
        return commentsId;
    }

    public String getComment() {
        return comment;
    }
}
