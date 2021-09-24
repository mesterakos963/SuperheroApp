package app.superhero.src.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import app.superhero.src.api.SuperheroesRepository;

@EBean(scope = EBean.Scope.Fragment)
public class CommentsViewModel extends ViewModel {

    @Bean
    SuperheroesRepository repository;

    private final MutableLiveData<String> _comments = new MutableLiveData<>();

    public LiveData<String> comments = _comments;

    @Background
    public void setComment(int id, String comment) {
        _comments.postValue(comment);
        repository.cacheComments(id, comment);
    }
}
