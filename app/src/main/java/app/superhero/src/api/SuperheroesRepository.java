package app.superhero.src.api;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import app.superhero.SuperheroApplication;
import app.superhero.src.dao.SuperheroMasterData;
import app.superhero.src.dao.SuperheroMasterDataDao;
import app.superhero.src.interfaces.CustomCallback;
import app.superhero.src.interfaces.SuperheroesService;
import app.superhero.src.model.response.SuperheroesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static app.superhero.src.database.DatabaseModule.provideDatabase;

@EBean(scope = EBean.Scope.Singleton)
public class SuperheroesRepository {

    SuperheroMasterDataDao superheroMasterDataDao;

    @App
    SuperheroApplication application;

    @Bean
    ApiClient apiClient;

    @AfterInject
    public void init() {
        superheroMasterDataDao = provideDatabase(application).superheroMasterDataDao();
    }

    public SuperheroesService getSuperheroService() {
        return apiClient.getSuperheroesService();
    }

    public void searchByName(String name, CustomCallback<SuperheroMasterData> customCallback) {
        Call<SuperheroesResponse> call = getSuperheroService().listSuperheroes(name);
        call.enqueue(new Callback<SuperheroesResponse>() {
            @Override
            public void onResponse(Call<SuperheroesResponse> call, Response<SuperheroesResponse> response) {
                if (response.body() != null && response.body().isValid()) {
                    cacheSuperHeroes(response, customCallback, name);
                } else {
                    customCallback.onError(new ArrayList<>(), new Throwable(response.body().getError()));
                }
            }

            @Override
            public void onFailure(Call<SuperheroesResponse> call, Throwable t) {
                passCachedSuperHeroesFromDbWithError(name, t, customCallback);
            }
        });
    }

    @Background
    protected void passCachedSuperHeroesFromDbWithError(String name, Throwable t, CustomCallback<SuperheroMasterData> customCallback) {
        List<SuperheroMasterData> superheroMasterDataList = getSuperHeroesFromDbByName(name);
        customCallback.onError(superheroMasterDataList, t);
    }

    @Background
    protected void cacheSuperHeroes(Response<SuperheroesResponse> response, CustomCallback<SuperheroMasterData> customCallback, String name) {
        List<SuperheroMasterData> superheroMasterDataList = Stream.of(response.body().getResults())
                .map(superhero ->
                        new SuperheroMasterData(superhero.getId(), superhero.getName(), superhero.getImageDto().getUrl())
                ).collect(Collectors.toList());
        superheroMasterDataDao.insertSuperheros(superheroMasterDataList);
        customCallback.onSuccess(getSuperHeroesFromDbByName(name));
    }

    protected List<SuperheroMasterData> getSuperHeroesFromDbByName(String name){
        return superheroMasterDataDao.getSuperheroesByName("%" + name + "%");
    }

    public void getPowerstatsById(int id, Callback<SuperheroesResponse> callback) {
        Call<SuperheroesResponse> call = getSuperheroService().listPowerstats(id);
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
        Call<SuperheroesResponse> call = getSuperheroService().listBiography(id);
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
        Call<SuperheroesResponse> call = getSuperheroService().listAppearance(id);
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
        Call<SuperheroesResponse> call = getSuperheroService().listWork(id);
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
        Call<SuperheroesResponse> call = getSuperheroService().listConnections(id);
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
