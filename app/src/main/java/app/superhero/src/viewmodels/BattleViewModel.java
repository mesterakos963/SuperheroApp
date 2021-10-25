package app.superhero.src.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.superhero.src.api.SuperheroesRepository;
import app.superhero.src.dao.Powerstats;
import app.superhero.src.dao.SuperheroMasterData;
import app.superhero.src.interfaces.ItemCallback;
import app.superhero.src.interfaces.ListCallback;

@EBean(scope = EBean.Scope.Fragment)
public class BattleViewModel extends SuperheroParentViewModel {
    private final MutableLiveData<SuperheroMasterData> _firstSuperhero = new MutableLiveData<>();
    private final MutableLiveData<SuperheroMasterData> _secondSuperhero = new MutableLiveData<>();
    private final MutableLiveData<Throwable> error = new MutableLiveData<>();
    private final MutableLiveData<Powerstats> _firstHeroPowerstats = new MutableLiveData<>();
    private final MutableLiveData<Powerstats> _secondHeroPowerstats = new MutableLiveData<>();
    private final MutableLiveData<Map<Integer, Integer>> _heroWithHp = new MutableLiveData<>();
    private final MutableLiveData<Integer> _defender = new MutableLiveData<>();
    public LiveData<SuperheroMasterData> firstSuperhero = _firstSuperhero;
    public LiveData<SuperheroMasterData> secondSuperhero = _secondSuperhero;
    public LiveData<Powerstats> firstHeroPowerstats = _firstHeroPowerstats;
    public LiveData<Powerstats> secondHeroPowerstats = _secondHeroPowerstats;
    public LiveData<Map<Integer, Integer>> heroWithHp = _heroWithHp;
    public LiveData<Integer> defender = _defender;

    private final Map<Integer, Integer> tmp = new HashMap<>();

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

    public void getFirstPowerstats(int superheroId) {
        repository.getPowerstats(superheroId, new ItemCallback<Powerstats>() {
            @Override
            public void onSuccess(Powerstats result) {
                _firstHeroPowerstats.postValue(result);
            }

            @Override
            public void onError(Powerstats fallbackResult, Throwable t) {
                _firstHeroPowerstats.postValue(fallbackResult);
                error.postValue(t);
            }
        });
    }

    public void getSecondPowerstats(int superheroId) {
        repository.getPowerstats(superheroId, new ItemCallback<Powerstats>() {
            @Override
            public void onSuccess(Powerstats result) {
                _secondHeroPowerstats.postValue(result);
            }

            @Override
            public void onError(Powerstats fallbackResult, Throwable t) {
                _secondHeroPowerstats.postValue(fallbackResult);
                error.postValue(t);
            }
        });
    }

    public void setFirstSuperhero(SuperheroMasterData superhero) {
        _firstSuperhero.postValue(superhero);
        tmp.put(superhero.getId(), 100);
        _heroWithHp.postValue(tmp);
    }

    public void setSecondSuperhero(SuperheroMasterData superhero) {
        _secondSuperhero.setValue(superhero);
        tmp.put(superhero.getId(), 100);
        _heroWithHp.postValue(tmp);
    }

    public void setDefender(int id) {
        _defender.postValue(id);
    }

    public void refreshHp(int id, int hp) {
        int newHp = heroWithHp.getValue().get(id) - hp;
        if (newHp <= 0) {
            tmp.put(defender.getValue(), 0);
            heroWithHp.getValue().put(id, 0);
        } else {
            tmp.put(defender.getValue(), newHp);
            heroWithHp.getValue().put(id, newHp);
        }
        _heroWithHp.postValue(tmp);
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
