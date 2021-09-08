package app.superhero.src.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Work {
    @PrimaryKey
    int workId;

    @ColumnInfo
    String occupation;

    @ColumnInfo
    String base;

}
