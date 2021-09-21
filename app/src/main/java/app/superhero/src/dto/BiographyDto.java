package app.superhero.src.dto;

import com.squareup.moshi.Json;

import java.io.Serializable;
import java.util.List;

import app.superhero.src.model.response.BaseResponse;
import app.superhero.src.model.response.ResponseResult;
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

    /*public BiographyDto(String response, int id, String name, String fullName, String alterEgos,
                        List<String> aliases, String placeOfBirth, String firstAppearance,
                        String publisher, String alignment) {
        this.response = response;
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.alterEgos = alterEgos;
        this.aliases = aliases;
        this.placeOfBirth = placeOfBirth;
        this.firstAppearance = firstAppearance;
        this.publisher = publisher;
        this.alignment = alignment;
    }*/

    public ResponseResult getResponse() {
        return response;
    }

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
