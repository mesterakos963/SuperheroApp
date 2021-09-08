package app.superhero.src.fragments;

import android.app.Activity;
import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import app.superhero.src.api.SuperheroesAdapter;
import app.superhero.src.dto.SuperheroDto;
import app.superhero.src.interfaces.ItemClickListener;
import app.superhero.src.utils.RecyclerViewEmptySupport;
import app.superhero.src.viewmodels.SuperheroListViewModel;
import app.superhero.src.views.EmptyView;
import app.superhero.src.views.LoadingView;
import app.superhero.src.views.SearchbarView;
import app.superheroDto.R;

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

    private Timer timer;

    @AfterViews
    void init() {
        layoutManager = new GridLayoutManager(getContext(), (int) getNumberOfColumns());
        adapter = new SuperheroesAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(emptyView);
        emptyView.setOnClickListenerToEmptyView(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null) {
                    hideKeyboard(getActivity());
                }
                searchBar.clearEditTextFocus();
            }
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
    public void onItemClick(SuperheroDto superheroDto) {
        if (getActivity() != null) {
            Navigation.findNavController(getActivity(), R.id.navHostFragment)
                    .navigate(SuperheroListFragment_Directions.actionSuperheroListFragmentToSuperheroProfileFragment2(superheroDto));
        }
    }

    @UiThread
    public void observeSuperheroes() {
        superheroListViewModel.getSuperheroes().observe(this, this::refreshAdapter);
    }

    @UiThread
    public void observeSearchText() {
        superheroListViewModel.getSearchText().observe(this, searchText -> {
            setEmptyViewText();
            superheroListViewModel.fetchSuperheroes(searchText);
        });
    }

    @UiThread
    public void observeIsLoading() {
        superheroListViewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                loadingView.setVisibility(View.VISIBLE);
            } else {
                loadingView.setVisibility(View.GONE);
            }
        });
    }

    @UiThread
    protected void refreshAdapter(List<SuperheroDto> superheroDtos) {
        adapter.refreshData(superheroDtos);
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public float getNumberOfColumns() {
        return getScreenWidth() / pxFromDp(getContext(), 170);
    }

    public void setEmptyViewText() {
        if (superheroListViewModel.getSearchTextString() == null || superheroListViewModel.getSearchTextString().isEmpty()) {
            emptyViewText.setText(R.string.empty_view_text);
        } else {
            emptyViewText.setText(R.string.empty_view_error_text);
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
