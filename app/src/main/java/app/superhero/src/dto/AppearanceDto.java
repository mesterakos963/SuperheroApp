package app.superhero.src.dto;

import com.squareup.moshi.Json;

import java.io.Serializable;
import java.util.List;

import app.superhero.src.utils.ZeroWhenNull;

public class AppearanceDto implements Serializable {
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

    public AppearanceDto(int id, String name, String gender, String race, List<String> height, List<String> weight, String eyeColor, String hairColor) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.race = race;
        this.height = height;
        this.weight = weight;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getRace() {
        return race;
    }

    public List<String> getHeight() {
        return height;
    }

    public List<String> getWeight() {
        return weight;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public String getHairColor() {
        return hairColor;
    }
}
