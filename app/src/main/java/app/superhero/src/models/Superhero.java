package app.superhero.src.models;

import java.io.Serializable;

public class Superhero implements Serializable {
    int id;
    String name;
    Powerstats powerstats;
    Biography biography;
    Appearance appearance;
    Work work;
    Connections connections;
    Image image;

    public String getName(){
        return name;
    }

    public Image getImage() { return image; }
}
