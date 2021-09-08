package app.superhero.src.model.response;

import java.util.List;

import app.superhero.src.dto.SuperheroDto;

public class SuperheroesResponse extends BaseResponse {
    private List<SuperheroDto> results;

    public List<SuperheroDto> getResults() {
        return this.results;
    }
}
