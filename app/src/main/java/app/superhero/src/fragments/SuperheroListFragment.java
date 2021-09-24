package app.superhero.src.fragments;

import android.app.Activity;
import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import app.superhero.R;
import app.superhero.src.api.SuperheroesAdapter;
import app.superhero.src.dao.SuperheroMasterData;
import app.superhero.src.interfaces.ItemClickListener;
import app.superhero.src.utils.RecyclerViewEmptySupport;
import app.superhero.src.viewmodels.SuperheroListViewModel;
import app.superhero.src.views.EmptyView;
import app.superhero.src.views.LoadingView;
import app.superhero.src.views.SearchbarView;

import static app.superhero.src.utils.Utils.pxFromDp;

@EFragment(R.layout.fragment_superhero_list)
public class SuperheroListFragment extends BaseFragment implements ItemClickListener {

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

    @ViewById
    CollapsingToolbarLayout toolbarLayout;

    @ViewById
    LoadingView loadingView;

    SuperheroesAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    app.superhero.src.utils.Debouncer debouncer;
    private boolean firstStart = true;

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @AfterViews
    void init() {
        layoutManager = new GridLayoutManager(getContext(), getNumberOfColumns());
        adapter = new SuperheroesAdapter(new ArrayList<>(), this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(emptyView);
        emptyView.setOnClickListenerToEmptyView(view -> {
            if (getActivity() != null) {
                hideKeyboard(getActivity());
            }
            searchBar.clearEditTextFocus();
        });
        emptyViewText.setText(R.string.empty_view_text);
        toolbarLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                emptyView.setPaddingBottom(toolbarLayout.getMeasuredHeight());
                loadingView.setPaddingBottom(toolbarLayout.getMeasuredHeight());
                toolbarLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        observeSuperheroes();
        bindSearchView();
        observeSearchText();
        observeIsLoading();
        debouncer = new app.superhero.src.utils.Debouncer(500, TimeUnit.MILLISECONDS,
                message -> superheroListViewModel.postSearch(message)
        );
    }

    private void bindSearchView() {
        searchBar.bind(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                debouncer.process(editable.toString());
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        firstStart = false;
        superheroListViewModel.setOnPauseSearchText(superheroListViewModel.getSearchTextString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onItemClick(SuperheroMasterData superhero) {
        if (getActivity() != null) {
            NavDirections action =
                    SuperheroListFragment_Directions.actionSuperheroListFragmentToSuperheroDetailsFragment(superhero.getId(), superhero.getUrl(), superhero.getName());
            Navigation.findNavController(getActivity(), R.id.navHostFragment).navigate(action);
        }
    }

    @UiThread
    public void observeSuperheroes() {
        superheroListViewModel.superheroes.observe(this, this::refreshAdapter);
    }

    @UiThread
    public void observeSearchText() {
        superheroListViewModel.getSearchText().observe(this, searchText -> {
            setEmptyViewText();
            if (!searchText.isEmpty()
                    && firstStart
                    || superheroListViewModel.onPauseSearchText.getValue() != null
                    && !superheroListViewModel.onPauseSearchText.getValue().equals(searchText)) {
                superheroListViewModel.fetchSuperheroes(searchText);
                superheroListViewModel.setOnPauseSearchText("");
            }
        });
    }

    @UiThread
    public void observeIsLoading() {
        superheroListViewModel.getIsLoading().observe(this, isLoading -> {
            if (firstStart && isLoading) {
                loadingView.setVisibility(View.VISIBLE);
            } else {
                loadingView.setVisibility(View.GONE);
            }
        });
    }

    @UiThread
    protected void refreshAdapter(List<SuperheroMasterData> superheroList) {
        if (superheroList != null) {
            adapter.setData(superheroList);
        }
        if (superheroList != null && firstStart) {
            loadingView.setVisibility(View.GONE);
        }
    }

    public int getNumberOfColumns() {
        return (int) (getScreenWidth() / pxFromDp(getContext(), 170));
    }

    public void setEmptyViewText() {
        if (superheroListViewModel.getSearchTextString() == null
                || superheroListViewModel.getSearchTextString().isEmpty()) {
            emptyViewText.setText(R.string.empty_view_text);
        } else {
            emptyViewText.setText(R.string.empty_view_error_text);
        }
    }
}
