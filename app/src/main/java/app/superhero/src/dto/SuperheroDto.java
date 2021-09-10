package app.superhero.src.dto;

import com.squareup.moshi.Json;

import java.io.Serializable;

public class SuperheroDto implements Serializable {
    int id;
    String name;

    @Json(name = "image")
    ImageDto imageDto;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ImageDto getImageDto() {
        return imageDto;
    }
}
