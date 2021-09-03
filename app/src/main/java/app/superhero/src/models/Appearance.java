package app.superhero.src.models;

import com.squareup.moshi.Json;

import java.io.Serializable;
import java.util.List;

public class Appearance implements Serializable {
    String gender;
    String race;
    List<String> height;
    List<String> weight;
    @Json(name = "eye-color")
    String eyeColor;

    @Json(name = "hair-color")
    String hairColor;
}
