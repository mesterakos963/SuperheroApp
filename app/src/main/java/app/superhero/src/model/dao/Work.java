package app.superhero.src.model.dao;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Work {
    @PrimaryKey
    public int workId;

    String occupation;
    String base;

    public Work(int workId, String occupation, String base) {
        this.workId = workId;
        this.occupation = occupation;
        this.base = base;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getBase() {
        return base;
    }
}
