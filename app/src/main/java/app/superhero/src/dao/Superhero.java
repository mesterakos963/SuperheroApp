package app.superhero.src.dao;

import androidx.room.Embedded;
import androidx.room.Relation;

public class Superhero {
    @Embedded
    public SuperheroMasterData superheroMasterData;

    @Relation(
            parentColumn = "superheroId",
            entityColumn = "appearanceId"
    )
    public Appearance appearance;

    @Relation(
            parentColumn = "superheroId",
            entityColumn = "biographyId"
    )
    public Biography biography;

    @Relation(
            parentColumn = "superheroId",
            entityColumn = "connectionsId"
    )
    public Connections connections;

    @Relation(
            parentColumn = "superheroId",
            entityColumn = "powerstatsId"
    )
    public Powerstats powerstats;

    @Relation(
            parentColumn = "superheroId",
            entityColumn = "workId"
    )
    public Work work;

    @Relation(
            parentColumn = "superheroId",
            entityColumn = "comment"
    )
    public Comments comment;
}
