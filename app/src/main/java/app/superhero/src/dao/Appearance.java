package app.superhero.src.dao;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Appearance {
    @PrimaryKey
    int appearanceId;

    String gender;
    String race;
    List<String> height;
    List<String> weight;
    String eyeColor;
    String hairColor;

    public Appearance(int appearanceId, String gender, String race, List<String> height, List<String> weight, String eyeColor, String hairColor) {
        this.appearanceId = appearanceId;
        this.gender = gender;
        this.race = race;
        this.height = height;
        this.weight = weight;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
    }

    public int getAppearanceId() {
        return appearanceId;
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
