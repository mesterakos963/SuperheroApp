package app.superhero.src.api;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import app.superhero.SuperheroApplication;
import app.superhero.src.dao.SuperheroMasterDataDao;
import app.superhero.src.interfaces.SuperheroesService;
import app.superhero.src.model.response.SuperheroesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static app.superhero.src.database.DatabaseModule.provideDatabase;

@EBean(scope = EBean.Scope.Singleton)
public class SuperheroesRepository {

    @Bean
    ApiClient apiClient;

    SuperheroMasterDataDao superheroDao;

    SuperheroApplication application;
    SuperheroesService superheroesService;


    @AfterInject
    public void init() {
        superheroDao = provideDatabase(application).superheroMasterDataDao();
    }

    public void searchByName(String name, Callback<SuperheroesResponse> callback) {
        if (superheroesService == null) {
            superheroesService = apiClient.getClient().create(SuperheroesService.class);
        }
        Call<SuperheroesResponse> call = superheroesService.listSuperheroes(name);
        call.enqueue(new Callback<SuperheroesResponse>() {
            @Override
            public void onResponse(Call<SuperheroesResponse> call, Response<SuperheroesResponse> response) {
                if (response.body() != null) {
                    //superheroDao.insertSuperheros(response.body().getResults());
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

    public void getPowerstatsById(int id, Callback<SuperheroesResponse> callback) {
        if (superheroesService == null) {
            superheroesService = apiClient.getClient().create(SuperheroesService.class);
        }
        Call<SuperheroesResponse> call = superheroesService.listPowerstats(id);
        call.enqueue(new Callback<SuperheroesResponse>() {
            @Override
            public void onResponse(Call<SuperheroesResponse> call, Response<SuperheroesResponse> response) {
                if (response.body() != null) {
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

    public void getBiographyById(int id, Callback<SuperheroesResponse> callback) {
        if (superheroesService == null) {
            superheroesService = apiClient.getClient().create(SuperheroesService.class);
        }
        Call<SuperheroesResponse> call = superheroesService.listBiography(id);
        call.enqueue(new Callback<SuperheroesResponse>() {
            @Override
            public void onResponse(Call<SuperheroesResponse> call, Response<SuperheroesResponse> response) {
                if (response.body() != null) {
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

    public void getAppearanceById(int id, Callback<SuperheroesResponse> callback) {
        if (superheroesService == null) {
            superheroesService = apiClient.getClient().create(SuperheroesService.class);
        }
        Call<SuperheroesResponse> call = superheroesService.listAppearance(id);
        call.enqueue(new Callback<SuperheroesResponse>() {
            @Override
            public void onResponse(Call<SuperheroesResponse> call, Response<SuperheroesResponse> response) {
                if (response.body() != null) {
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

    public void getWorkById(int id, Callback<SuperheroesResponse> callback) {
        if (superheroesService == null) {
            superheroesService = apiClient.getClient().create(SuperheroesService.class);
        }
        Call<SuperheroesResponse> call = superheroesService.listWork(id);
        call.enqueue(new Callback<SuperheroesResponse>() {
            @Override
            public void onResponse(Call<SuperheroesResponse> call, Response<SuperheroesResponse> response) {
                if (response.body() != null) {
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

    public void getConnections(int id, Callback<SuperheroesResponse> callback) {
        if (superheroesService == null) {
            superheroesService = apiClient.getClient().create(SuperheroesService.class);
        }
        Call<SuperheroesResponse> call = superheroesService.listConnections(id);
        call.enqueue(new Callback<SuperheroesResponse>() {
            @Override
            public void onResponse(Call<SuperheroesResponse> call, Response<SuperheroesResponse> response) {
                if (response.body() != null) {
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
