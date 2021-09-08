package app.superhero.src.dao;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Appearance {
    @PrimaryKey
    int appearanceId;

    @ColumnInfo
    String gender;

    @ColumnInfo
    String race;

    @ColumnInfo
    List<String> height;

    @ColumnInfo
    List<String> weight;

    @ColumnInfo
    String eyeColor;

    @ColumnInfo
    String hairColor;
}
