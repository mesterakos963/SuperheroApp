package app.superhero.src.api;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DefaultDeferredManager;
import org.jdeferred.impl.DeferredObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import app.superhero.SuperheroApplication;
import app.superhero.src.dao.Appearance;
import app.superhero.src.dao.AppearanceDao;
import app.superhero.src.dao.Biography;
import app.superhero.src.dao.BiographyDao;
import app.superhero.src.dao.Comments;
import app.superhero.src.dao.CommentsDao;
import app.superhero.src.dao.Connections;
import app.superhero.src.dao.ConnectionsDao;
import app.superhero.src.dao.Powerstats;
import app.superhero.src.dao.PowerstatsDao;
import app.superhero.src.dao.Superhero;
import app.superhero.src.dao.SuperheroDao;
import app.superhero.src.dao.SuperheroMasterData;
import app.superhero.src.dao.SuperheroMasterDataDao;
import app.superhero.src.dao.Work;
import app.superhero.src.dao.WorkDao;
import app.superhero.src.dto.AppearanceDto;
import app.superhero.src.dto.BiographyDto;
import app.superhero.src.dto.ConnectionsDto;
import app.superhero.src.dto.PowerstatsDto;
import app.superhero.src.dto.WorkDto;
import app.superhero.src.interfaces.ItemCallback;
import app.superhero.src.interfaces.ListCallback;
import app.superhero.src.interfaces.SuperheroesService;
import app.superhero.src.model.response.SuperheroesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static app.superhero.src.database.DatabaseModule.provideDatabase;

@EBean(scope = EBean.Scope.Singleton)
public class SuperheroesRepository {

    //todo ha sikertelen a kérés, akkor a db-ből cacheljen

    SuperheroMasterDataDao superheroMasterDataDao;
    ConnectionsDao connectionsDao;
    BiographyDao biographyDao;
    AppearanceDao appearanceDao;
    WorkDao workDao;
    SuperheroDao superheroDao;
    CommentsDao commentDao;
    PowerstatsDao powerstatsDao;

    @App
    SuperheroApplication application;

    @Bean
    ApiClient apiClient;

    @AfterInject
    public void init() {
        superheroMasterDataDao = provideDatabase(application).superheroMasterDataDao();
        connectionsDao = provideDatabase(application).connectionsDao();
        biographyDao = provideDatabase(application).biographyDao();
        appearanceDao = provideDatabase(application).appearanceDao();
        workDao = provideDatabase(application).workDao();
        superheroDao = provideDatabase(application).superheroDao();
        commentDao = provideDatabase(application).commentDao();
        powerstatsDao = provideDatabase(application).powerstatsDao();
    }

    public SuperheroesService getSuperheroService() {
        return apiClient.getSuperheroesService();
    }

    public void searchByName(String name, ListCallback<SuperheroMasterData> listCallback) {
        Call<SuperheroesResponse> call = getSuperheroService().listSuperheroes(name);
        call.enqueue(new Callback<SuperheroesResponse>() {
            @Override
            public void onResponse(Call<SuperheroesResponse> call, Response<SuperheroesResponse> response) {
                if (response.body() != null && response.body().isValid()) {
                    cacheSuperHeroes(response, listCallback, name);
                } else {
                    listCallback.onError(new ArrayList<>(), new Throwable(response.body().getError()));
                }
            }

            @Override
            public void onFailure(Call<SuperheroesResponse> call, Throwable t) {
                passCachedSuperHeroesFromDbWithError(name, t, listCallback);
            }
        });
    }

    @Background
    protected void passCachedSuperHeroesFromDbWithError(String name, Throwable t, ListCallback<SuperheroMasterData> listCallback) {
        List<SuperheroMasterData> superheroMasterDataList = getSuperHeroesFromDbByName(name);
        listCallback.onError(superheroMasterDataList, t);
    }

    @Background
    protected void cacheSuperHeroes(Response<SuperheroesResponse> response, ListCallback<SuperheroMasterData> listCallback, String name) {
        List<SuperheroMasterData> cachedList = getSuperHeroesFromDbByName(name);
        Map<Integer, Boolean> map = Stream.of(cachedList).collect(Collectors.toMap(SuperheroMasterData::getId, SuperheroMasterData::getIsFavourite));
        List<SuperheroMasterData> superheroMasterDataList = Stream.of(response.body().getResults())
                .map(superhero ->
                        new SuperheroMasterData(superhero.getId(), superhero.getName(), superhero.getImageDto().getUrl(), getIsFavouriteById(map, superhero.getId()))
                ).collect(Collectors.toList());
        superheroMasterDataDao.insertSuperheros(superheroMasterDataList);
        listCallback.onSuccess(getSuperHeroesFromDbByName(name));
    }

