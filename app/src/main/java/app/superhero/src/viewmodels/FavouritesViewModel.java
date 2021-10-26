package app.superhero.src.viewmodels;

import androidx.lifecycle.MutableLiveData;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import app.superhero.src.api.SuperheroesRepository;
import app.superhero.src.interfaces.ListCallback;
import app.superhero.src.model.dao.SuperheroMasterData;

@EBean(scope = EBean.Scope.Fragment)
public class FavouritesViewModel extends SuperheroParentViewModel {
    @Bean
    SuperheroesRepository repository;

    public void fetchSuperheroes() {
        getRepository().getFavourites(new ListCallback<SuperheroMasterData>() {
            @Override
            public void onSuccess(List<SuperheroMasterData> results) {
                _superheroes.postValue(results);
            }

            @Override
            public void onError(List<SuperheroMasterData> fallbackResult, Throwable t) {
                _superheroes.postValue(fallbackResult);
                error.postValue(t);
            }
        });
    }

    @Override
    protected SuperheroesRepository getRepository() {
        return repository;
    }

    @Override
    public MutableLiveData<List<SuperheroMasterData>> getSuperheroes() {
        return _superheroes;
    }

    public void removeSuperHeroFromList(SuperheroMasterData superhero) {
        List<SuperheroMasterData> newList = new ArrayList<>(_superheroes.getValue());
        newList.remove(superhero);
        _superheroes.postValue(newList);
    }
}
