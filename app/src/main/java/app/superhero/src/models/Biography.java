package app.superhero.src.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Biography implements Serializable {

    @SerializedName("full-name")
    String fullName;

    @SerializedName("alter-egos")
    String alterEgos;

    List<String> aliases;

    @SerializedName("place-of-birth")
    String placeOfBirth;

    @SerializedName("first-appearance")
    String firstAppearance;

    String publisher;
    String alignment;

}
