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
}
