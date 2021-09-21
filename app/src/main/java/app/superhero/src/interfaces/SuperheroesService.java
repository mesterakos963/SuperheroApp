package app.superhero.src.interfaces;

import app.superhero.src.dto.AppearanceDto;
import app.superhero.src.dto.BiographyDto;
import app.superhero.src.dto.ConnectionsDto;
import app.superhero.src.dto.WorkDto;
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
    Call<BiographyDto> listBiography(@Path("id") int id);

    @GET("{id}/appearance")
    Call<AppearanceDto> listAppearance(@Path("id") int id);

    @GET("{id}/work")
    Call<WorkDto> listWork(@Path("id") int id);

    @GET("{id}/connections")
    Call<ConnectionsDto> listConnections(@Path("id") int id);
}
