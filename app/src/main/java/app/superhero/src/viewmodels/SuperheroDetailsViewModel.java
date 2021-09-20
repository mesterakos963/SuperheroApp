package app.superhero.src.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import app.superhero.src.api.SuperheroesRepository;
import retrofit2.Callback;

@EBean(scope = EBean.Scope.Fragment)
public class SuperheroDetailsViewModel {

    @Bean
    SuperheroesRepository repository;

    private final MutableLiveData<Integer> selectedPage = new MutableLiveData<>();

    public LiveData<Integer> getSelectedPage() {
        return selectedPage;
    }

    public int getSelectedPageAsInt() {
        if (selectedPage.getValue() != null) {
            return selectedPage.getValue();
        }
        return 0;
    }

    public void getCharacteristics(int id, Callback callback) {
        repository.getCharacteristics(id, callback);
    }

    public void setSelectedPage(int j) {
        selectedPage.postValue(j);
    }
}

