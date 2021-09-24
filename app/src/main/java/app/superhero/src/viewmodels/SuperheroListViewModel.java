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
    private final MutableLiveData<String> searchText = new MutableLiveData<String>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> _onPauseSearchText = new MutableLiveData<>();

    public LiveData<List<SuperheroMasterData>> superheroes = _superheroes;
    public LiveData<String> onPauseSearchText = _onPauseSearchText;

    @Bean
    SuperheroesRepository superheroesRepository;

    public LiveData<String> getSearchText() {
        return searchText;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void fetchSuperheroes(String name) {
        isLoading.postValue(true);
        superheroesRepository.searchByName(name, new ListCallback<SuperheroMasterData>() {
            @Override
            public void onSuccess(List<SuperheroMasterData> results) {
                _superheroes.postValue(results);
                isLoading.postValue(false);
            }

            @Override
            public void onError(List<SuperheroMasterData> fallbackResult, Throwable t) {
                _superheroes.postValue(fallbackResult);
                error.postValue(t);
                isLoading.postValue(false);
            }
        });
    }

    @AfterInject
    protected void init() {
        isLoading.postValue(false);
    }

    public void postSearch(String search) {
        searchText.postValue(search);
    }

    public String getSearchTextString() {
        return searchText.getValue();
    }

    public void setOnPauseSearchText(String text) {
        _onPauseSearchText.postValue(text);
    }
}