    private boolean getIsFavouriteById(Map<Integer, Boolean> map, int id) {
        if (map.get(id) != null) {
            return map.get(id);
        }
        return false;
    }

    protected List<SuperheroMasterData> getSuperHeroesFromDbByName(String name) {
        return superheroMasterDataDao.getSuperheroesByName("%" + name + "%");
    }

    public Promise<PowerstatsDto, Throwable, Object> getPowerstatsById(int id) {
        Deferred<PowerstatsDto, Throwable, Object> deferred = new DeferredObject<>();
        executeGetPowerstatsById(id, deferred);
        return deferred.promise();
    }

    public Promise<BiographyDto, Throwable, Object> getBiographyById(int id) {
        Deferred<BiographyDto, Throwable, Object> deferred = new DeferredObject<>();
        executeGetBiographyById(id, deferred);
        return deferred.promise();
    }

    public Promise<AppearanceDto, Throwable, Object> getAppearanceById(int id) {
        Deferred<AppearanceDto, Throwable, Object> deferred = new DeferredObject<>();
        executeGetAppearanceById(id, deferred);
        return deferred.promise();

    }

    public Promise<WorkDto, Throwable, Object> getWorkById(int id) {
        Deferred<WorkDto, Throwable, Object> deferred = new DeferredObject<>();
        executeGetWorkById(id, deferred);
        return deferred.promise();
    }

    public Promise<ConnectionsDto, Throwable, Object> getConnectionsById(int id) {
        Deferred<ConnectionsDto, Throwable, Object> deferred = new DeferredObject<>();
        executeGetConnectionsById(id, deferred);
        return deferred.promise();
    }

