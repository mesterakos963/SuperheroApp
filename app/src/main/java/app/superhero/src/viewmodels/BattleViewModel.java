package app.superhero.src.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

import app.superhero.src.api.SuperheroesRepository;
import app.superhero.src.dao.SuperheroMasterData;
import app.superhero.src.interfaces.ListCallback;

@EBean(scope = EBean.Scope.Fragment)
public class BattleViewModel extends SuperheroParentViewModel {
    private final MutableLiveData<SuperheroMasterData> _firstSuperhero = new MutableLiveData<>();
    private final MutableLiveData<SuperheroMasterData> _secondSuperhero = new MutableLiveData<>();
    public LiveData<SuperheroMasterData> firstSuperhero = _firstSuperhero;
    public LiveData<SuperheroMasterData> secondSuperhero = _secondSuperhero;

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

    public void setFirstSuperhero(SuperheroMasterData superhero) {
        _firstSuperhero.postValue(superhero);
    }

    public void setSecondSuperhero(SuperheroMasterData superhero) {
        _secondSuperhero.postValue(superhero);
    }

    @Override
    protected SuperheroesRepository getRepository() {
        return repository;
    }

    @Override
    public MutableLiveData<List<SuperheroMasterData>> getSuperheroes() {
        return _superheroes;
    }
}
