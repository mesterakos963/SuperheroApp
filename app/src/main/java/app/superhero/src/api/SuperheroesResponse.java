package app.superhero.src.api;

import java.util.List;

import app.superhero.src.models.Superhero;

public class SuperheroesResponse {
    private List<Superhero> results;
    public List<Superhero> getResults() { return this.results; }
}
