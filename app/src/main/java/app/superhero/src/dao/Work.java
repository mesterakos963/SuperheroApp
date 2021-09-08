package app.superhero.src.dao;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Work {
    @PrimaryKey
    public int workId;

    String occupation;

    String base;
}
