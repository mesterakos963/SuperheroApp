package app.superhero.src.interfaces;

import app.superhero.src.dto.ConnectionsDto;
import app.superhero.src.model.response.SuperheroesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SuperheroesService {

    @GET("search/{name}")
    Call<SuperheroesResponse> listSuperheroes(@Path("name") String name);

    @GET("{id}/powerstats")
    Call<SuperheroesResponse> listPowerstats(@Path("id") int id);

    @GET("{id}/biography")
    Call<SuperheroesResponse> listBiography(@Path("id") int id);

    @GET("{id}/appearance")
    Call<SuperheroesResponse> listAppearance(@Path("id") int id);

    @GET("{id}/work")
    Call<SuperheroesResponse> listWork(@Path("id") int id);

    @GET("{id}/connections")
    Call<ConnectionsDto> listConnections(@Path("id") int id);
}
