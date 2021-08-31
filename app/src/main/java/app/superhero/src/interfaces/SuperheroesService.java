package app.superhero.src.interfaces;

import app.superhero.src.api.SuperheroesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SuperheroesService {

    @GET("search/{name}")
    Call<SuperheroesResponse> listSuperheroes(@Path("name") String name);
}
