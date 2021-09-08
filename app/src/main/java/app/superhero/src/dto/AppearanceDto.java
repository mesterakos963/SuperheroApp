package app.superhero.src.dto;

import com.squareup.moshi.Json;

import java.io.Serializable;
import java.util.List;

import app.superhero.src.utils.ZeroWhenNull;

public class AppearanceDto implements Serializable {
    String response;

    @ZeroWhenNull
    int id;

    String name;
    String gender;
    String race;
    List<String> height;
    List<String> weight;
    @Json(name = "eye-color")
    String eyeColor;

    @Json(name = "hair-color")
    String hairColor;
}
