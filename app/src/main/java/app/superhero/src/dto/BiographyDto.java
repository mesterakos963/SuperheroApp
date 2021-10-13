package app.superhero.src.dto;

import com.squareup.moshi.Json;

import java.io.Serializable;
import java.util.List;

import app.superhero.src.model.response.BaseResponse;
import app.superhero.src.utils.ZeroWhenNull;

public class BiographyDto extends BaseResponse implements Serializable {
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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAlterEgos() {
        return alterEgos;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public String getFirstAppearance() {
        return firstAppearance;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getAlignment() {
        return alignment;
    }
}
