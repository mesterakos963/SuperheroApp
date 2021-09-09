package app.superhero.src.dto;

import java.io.Serializable;

public class SuperheroDto implements Serializable {
    int id;
    String name;

    String url;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() { return url; }
}
