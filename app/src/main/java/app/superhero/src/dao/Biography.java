package app.superhero.src.dao;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Biography {
    @PrimaryKey
    int biographyId;

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

