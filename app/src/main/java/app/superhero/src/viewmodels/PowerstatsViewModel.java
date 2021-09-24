package app.superhero.src.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import app.superhero.src.api.SuperheroesRepository;
import app.superhero.src.dao.Powerstats;
import app.superhero.src.interfaces.ItemCallback;

@EBean(scope = EBean.Scope.Fragment)
public class PowerstatsViewModel extends ViewModel {

    @Bean
    SuperheroesRepository repository;

    private final MutableLiveData<Powerstats> _powerstats = new MutableLiveData<>();
    public LiveData<Powerstats> powerstats = _powerstats;

    public void getPowerstats(int superheroId) {
        repository.getPowerstats(superheroId, new ItemCallback<Powerstats>() {
            @Override
            public void onSuccess(Powerstats result) {
                _powerstats.postValue(result);
            }

            @Override
            public void onError(Powerstats fallbackResult, Throwable t) {

            }
        });
    }
}
