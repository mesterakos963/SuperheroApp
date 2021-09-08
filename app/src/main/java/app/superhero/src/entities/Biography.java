package app.superhero.src.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Biography {
    @PrimaryKey
    int biographyID;

    @ColumnInfo
    String fullName;

    @ColumnInfo
    String alterEgos;

    @ColumnInfo
    List<String> aliases;

    @ColumnInfo
    String placeOfBirth;

    @ColumnInfo
    String firstAppearance;

    @ColumnInfo
    String publisher;

    @ColumnInfo
    String alignment;
}