    @Background
    protected void executeGetConnectionsById(int id, Deferred<ConnectionsDto, Throwable, Object> deferred) {
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
                deferred.reject(t);
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

    private void executeGetBiographyById(int id, Deferred<BiographyDto, Throwable, Object> deferred) {
        Call<BiographyDto> call = getSuperheroService().listBiography(id);
        call.enqueue(new Callback<BiographyDto>() {
            @Override
            public void onResponse(Call<BiographyDto> call, Response<BiographyDto> response) {
                if (response.body() != null) {
                    cacheBiography(response, deferred);
                } else {
                    deferred.reject(new Throwable());
                }
            }

            @Override
            public void onFailure(Call<BiographyDto> call, Throwable t) {
                call.cancel();
                deferred.reject(new Throwable());
            }
        });
    }

    @Background
    protected void cacheBiography(Response<BiographyDto> response, Deferred<BiographyDto, Throwable, Object> deferred) {
        Biography biography = Stream.of(response.body())
                .map(biographyDto ->
                        new Biography(biographyDto.getId(), biographyDto.getFullName(),
                                biographyDto.getAlterEgos(), biographyDto.getAliases(),
                                biographyDto.getPlaceOfBirth(), biographyDto.getFirstAppearance(),
                                biographyDto.getPublisher(), biographyDto.getAlignment())).single();
        biographyDao.insertBiography(biography);
        deferred.resolve(response.body());
    }

    private void executeGetAppearanceById(int id, Deferred<AppearanceDto, Throwable, Object> deferred) {
        Call<AppearanceDto> call = getSuperheroService().listAppearance(id);
        call.enqueue(new Callback<AppearanceDto>() {
            @Override
            public void onResponse(Call<AppearanceDto> call, Response<AppearanceDto> response) {
                if (response.body() != null) {
                    cacheAppearance(response, deferred);
                } else {
                    deferred.reject(new Throwable());
                }
            }

            @Override
            public void onFailure(Call<AppearanceDto> call, Throwable t) {
                call.cancel();
                deferred.reject(new Throwable());
            }
        });
    }

    @Background
    protected void cacheAppearance(Response<AppearanceDto> response, Deferred<AppearanceDto, Throwable, Object> deferred) {
        Appearance appearance = Stream.of(response.body())
                .map(appearanceDto ->
                        new Appearance(appearanceDto.getId(), appearanceDto.getGender(),
                                appearanceDto.getRace(), appearanceDto.getHeight(),
                                appearanceDto.getWeight(), appearanceDto.getEyeColor(),
                                appearanceDto.getHairColor())
                ).single();
        appearanceDao.insertAppearance(appearance);
        deferred.resolve(response.body());
    }

    private void executeGetWorkById(int id, Deferred<WorkDto, Throwable, Object> deferred) {
        Call<WorkDto> call = getSuperheroService().listWork(id);
        call.enqueue(new Callback<WorkDto>() {
            @Override
            public void onResponse(Call<WorkDto> call, Response<WorkDto> response) {
                if (response.body() != null) {
                    cacheWork(response, deferred);
                } else {
                    deferred.reject(new Throwable());
                }
            }

            @Override
            public void onFailure(Call<WorkDto> call, Throwable t) {
                call.cancel();
                deferred.reject(new Throwable());
            }
        });
    }

    @Background
    protected void cacheWork(Response<WorkDto> response, Deferred<WorkDto, Throwable, Object> deferred) {
        Work work = Stream.of(response.body())
                .map(workDto ->
                        new Work(workDto.getId(), workDto.getOccupation(), workDto.getBase())
                ).single();
        workDao.insertWork(work);
        deferred.resolve(response.body());
    }

    @Background
    public void getCharacteristics(int id, ItemCallback<Superhero> itemCallback) {
        DefaultDeferredManager defaultDeferredManager = new DefaultDeferredManager();
        defaultDeferredManager.when(
                getBiographyById(id),
                getAppearanceById(id),
                getWorkById(id),
                getConnectionsById(id)
        ).done(result -> {
            if (result != null && result.size() >= 4) {
                Superhero superhero = superheroDao.getSuperhero(id);
                itemCallback.onSuccess(superhero);
            }
        });
    }

    @Background
    public void getPowerstats(int id, ItemCallback<Powerstats> itemCallback) {
        DefaultDeferredManager defaultDeferredManager = new DefaultDeferredManager();
        defaultDeferredManager.when(
                getPowerstatsById(id)
        ).done(result -> {
            if (result != null) {
                Powerstats powerstats = powerstatsDao.getPowerstats(id);
                itemCallback.onSuccess(powerstats);
            }
        });
    }

    @Background
    public void getCommentFromDbById(int id, ItemCallback<Comments> itemCallback) {
        itemCallback.onSuccess(commentDao.getComment(id));
    }

    @Background
    public void cacheComments(int id, String text) {
        Comments comment = new Comments(id, text);
        commentDao.insertComments(comment);
    }

    @Background
    protected void cachePowerstats(Response<PowerstatsDto> response, Deferred<PowerstatsDto, Throwable, Object> deferred) {
        Powerstats powerstats = Stream.of(response.body())
                .map(powerstatsDto ->
                        new Powerstats(powerstatsDto.getId(), powerstatsDto.getIntelligence(),
                                powerstatsDto.getStrength(), powerstatsDto.getSpeed(),
                                powerstatsDto.getDurability(), powerstatsDto.getPower(),
                                powerstatsDto.getCombat())
                ).single();
        powerstatsDao.insertPowerstats(powerstats);
        deferred.resolve(response.body());
    }

    private void executeGetPowerstatsById(int id, Deferred<PowerstatsDto, Throwable, Object> deferred) {
        Call<PowerstatsDto> call = getSuperheroService().listPowerstats(id);
        call.enqueue(new Callback<PowerstatsDto>() {
            @Override
            public void onResponse(Call<PowerstatsDto> call, Response<PowerstatsDto> response) {
                if (response.body() != null) {
                    cachePowerstats(response, deferred);
                } else {
                    deferred.reject(new Throwable());
                }
            }

            @Override
            public void onFailure(Call<PowerstatsDto> call, Throwable t) {
                call.cancel();
                deferred.reject(new Throwable());
            }
        });
    }

    @Background
    public void cacheFavourite(SuperheroMasterData data) {
        superheroMasterDataDao.update(data);
    }

    @Background
    public void getFavourites(ListCallback<SuperheroMasterData> listCallback) {
        listCallback.onSuccess(superheroMasterDataDao.getFavourites());
    }
}
