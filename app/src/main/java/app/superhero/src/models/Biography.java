package app.superhero.src.models;

import com.squareup.moshi.Json;

import java.io.Serializable;
import java.util.List;

public class Biography implements Serializable {

    @Json(name = "full-name")
    String fullName;

    @Json(name = "alter-egos")
    String alterEgos;

    List<String> aliases;

    @Json(name = "place-of-birth")
    String placeOfBirth;

    @Json(name = "first-appearance")
    String firstAppearance;

    String publisher;
    String alignment;

}
