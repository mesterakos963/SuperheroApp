package app.superhero.src.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import app.superhero.R;
import app.superhero.src.dao.SuperheroMasterData;
import app.superhero.src.viewmodels.FavouritesViewModel;
import app.superhero.src.viewmodels.SuperheroParentViewModel;
import app.superhero.src.views.EmptyView;

@EFragment(R.layout.fragment_favourites)
public class FavouritesListFragment extends SuperHeroListParentFragment {
    @Bean
    FavouritesViewModel viewModel;

    @ViewById
    RecyclerView recyclerView;

    @Override
    protected RecyclerView getRecyclerView() {
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

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void starClick() {
        starClickCallback = (superhero, position) -> {
            viewModel.setIsFavourite(superhero);
            if (adapter.getItemCount() > 0) {
                viewModel.removeSuperHeroFromList(superhero);
            }
            if (adapter.getItemCount() == 0) {
                emptyView.setText(getResources().getString(R.string.favourites_empty_view));
                emptyView.setEmptyViewImage(getResources().getDrawable(R.drawable.ic_undraw_be_the_hero_ssr2));
            }
        };
    }

    @Override
    public void onItemClick(SuperheroMasterData superhero) {
        if (getActivity() != null) {
            SuperheroDetailsFragment superheroDetailsFragment = SuperheroDetailsFragment_.builder().superhero(superhero).build();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim,
                            R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim)
                    .replace(R.id.navHostFragment, superheroDetailsFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    @UiThread
    protected void refreshAdapter(List<SuperheroMasterData> superheroList) {
        adapter.setData(superheroList);
        if (superheroList.isEmpty()) {
            emptyView.setText(getResources().getString(R.string.favourites_empty_view));
            emptyView.setEmptyViewImage(getResources().getDrawable(R.drawable.ic_undraw_be_the_hero_ssr2));
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }
    }

    private void observeFavourites() {
        viewModel.superheroes.observe(this, this::refreshAdapter);
    }
}
