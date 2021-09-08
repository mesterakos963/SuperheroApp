package app.superhero.src.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Connections {
    @PrimaryKey
    int connectionsId;

    @ColumnInfo
    String groupAffiliation;

    @ColumnInfo
    String relatives;
}
