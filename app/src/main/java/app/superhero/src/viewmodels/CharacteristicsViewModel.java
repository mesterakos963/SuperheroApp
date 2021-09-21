package app.superhero.src.viewmodels;

import androidx.lifecycle.ViewModel;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import app.superhero.src.api.SuperheroesRepository;
import app.superhero.src.dao.Superhero;
import app.superhero.src.interfaces.ItemCallback;

@EBean(scope = EBean.Scope.Fragment)
public class CharacteristicsViewModel extends ViewModel {

    @Bean
    SuperheroesRepository repository;

    public void getCharacteristics(int superHeroId) {
        repository.getCharacteristics(superHeroId, new ItemCallback<Superhero>() {
            @Override
            public void onSuccess(Superhero superhero) {
                //belekúrni a livedataba, feliratkozni rá, setData-val frissíteni a customView-kat, csumi
            }

            @Override
            public void onError(Superhero fallbackResult, Throwable t) {

            }
        });
    }
}
