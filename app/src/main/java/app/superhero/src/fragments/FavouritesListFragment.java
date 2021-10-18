package app.superhero.src.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
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
import app.superhero.src.viewmodels.FavouritesViewModel;
import app.superhero.src.viewmodels.SuperheroParentViewModel;
import app.superhero.src.views.EmptyView;

@EFragment(R.layout.fragment_favourites)
public class FavouritesListFragment extends SuperHeroListParentFragment {
    @Bean
    FavouritesViewModel viewModel;

    @ViewById
    RecyclerViewEmptySupport recyclerView;

    @Override
    protected RecyclerViewEmptySupport getRecyclerView() {
        return recyclerView;
    }

    @Override
    protected EmptyView getEmptyView() {
        return emptyView;
    }

    @Override
    protected SuperheroParentViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        observeFavourites();
    }

    @Override
    protected void doOnInit() {
        viewModel.fetchSuperheroes();
    }

    @Override
    protected void starClick() {
        starClickCallback = (superhero, position) -> {
            viewModel.setIsFavourite(superhero);
            adapter.deleteItem(position);
            if (adapter.getItemCount() == 0) {
                emptyView.setText(getResources().getString(R.string.favourites_empty_view));
            }
        };
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
        adapter.setData(superheroList);
        if (superheroList.isEmpty()) {
            emptyView.setText(getResources().getString(R.string.favourites_empty_view));
        }
    }

    private void observeFavourites() {
        viewModel.superheroes.observe(this, this::refreshAdapter);
    }
}
