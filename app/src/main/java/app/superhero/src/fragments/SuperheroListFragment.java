package app.superhero.src.fragments;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import app.superhero.R;
import app.superhero.src.api.SuperheroesAdapter;
import app.superhero.src.interfaces.ItemClickListener;
import app.superhero.src.models.Superhero;
import app.superhero.src.viewmodels.SuperheroListViewModel;
import app.superhero.src.views.SearchbarView;

@EFragment(R.layout.fragment_superhero_list)
public class SuperheroListFragment extends BaseFragment implements ItemClickListener {

    public final int errorMessageDuration = 5000;

    @Bean
    SuperheroListViewModel superheroListViewModel;

    @ViewById
    RecyclerView recyclerView;

    @ViewById
    ConstraintLayout recyclerViewContainer;

    @ViewById
    TextView nameText;

    @ViewById
    SearchbarView searchBar;

    SuperheroesAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    private Timer timer;

    @AfterViews
    void init() {
        layoutManager = new GridLayoutManager(getContext(), 2);
        adapter = new SuperheroesAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        observeSuperheroes();
        bindSearchView();
    }

    private void bindSearchView() {
        searchBar.bind("", new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        superheroListViewModel.fetchSuperheroes(editable.toString());
                    }
                }, 400);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onItemClick(Superhero superhero) {
        if (getActivity() != null) {
            Navigation.findNavController(getActivity(), R.id.navHostFragment)
                    .navigate(SuperheroListFragment_Directions.actionSuperheroListFragmentToSuperheroProfileFragment2(superhero));
        }
    }

    @UiThread
    public void observeSuperheroes() {
        superheroListViewModel.getSuperheroes().observe(this, this::refreshAdapter);
    }

    @UiThread
    protected void refreshAdapter(List<Superhero> superheroes){
        adapter.refreshData(superheroes);
    }
}
