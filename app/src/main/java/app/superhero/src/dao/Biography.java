package app.superhero.src.dao;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Biography {
    @PrimaryKey
    int biographyId;
    String fullName;
    String alterEgos;
    List<String> aliases;
    String placeOfBirth;
    String firstAppearance;
    String publisher;
    String alignment;
}

