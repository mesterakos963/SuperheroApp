package app.superhero.src.fragments;

import android.content.res.Resources;

import androidx.annotation.UiThread;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.List;

import app.superhero.R;
import app.superhero.src.api.SuperheroesAdapter;
import app.superhero.src.dao.SuperheroMasterData;
import app.superhero.src.interfaces.ItemClickListener;
import app.superhero.src.interfaces.StarClickCallback;
import app.superhero.src.utils.RecyclerViewEmptySupport;
import app.superhero.src.viewmodels.SuperheroListViewModel;
import app.superhero.src.views.EmptyView;

import static app.superhero.src.utils.Utils.pxFromDp;

@EFragment
public abstract class SuperHeroListParentFragment extends BaseFragment implements ItemClickListener {

    @Bean
    SuperheroListViewModel viewModel;

    protected SuperheroesAdapter adapter;
    protected RecyclerView.LayoutManager layoutManager;
    protected StarClickCallback starClickCallback;

    @AfterViews
    protected void init() {
        doOnInit();
        starClickCallback = new StarClickCallback() {
            @Override
            public void onStarClick(SuperheroMasterData superhero) {
                viewModel.setIsFavourite(superhero);
            }
        };
        layoutManager = new GridLayoutManager(getContext(), getNumberOfColumns());
        adapter = new SuperheroesAdapter(new ArrayList<>(), this, starClickCallback);
        getRecyclerView().setLayoutManager(layoutManager);
        getRecyclerView().setAdapter(adapter);
        getRecyclerView().setEmptyView(getEmptyView());
        observeSuperheroes();
    }

    protected abstract void doOnInit();

    protected abstract void setEmptyViewText(EmptyView emptyView, String text);

    protected abstract RecyclerViewEmptySupport getRecyclerView();

    protected abstract EmptyView getEmptyView();

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    @Override
    public void onItemClick(SuperheroMasterData superhero) {
        if (getActivity() != null) {
            NavDirections action =
                    SuperheroListFragment_Directions.actionSuperheroListFragmentToSuperheroDetailsFragment(superhero);
            Navigation.findNavController(getActivity(), R.id.navHostFragment).navigate(action);
        }
    }

    @UiThread
    protected void refreshAdapter(List<SuperheroMasterData> superheroList) {
    }

    protected int getNumberOfColumns() {
        return (int) (getScreenWidth() / pxFromDp(getContext(), 170));
    }

    @UiThread
    public void observeSuperheroes() {
        viewModel.superheroes.observe(this, this::refreshAdapter);
    }
}



