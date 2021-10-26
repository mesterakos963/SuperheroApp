package app.superhero.src.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import app.superhero.src.api.SuperheroesRepository;
import app.superhero.src.model.dao.SuperheroMasterData;

public abstract class SuperheroParentViewModel extends ViewModel {
    protected final MutableLiveData<List<SuperheroMasterData>> _superheroes = new MutableLiveData<>();
    protected final MutableLiveData<Throwable> error = new MutableLiveData<>();
    public LiveData<List<SuperheroMasterData>> superheroes = _superheroes;

    protected abstract SuperheroesRepository getRepository();

    public abstract MutableLiveData<List<SuperheroMasterData>> getSuperheroes();

    public void setIsFavourite(SuperheroMasterData superhero) {
        superhero.setFavourite(!superhero.getIsFavourite());
        getRepository().cacheFavourite(superhero);
    }
}
