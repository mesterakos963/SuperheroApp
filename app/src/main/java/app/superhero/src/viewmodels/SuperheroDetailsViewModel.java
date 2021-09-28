package app.superhero.src.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import app.superhero.src.api.SuperheroesRepository;
import app.superhero.src.dao.SuperheroMasterData;

@EBean(scope = EBean.Scope.Fragment)
public class SuperheroDetailsViewModel extends ViewModel {

    private final MutableLiveData<SuperheroMasterData> _superheroMasterData = new MutableLiveData<>();
    private final MutableLiveData<Integer> _selectedPage = new MutableLiveData<>();
    public LiveData<Integer> selectedPage = _selectedPage;
    public LiveData<SuperheroMasterData> superheroMasterData = _superheroMasterData;


    /*  todo átírni, hogy egy egész SHMD-t passzolni át
        onClick -> átváltani a kinézetet és a VM-ből elindítani az updetet
     */

    @Bean
    SuperheroesRepository repository;

    public void setSelectedPage(int j) {
        _selectedPage.postValue(j);
    }

    public void setSuperheroMasterData(SuperheroMasterData superheroMasterData) {
        _superheroMasterData.postValue(superheroMasterData);
    }
}

