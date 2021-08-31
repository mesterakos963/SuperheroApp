package app.superhero.src.api;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import app.superhero.src.interfaces.SuperheroesService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EBean(scope = EBean.Scope.Singleton)
public class SuperheroesRepository {

    @Bean
    ApiClient apiClient;

    SuperheroesService superheroesService;

    public void searchByName(String name, Callback<SuperheroesResponse> callback) {
        if (superheroesService == null) {
            superheroesService = apiClient.getClient().create(SuperheroesService.class);
        }
        Call<SuperheroesResponse> call = superheroesService.listSuperheroes(name);
        call.enqueue(new Callback<SuperheroesResponse>() {
            @Override
            public void onResponse(Call<SuperheroesResponse> call, Response<SuperheroesResponse> response) {
                if(response.body() != null){
                    callback.onResponse(call, response);
                }
            }

            @Override
            public void onFailure(Call<SuperheroesResponse> call, Throwable t) {
                call.cancel();
                callback.onFailure(call, t);
            }
        });
    }
}
