package app.superhero.src.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.androidannotations.annotations.EBean;

@EBean(scope = EBean.Scope.Fragment)
public class SuperheroDetailsViewModel {

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

    public void setSelectedPage(int j) {
        selectedPage.postValue(j);
    }
}

