package app.superhero.src.dao;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Connections {
    @PrimaryKey
    int connectionsId;
    String groupAffiliation;
    String relatives;

    public Connections(int connectionsId, String groupAffiliation, String relatives) {
        this.connectionsId = connectionsId;
        this.groupAffiliation = groupAffiliation;
        this.relatives = relatives;
    }

    public String getGroupAffiliation() {
        return groupAffiliation;
    }

    public String getRelatives() {
        return relatives;
    }
}
