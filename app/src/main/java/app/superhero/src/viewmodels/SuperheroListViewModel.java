package app.superhero.src.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import app.superhero.src.api.SuperheroesRepository;
import app.superhero.src.dao.SuperheroMasterData;
import app.superhero.src.dto.SuperheroDto;
import app.superhero.src.interfaces.CustomCallback;
import app.superhero.src.model.response.SuperheroesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EBean()
public class SuperheroListViewModel extends ViewModel {

    @Bean
    SuperheroesRepository superheroesRepository;

    private final MutableLiveData<List<SuperheroDto>> superheroes = new MutableLiveData<>();
    private final MutableLiveData<Throwable> error = new MutableLiveData<>();
    private final MutableLiveData<String> searchText = new MutableLiveData<String>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public LiveData<List<SuperheroDto>> getSuperheroes() {
        return superheroes;
    }

    public LiveData<String> getSearchText() {
        return searchText;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void fetchSuperheroes(String name) {
        isLoading.postValue(true);
        superheroesRepository.searchByName(name, new CustomCallback<SuperheroMasterData>() {
            @Override
            public void onSuccess(List<SuperheroMasterData> results) {
                if (response.body().isValid()) {
                    superheroes.postValue(response.body().getResults());
                } else {
                    superheroes.postValue(new ArrayList<>());
                }
                isLoading.postValue(false);
            }

            @Override
            public void onError(Throwable t) {
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

}
