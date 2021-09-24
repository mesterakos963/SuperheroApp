package app.superhero.src.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import app.superhero.src.api.SuperheroesRepository;
import app.superhero.src.dao.Superhero;
import app.superhero.src.interfaces.ItemCallback;

@EBean(scope = EBean.Scope.Fragment)
public class CharacteristicsViewModel extends ViewModel {

    private final MutableLiveData<Superhero> _superhero = new MutableLiveData<>();
    public LiveData<Superhero> superhero = _superhero;
    @Bean
    SuperheroesRepository repository;

    public void getCharacteristics(int superHeroId) {
        repository.getCharacteristics(superHeroId, new ItemCallback<Superhero>() {
            @Override
            public void onSuccess(Superhero superhero) {
                _superhero.postValue(superhero);
            }

            @Override
            public void onError(Superhero fallbackResult, Throwable t) {

            }
        });
    }
}
