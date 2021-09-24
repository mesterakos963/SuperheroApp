package app.superhero.src.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import app.superhero.src.api.SuperheroesRepository;
import app.superhero.src.dao.Comments;
import app.superhero.src.interfaces.ItemCallback;

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

    public void getComment(int id){
        repository.getCommentFromDbById(id, new ItemCallback<Comments>() {
            @Override
            public void onSuccess(Comments result) {
                _comments.postValue(result.getComment());
            }

            @Override
            public void onError(Comments fallbackResult, Throwable t) {
                _comments.postValue("");
            }
        });
    }
}
