package app.superhero.src.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Appearance implements Serializable {
    String gender;
    String race;
    List<String> height;
    List<String> weight;
    @SerializedName("eye-color")
    String eyeColor;

    @SerializedName("hair-color")
    String hairColor;
}
