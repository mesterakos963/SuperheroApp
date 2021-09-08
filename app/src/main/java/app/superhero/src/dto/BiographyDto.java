package app.superhero.src.dto;

import com.squareup.moshi.Json;

import java.io.Serializable;
import java.util.List;

import app.superhero.src.utils.ZeroWhenNull;

public class BiographyDto implements Serializable {

    String response;

    @ZeroWhenNull
    int id;

    String name;

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
