package app.superhero.src.fragments;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.List;

import app.superhero.MainActivity;
import app.superhero.src.api.SuperheroesAdapter;
import app.superhero.src.dao.SuperheroMasterData;
import app.superhero.src.interfaces.ItemClickListener;
import app.superhero.src.interfaces.StarClickCallback;
import app.superhero.src.viewmodels.SuperheroParentViewModel;
import app.superhero.src.views.EmptyView;

import static app.superhero.src.utils.Utils.pxFromDp;

@EFragment
public abstract class SuperHeroListParentFragment extends BaseFragment implements ItemClickListener {

    protected SuperheroesAdapter adapter;
    protected RecyclerView.LayoutManager layoutManager;
    protected StarClickCallback starClickCallback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        observeSuperheroes();
    }

    @AfterViews
    protected void init() {
        ((MainActivity) getActivity()).setBottomNavigationViewVisibile(true);
        doOnInit();
        starClick();
        layoutManager = new GridLayoutManager(getContext(), getNumberOfColumns());
        adapter = new SuperheroesAdapter(new ArrayList<>(), this, starClickCallback, false, true, false);
        getRecyclerView().setLayoutManager(layoutManager);
        getRecyclerView().setAdapter(adapter);
    }

    protected abstract void doOnInit();

    protected abstract void starClick();

    protected abstract RecyclerView getRecyclerView();

    protected abstract EmptyView getEmptyView();

    protected abstract SuperheroParentViewModel getViewModel();

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
        getViewModel().getSuperheroes().observe(this, this::refreshAdapter);
    }
}



