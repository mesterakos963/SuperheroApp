package app.superhero.src.fragments;

import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

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
import app.superhero.src.utils.RecyclerViewEmptySupport;
import app.superhero.src.viewmodels.SuperheroListViewModel;
import app.superhero.src.views.EmptyView;
import app.superhero.src.views.SearchbarView;

import static app.superhero.src.utils.Utils.pxFromDp;

@EFragment(R.layout.fragment_superhero_list)
public class SuperheroListFragment extends BaseFragment implements ItemClickListener {

    public final int errorMessageDuration = 5000;

    @Bean
    SuperheroListViewModel superheroListViewModel;

    @ViewById
    RecyclerViewEmptySupport recyclerView;

    @ViewById
    TextView nameText;

    @ViewById
    SearchbarView searchBar;

    @ViewById
    EmptyView emptyView;

    @ViewById
    TextView emptyViewText;

    SuperheroesAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    private Timer timer;

    @AfterViews
    void init() {
        layoutManager = new GridLayoutManager(getContext(), (int) getNumberOfColumns());
        adapter = new SuperheroesAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(emptyView);
        emptyViewText.setText(R.string.empty_view_text);
        observeSuperheroes();
        bindSearchView();
        observeSearchText();
    }

    private void bindSearchView() {
        searchBar.bind(new TextWatcher() {
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
                        superheroListViewModel.postSearch(editable.toString());
                    }
                }, 200);
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
    public void observeSearchText() {
        superheroListViewModel.getSearchText().observe(this, searchText -> {
            superheroListViewModel.fetchSuperheroes(searchText);
        });
    }

    @UiThread
    protected void refreshAdapter(List<Superhero> superheroes) {
        adapter.refreshData(superheroes);
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public float getNumberOfColumns() {
        return getScreenWidth() / pxFromDp(getContext(), 170);
    }

    //loading, amíg nem írok, addig emptyView szöveggel, sikertelen keresésnél szintén
    //frame layout -> emptyView alapból visible, rv meg gone
    //adapternek observer (EmptyDataObserver.java)
    //Frame -> RV (bg transparent)
    //CustomEmptyView extends ContraintLayout
    //Hint
    //icon színe is változzon
    //empty view legyen custom

    //loading(frameLayout) (+1 view) -> progress bar -> xml-ben az empty view fölött legyen, amikor elmegy a request
}
