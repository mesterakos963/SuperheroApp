package app.superhero.src.dto;

import java.io.Serializable;

import app.superhero.src.utils.ZeroWhenNull;

public class WorkDto implements Serializable {
    @ZeroWhenNull
    int id;

    String name;
    String occupation;
    String base;

    public WorkDto(int id, String name, String occupation, String base) {
        this.id = id;
        this.name = name;
        this.occupation = occupation;
        this.base = base;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getBase() {
        return base;
    }
}
