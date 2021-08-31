package app.superhero.src.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

import app.superhero.src.api.SuperheroesRepository;
import app.superhero.src.api.SuperheroesResponse;
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

    public LiveData<List<Superhero>> getSuperheroes() {
        return superheroes;
    }

    public LiveData<Throwable> getError() {
        return error;
    }

    public void fetchSuperheroes() {
        superheroesRepository.searchByName("batman", new Callback<SuperheroesResponse>() {
            @Override
            public void onResponse(Call<SuperheroesResponse> call, Response<SuperheroesResponse> response) {
                superheroes.postValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<SuperheroesResponse> call, Throwable t) {
                error.postValue(t);
            }
        });
    }

}
