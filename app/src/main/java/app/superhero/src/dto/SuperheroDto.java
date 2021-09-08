package app.superhero.src.dto;

import java.io.Serializable;

public class SuperheroDto implements Serializable {
    int id;
    String name;
    ImageDto imageDto;

    public String getName() {
        return name;
    }

    public ImageDto getImageDto() {
        return imageDto;
    }
}
