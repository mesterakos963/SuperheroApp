package app.superhero.src.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

import app.superhero.src.api.SuperheroesRepository;
import app.superhero.src.dao.SuperheroMasterData;
import app.superhero.src.interfaces.ListCallback;

@EBean(scope = EBean.Scope.Fragment)
public class SuperheroesListViewModel extends SuperheroParentViewModel {
    private final MutableLiveData<String> _searchText = new MutableLiveData<String>();
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> _onPauseSearchText = new MutableLiveData<>();
    public LiveData<String> onPauseSearchText = _onPauseSearchText;
    public LiveData<String> searchText = _searchText;
    public LiveData<Boolean> isLoading = _isLoading;

    @Bean
    SuperheroesRepository repository;

    @Background
    public void fetchSuperheroes(String name, boolean forcedToRefreshImmediately) {
        _isLoading.postValue(true);
        if(forcedToRefreshImmediately){
            _superheroes.postValue(repository.getSuperHeroesFromDbByName(name));
        }
        getRepository().searchByName(name, new ListCallback<SuperheroMasterData>() {
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

    @Override
    protected SuperheroesRepository getRepository() {
        return repository;
    }

    @Override
    public MutableLiveData<List<SuperheroMasterData>> getSuperheroes() {
        return _superheroes;
    }
}
