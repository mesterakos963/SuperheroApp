package app.superhero.src.api;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.jdeferred.Deferred;
import org.jdeferred.FailCallback;
import org.jdeferred.Promise;
import org.jdeferred.impl.DefaultDeferredManager;
import org.jdeferred.impl.DeferredObject;

import java.util.ArrayList;
import java.util.List;

import app.superhero.SuperheroApplication;
import app.superhero.src.dao.Connections;
import app.superhero.src.dao.ConnectionsDao;
import app.superhero.src.dao.SuperheroMasterData;
import app.superhero.src.dao.SuperheroMasterDataDao;
import app.superhero.src.dto.ConnectionsDto;
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
    ConnectionsDao connectionsDao;

    @App
    SuperheroApplication application;

    @Bean
    ApiClient apiClient;

    @AfterInject
    public void init() {
        superheroMasterDataDao = provideDatabase(application).superheroMasterDataDao();
        connectionsDao = provideDatabase(application).connectionsDao();
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

    protected List<SuperheroMasterData> getSuperHeroesFromDbByName(String name) {
        return superheroMasterDataDao.getSuperheroesByName("%" + name + "%");
    }

    public Promise<SuperheroesResponse, Throwable, Object> getPowerstatsById(int id, Callback<SuperheroesResponse> callback) {
        Deferred<SuperheroesResponse, Throwable, Object> deferred = new DeferredObject<>();
        executeGetPowerstatsById(id, callback, deferred);
        return deferred.promise();
    }

    public Promise<SuperheroesResponse, Throwable, Object> getBiographyById(int id, Callback<SuperheroesResponse> callback) {
        Deferred<SuperheroesResponse, Throwable, Object> deferred = new DeferredObject<>();
        executeGetBiographyById(id, callback, deferred);
        return deferred.promise();
    }

    public Promise<SuperheroesResponse, Throwable, Object> getAppearanceById(int id, Callback<SuperheroesResponse> callback) {
        Deferred<SuperheroesResponse, Throwable, Object> deferred = new DeferredObject<>();
        executeGetAppearanceById(id, callback, deferred);
        return deferred.promise();

    }

    public Promise<SuperheroesResponse, Throwable, Object> getWorkById(int id, Callback<SuperheroesResponse> callback) {
        Deferred<SuperheroesResponse, Throwable, Object> deferred = new DeferredObject<>();
        executeGetWorkById(id, callback, deferred);
        return deferred.promise();
    }

    public Promise<ConnectionsDto, Throwable, Object> getConnectionsById(int id, Callback<ConnectionsDto> callback) {
        Deferred<ConnectionsDto, Throwable, Object> deferred = new DeferredObject<>();
        executeGetConnectionsById(id, callback, deferred);
        return deferred.promise();
    }

    @Background
    protected void executeGetConnectionsById(int id, Callback<ConnectionsDto> callback, Deferred<ConnectionsDto, Throwable, Object> deferred) {
        Call<ConnectionsDto> call = getSuperheroService().listConnections(id);
        call.enqueue(new Callback<ConnectionsDto>() {
            @Override
            public void onResponse(Call<ConnectionsDto> call, Response<ConnectionsDto> response) {
                if (response.body() != null) {
                    cacheConnections(call, response, deferred);
                } else {
                    deferred.reject(new Throwable());
                }
            }

            @Override
            public void onFailure(Call<ConnectionsDto> call, Throwable t) {
                call.cancel();
                deferred.fail((FailCallback<Throwable>) t);
            }
        });
    }

    @Background
    protected void cacheConnections(Call<ConnectionsDto> call, Response<ConnectionsDto> response, Deferred<ConnectionsDto, Throwable, Object> deferred) {
        Connections connection = Stream.of(response.body())
                .map(connectionDto ->
                        new Connections(connectionDto.getId(), connectionDto.getGroupAffiliation(), connectionDto.getRelatives())
                ).single();
        connectionsDao.insertConnection(connection);
        deferred.resolve(response.body());
    }

    private void executeGetPowerstatsById(int id, Callback<SuperheroesResponse> callback, Deferred<SuperheroesResponse, Throwable, Object> deferred) {
        Call<SuperheroesResponse> call = getSuperheroService().listPowerstats(id);
        call.enqueue(new Callback<SuperheroesResponse>() {
            @Override
            public void onResponse(Call<SuperheroesResponse> call, Response<SuperheroesResponse> response) {
                if (response.body() != null) {
                    deferred.resolve(response.body());

                }
            }

            @Override
            public void onFailure(Call<SuperheroesResponse> call, Throwable t) {
                call.cancel();
                deferred.fail((FailCallback<Throwable>) t);
            }
        });
    }

    private void executeGetBiographyById(int id, Callback<SuperheroesResponse> callback, Deferred<SuperheroesResponse, Throwable, Object> deferred) {
        Call<SuperheroesResponse> call = getSuperheroService().listBiography(id);
        call.enqueue(new Callback<SuperheroesResponse>() {
            @Override
            public void onResponse(Call<SuperheroesResponse> call, Response<SuperheroesResponse> response) {
                if (response.body() != null) {
                    deferred.resolve(response.body());
                }
            }

            @Override
            public void onFailure(Call<SuperheroesResponse> call, Throwable t) {
                call.cancel();
                deferred.fail((FailCallback<Throwable>) t);
            }
        });
    }

    private void executeGetAppearanceById(int id, Callback<SuperheroesResponse> callback, Deferred<SuperheroesResponse, Throwable, Object> deferred) {
        Call<SuperheroesResponse> call = getSuperheroService().listAppearance(id);
        call.enqueue(new Callback<SuperheroesResponse>() {
            @Override
            public void onResponse(Call<SuperheroesResponse> call, Response<SuperheroesResponse> response) {
                if (response.body() != null) {
                    deferred.resolve(response.body());
                }
            }

            @Override
            public void onFailure(Call<SuperheroesResponse> call, Throwable t) {
                call.cancel();
                deferred.fail((FailCallback<Throwable>) t);
            }
        });
    }

    private void executeGetWorkById(int id, Callback<SuperheroesResponse> callback, Deferred<SuperheroesResponse, Throwable, Object> deferred) {
        Call<SuperheroesResponse> call = getSuperheroService().listWork(id);
        call.enqueue(new Callback<SuperheroesResponse>() {
            @Override
            public void onResponse(Call<SuperheroesResponse> call, Response<SuperheroesResponse> response) {
                if (response.body() != null) {
                    deferred.resolve(response.body());
                }
            }

            @Override
            public void onFailure(Call<SuperheroesResponse> call, Throwable t) {
                call.cancel();
                deferred.fail((FailCallback<Throwable>) t);
            }
        });
    }

    @Background
    public void getCharacteristics(int id, Callback callback) {
        DefaultDeferredManager defaultDeferredManager = new DefaultDeferredManager();
        defaultDeferredManager.when(
                getBiographyById(id, callback),
                getAppearanceById(id, callback),
                getWorkById(id, callback),
                getConnectionsById(id, callback)
        ).done(result -> {
            if (result != null && result.size() > 0) {
                result.get(0);
            }
        });
/*
        defaultDeferredManager.when(getAppearanceById(id, callback), getAppearanceById(id, callback)).done(result -> {
            if (result != null && result.size() > 0) {
                result.get(0);
            }
        });

        defaultDeferredManager.when(getWorkById(id, callback), getWorkById(id, callback)).done(result -> {
            if (result != null && result.size() > 0) {
                result.get(0);
            }
        });

        defaultDeferredManager.when(getConnectionsById(id, callback), getConnectionsById(id, callback)).done(result -> {
            if (result != null && result.size() > 0) {
                result.get(0);
            }
        });*/
    }
}
