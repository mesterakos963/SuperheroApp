package app.superhero.src.fragments;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;
import java.util.concurrent.TimeUnit;

import app.superhero.R;
import app.superhero.src.dao.SuperheroMasterData;
import app.superhero.src.interfaces.ItemClickListener;
import app.superhero.src.utils.RecyclerViewEmptySupport;
import app.superhero.src.viewmodels.SuperheroListViewModel;
import app.superhero.src.views.EmptyView;
import app.superhero.src.views.LoadingView;
import app.superhero.src.views.SearchbarView;

@EFragment(R.layout.fragment_superhero_list)
public class SuperheroListFragment extends SuperHeroListParentFragment implements ItemClickListener {

    @Bean
    SuperheroListViewModel superheroListViewModel;

    @ViewById
    RecyclerViewEmptySupport recyclerView;

    @ViewById
    SearchbarView searchBar;

    @ViewById
    CollapsingToolbarLayout toolbarLayout;

    @ViewById
    LoadingView loadingView;

    app.superhero.src.utils.Debouncer debouncer;
    private boolean firstStart = true;

    @Override
    protected void setEmptyViewText() {
        if (superheroListViewModel.getSearchTextString() == null
                || superheroListViewModel.getSearchTextString().isEmpty()) {
            emptyView.setText(getResources().getString(R.string.empty_view_text));
        } else {
            emptyView.setText(getResources().getString(R.string.empty_view_error_text));
        }
    }

    @Override
    protected RecyclerViewEmptySupport getRecyclerView() {
        return recyclerView;
    }

    @Override
    protected EmptyView getEmptyView() {
        return emptyView;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void doOnInit() {
        emptyView.setText(getResources().getString(R.string.empty_view_text));
        emptyViewOnClickListener();
        adjustPaddingToKeyboard();
        bindSearchView();
        observeSearchText();
        observeIsLoading();
        debouncer = new app.superhero.src.utils.Debouncer(500, TimeUnit.MILLISECONDS,
                message -> superheroListViewModel.postSearch(message)
        );
    }

    private void emptyViewOnClickListener() {
        emptyView.setOnClickListenerToEmptyView(view -> {
            if (getActivity() != null) {
                hideKeyboard(getActivity());
            }
            searchBar.clearEditTextFocus();
        });
    }

    private void adjustPaddingToKeyboard() {
        toolbarLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                emptyView.setPaddingBottom(toolbarLayout.getMeasuredHeight());
                loadingView.setPaddingBottom(toolbarLayout.getMeasuredHeight());
                toolbarLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
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
            if (isLoading && adapter.getItemCount() == 0) {
                loadingView.setVisibility(View.VISIBLE);
            } else {
                loadingView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    @UiThread
    protected void refreshAdapter(List<SuperheroMasterData> superheroList) {
        if (superheroList != null) {
            emptyView.setVisibility(View.GONE);
            adapter.setData(superheroList);
        }
        if (superheroList != null && firstStart) {
            loadingView.setVisibility(View.GONE);
        }
        if(superheroList.isEmpty()) {
            emptyView.setText(getResources().getString(R.string.empty_view_error_text));
            emptyView.setVisibility(View.VISIBLE);
        }
    }
}
