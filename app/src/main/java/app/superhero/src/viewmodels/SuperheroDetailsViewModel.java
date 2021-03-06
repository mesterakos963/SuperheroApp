package app.superhero.src.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import app.superhero.src.api.SuperheroesRepository;
import app.superhero.src.model.dao.SuperheroMasterData;

@EBean(scope = EBean.Scope.Fragment)
public class SuperheroDetailsViewModel extends ViewModel {
    private final MutableLiveData<SuperheroMasterData> _superheroMasterData = new MutableLiveData<>();
    private final MutableLiveData<Integer> _selectedPage = new MutableLiveData<>();
    public LiveData<Integer> selectedPage = _selectedPage;
    public LiveData<SuperheroMasterData> superheroMasterData = _superheroMasterData;

    @Bean
    SuperheroesRepository repository;

    public void setSelectedPage(int j) {
        _selectedPage.postValue(j);
    }

    public void setSuperheroMasterData(SuperheroMasterData superheroMasterData) {
        _superheroMasterData.postValue(superheroMasterData);
    }

    public void setIsFavourite() {
        SuperheroMasterData superhero = _superheroMasterData.getValue();
        superhero.setFavourite(!superhero.getIsFavourite());
        repository.cacheFavourite(superhero);
        _superheroMasterData.postValue(superhero);
    }
}

