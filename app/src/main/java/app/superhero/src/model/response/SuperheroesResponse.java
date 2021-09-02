package app.superhero.src.model.response;

import java.util.List;

import app.superhero.src.models.Superhero;

public class SuperheroesResponse extends BaseResponse {
    private List<Superhero> results;
    public List<Superhero> getResults() { return this.results; }
}
