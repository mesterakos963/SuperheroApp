package app.superhero.src.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

import app.superhero.src.api.SuperheroesRepository;
import app.superhero.src.model.response.SuperheroesResponse;
import app.superhero.src.models.Superhero;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EBean()
public class SuperheroListViewModel extends ViewModel {

    @Bean
    SuperheroesRepository superheroesRepository;

    private final MutableLiveData<List<Superhero>> superheroes = new MutableLiveData<>();
    private final MutableLiveData<Throwable> error = new MutableLiveData<>();
    public MutableLiveData<String> searchText = new MutableLiveData<String>();

    public LiveData<List<Superhero>> getSuperheroes() { return superheroes; }
    public LiveData<String> getSearchText() {
        return searchText;
    }

    public void fetchSuperheroes(String name) {
        superheroesRepository.searchByName(name, new Callback<SuperheroesResponse>() {
            @Override
            public void onResponse(Call<SuperheroesResponse> call, Response<SuperheroesResponse> response) {
                if (response.body().isValid()) {
                    superheroes.postValue(response.body().getResults());
                } else {
                    response.body().getError();
                }
            }

            @Override
            public void onFailure(Call<SuperheroesResponse> call, Throwable t) {
                error.postValue(t);
            }
        });
    }

    public void postSearch(String search) {
        searchText.postValue(search);
    }
}
