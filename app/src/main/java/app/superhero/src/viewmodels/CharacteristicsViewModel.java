package app.superhero.src.viewmodels;

import androidx.lifecycle.ViewModel;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import app.superhero.src.api.SuperheroesRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EBean(scope = EBean.Scope.Fragment)
public class CharacteristicsViewModel extends ViewModel {

    @Bean
    SuperheroesRepository repository;

    public void getCharacteristics(int superHeroId) {
        repository.getCharacteristics(superHeroId, new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }
}
