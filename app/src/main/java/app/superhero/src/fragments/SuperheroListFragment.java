package app.superhero.src.fragments;

import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import app.superhero.R;
import app.superhero.src.api.SuperheroesAdapter;
import app.superhero.src.interfaces.ItemClickListener;
import app.superhero.src.models.Superhero;
import app.superhero.src.viewmodels.SuperheroListViewModel;

@EFragment(R.layout.fragment_superhero_list)
public class SuperheroListFragment extends BaseFragment implements ItemClickListener {

    @Bean
    SuperheroListViewModel superheroListViewModel;

    @ViewById
    RecyclerView recyclerView;

    @ViewById
    CoordinatorLayout recyclerViewContainer;

    @ViewById
    TextView nameText;

    SuperheroesAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @AfterViews
    void init() {
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new SuperheroesAdapter(requireContext(), new ArrayList<>(), this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        observeSuperheroes();
        superheroListViewModel.fetchSuperheroes();
    }

    @Override
    public void onItemClick(Superhero superhero) {
        if (getActivity() != null) {
            Navigation.findNavController(getActivity(), R.id.navHostFragment)
                    .navigate(SuperheroListFragment_Directions.actionSuperheroListFragmentToSuperheroProfileFragment2(superhero));
        }
    }

    public void observeSuperheroes() {
        superheroListViewModel.getSuperheroes().observe(this, superheroes -> {
            adapter.update(superheroes);
        });
    }

}
