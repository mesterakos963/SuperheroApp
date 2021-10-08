package app.superhero.src.fragments;

import android.widget.TextView;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import app.superhero.R;
import app.superhero.src.dao.SuperheroMasterData;
import app.superhero.src.utils.RecyclerViewEmptySupport;
import app.superhero.src.viewmodels.SuperheroListViewModel;
import app.superhero.src.views.EmptyView;

@EFragment(R.layout.fragment_favourites)
public class FavouritesListFragment extends SuperHeroListParentFragment {

    @Bean
    SuperheroListViewModel superheroListViewModel;

    @ViewById
    RecyclerViewEmptySupport recyclerView;

    @ViewById
    EmptyView emptyView;

    @ViewById
    TextView emptyViewText;

    @Override
    protected RecyclerViewEmptySupport getRecyclerView() {
        return recyclerView;
    }

    @Override
    protected EmptyView getEmptyView() {
        return emptyView;
    }

    @Override
    protected void setEmptyViewText(EmptyView emptyView, String text) {
        emptyView.setText(getResources().getString(R.string.favourites_empty_view));
    }

    @Override
    protected void doOnInit() {
        viewModel.fetchFavourites();
        observeFavourites();
    }

    @Override
    public void onItemClick(SuperheroMasterData superhero) {
        if (getActivity() != null) {
            NavDirections action =
                    FavouritesListFragment_Directions.actionFavouritesListFragmentToSuperheroDetailsFragment(superhero);
            Navigation.findNavController(getActivity(), R.id.navHostFragment).navigate(action);
        }
    }

    @Override
    @UiThread
    protected void refreshAdapter(List<SuperheroMasterData> superheroList) {
        if (superheroList != null) {
            adapter.setData(superheroList);
        }
    }

    private void observeFavourites() {
        viewModel.favourites.observe(this, this::refreshAdapter);
    }
}
