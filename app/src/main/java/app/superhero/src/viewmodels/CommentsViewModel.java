package app.superhero.src.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import app.superhero.src.api.SuperheroesRepository;
import app.superhero.src.interfaces.ItemCallback;
import app.superhero.src.model.dao.Comments;

@EBean(scope = EBean.Scope.Fragment)
public class CommentsViewModel extends ViewModel {
    private final MutableLiveData<String> _comments = new MutableLiveData<>();
    public LiveData<String> comments = _comments;

    @Bean
    SuperheroesRepository repository;

    @Background
    public void setComment(int id, String comment) {
        _comments.postValue(comment);
        repository.cacheComments(id, comment);
    }

    public void getComment(int id) {
        repository.getCommentFromDbById(id, new ItemCallback<Comments>() {
            @Override
            public void onSuccess(Comments result) {
                if (result != null) {
                    _comments.postValue(result.getComment());
                } else {
                    _comments.postValue("");
                }
            }

            @Override
            public void onError(Comments fallbackResult, Throwable t) {
                _comments.postValue("");
            }
        });
    }
}
