package app.superhero.src.dao;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Connections {
    @PrimaryKey
    int connectionsId;

    String groupAffiliation;
    String relatives;
}
