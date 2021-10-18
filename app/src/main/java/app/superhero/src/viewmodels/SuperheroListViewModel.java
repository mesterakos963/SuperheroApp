package app.superhero.src.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

import app.superhero.src.api.SuperheroesRepository;
import app.superhero.src.dao.SuperheroMasterData;
import app.superhero.src.interfaces.ListCallback;

@EBean(scope = EBean.Scope.Fragment)
public class SuperheroListViewModel extends ViewModel {
    private final MutableLiveData<List<SuperheroMasterData>> _superheroes = new MutableLiveData<>();
    private final MutableLiveData<Throwable> error = new MutableLiveData<>();
    private final MutableLiveData<String> _searchText = new MutableLiveData<String>();
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> _onPauseSearchText = new MutableLiveData<>();
    private final MutableLiveData<List<SuperheroMasterData>> _favourites = new MutableLiveData<>();
    private final MutableLiveData<SuperheroMasterData> _firstSuperhero = new MutableLiveData<>();
    private final MutableLiveData<SuperheroMasterData> _secondSuperhero = new MutableLiveData<>();
    public LiveData<List<SuperheroMasterData>> superheroes = _superheroes;
    public LiveData<String> onPauseSearchText = _onPauseSearchText;
    public LiveData<List<SuperheroMasterData>> favourites = _favourites;
    public LiveData<String> searchText = _searchText;
    public LiveData<Boolean> isLoading = _isLoading;
    public LiveData<SuperheroMasterData> firstSuperhero = _firstSuperhero;
    public LiveData<SuperheroMasterData> secondSuperhero = _secondSuperhero;

    @Bean
    SuperheroesRepository superheroesRepository;

    public void fetchSuperheroes(String name) {
        _isLoading.postValue(true);
        superheroesRepository.searchByName(name, new ListCallback<SuperheroMasterData>() {
            @Override
            public void onSuccess(List<SuperheroMasterData> results) {
                _superheroes.postValue(results);
                _isLoading.postValue(false);
            }

            @Override
            public void onError(List<SuperheroMasterData> fallbackResult, Throwable t) {
                _superheroes.postValue(fallbackResult);
                error.postValue(t);
                _isLoading.postValue(false);
            }
        });
    }

    public void fetchFavourites() {
        superheroesRepository.getFavourites(new ListCallback<SuperheroMasterData>() {
            @Override
            public void onSuccess(List<SuperheroMasterData> results) {
                _favourites.postValue(results);
            }

            @Override
            public void onError(List<SuperheroMasterData> fallbackResult, Throwable t) {
                _favourites.postValue(fallbackResult);
                error.postValue(t);
            }
        });
    }

    public void setIsFavourite(SuperheroMasterData superhero) {
        superhero.setFavourite(!superhero.getIsFavourite());
        superheroesRepository.cacheFavourite(superhero);
    }

    @AfterInject
    protected void init() {
        _isLoading.postValue(false);
    }

    public void postSearch(String search) {
        _searchText.postValue(search);
    }

    public String getSearchTextString() {
        return _searchText.getValue();
    }

    public void setOnPauseSearchText(String text) {
        _onPauseSearchText.postValue(text);
    }

    public void setFirstSuperhero(SuperheroMasterData superhero) {
        _firstSuperhero.postValue(superhero);
    }

    public void setSecondSuperhero(SuperheroMasterData superhero) {
        _secondSuperhero.postValue(superhero);
    }
}
