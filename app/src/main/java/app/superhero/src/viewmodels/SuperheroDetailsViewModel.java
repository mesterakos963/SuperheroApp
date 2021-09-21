package app.superhero.src.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import app.superhero.src.api.SuperheroesRepository;

@EBean(scope = EBean.Scope.Fragment)
public class SuperheroDetailsViewModel extends ViewModel {

    @Bean
    SuperheroesRepository repository;

    private final MutableLiveData<Integer> _selectedPage = new MutableLiveData<>();
    public LiveData<Integer> selectedPage = _selectedPage;

    private final MutableLiveData<Integer> _superheroId = new MutableLiveData<>();
    public LiveData<Integer> superheroId = _superheroId;

    private final MutableLiveData<String> _imageUrl = new MutableLiveData<>();
    public LiveData<String> imageUrl = _imageUrl;

    private final MutableLiveData<String> _name = new MutableLiveData<>();
    public LiveData<String> name = _name;

    public void setSelectedPage(int j) {
        _selectedPage.postValue(j);
    }

    public void setSuperheroId(int superheroId) {
        _superheroId.postValue(superheroId);
    }

    public void setImageUrl(String imageUrl) {
        _imageUrl.postValue(imageUrl);
    }

    public void setName(String name) {
        _name.postValue(name);
    }

}

