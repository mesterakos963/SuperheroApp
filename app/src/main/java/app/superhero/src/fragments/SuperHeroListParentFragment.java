package app.superhero.src.fragments;

import android.content.res.Resources;

import androidx.annotation.UiThread;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

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

    @ViewById
    EmptyView emptyView;

    protected SuperheroesAdapter adapter;
    protected RecyclerView.LayoutManager layoutManager;
    protected StarClickCallback starClickCallback;

    @AfterViews
    protected void init() {
        doOnInit();
        starClick();
        layoutManager = new GridLayoutManager(getContext(), getNumberOfColumns());
        adapter = new SuperheroesAdapter(new ArrayList<>(), this, starClickCallback);
        getRecyclerView().setLayoutManager(layoutManager);
        getRecyclerView().setAdapter(adapter);
        getRecyclerView().setEmptyView(getEmptyView());
        observeSuperheroes();
    }

    protected abstract void doOnInit();

    protected abstract void starClick();

    protected abstract RecyclerViewEmptySupport getRecyclerView();

    protected abstract EmptyView getEmptyView();

    @UiThread
    protected abstract void refreshAdapter(List<SuperheroMasterData> superheroList);

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    @Override
    public abstract void onItemClick(SuperheroMasterData superhero);

    protected int getNumberOfColumns() {
        return (int) (getScreenWidth() / pxFromDp(requireContext(), 170));
    }

    @UiThread
    public void observeSuperheroes() {
        viewModel.superheroes.observe(this, this::refreshAdapter);
    }
}



