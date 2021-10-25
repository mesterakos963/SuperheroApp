package app.superhero;

import static android.view.View.GONE;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import app.superhero.src.fragments.BattleFragment;
import app.superhero.src.fragments.BattleFragment_;
import app.superhero.src.fragments.FavouritesListFragment;
import app.superhero.src.fragments.FavouritesListFragment_;
import app.superhero.src.fragments.SuperheroListFragment;
import app.superhero.src.fragments.SuperheroListFragment_;

@EActivity(R.layout.activity_main)
public class MainActivity extends FragmentActivity {
    @ViewById
    ConstraintLayout mainActivityRoot;

    @ViewById
    BottomNavigationView bottomNavigationView;

    boolean isKeyboardShowing = false;

    private SuperheroListFragment superheroListFragment;
    private FavouritesListFragment favouritesListFragment;
    private BattleFragment battleFragment;
    private List<Fragment> fragments;

    private final String TAG_SUPERHERO_LIST_FRAGMENT = "superhero_list_fragment";
    private final String TAG_FAVOURITES_LIST_FRAGMENT = "favourites_list_fragment";
    private final String TAG_BATTLE_FRAGMENT = "battle_fragment";
    private final String KEY_SELECTED_INDEX = "key_selected_index";
    private int selectedIndex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            superheroListFragment = SuperheroListFragment_.builder().build();
            favouritesListFragment = FavouritesListFragment_.builder().build();
            battleFragment = BattleFragment_.builder().build();
            fragments = new ArrayList<>();
            fragments.add(superheroListFragment);
            fragments.add(favouritesListFragment);
            fragments.add(battleFragment);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.navHostFragment, superheroListFragment, TAG_SUPERHERO_LIST_FRAGMENT)
                    .add(R.id.navHostFragment, favouritesListFragment, TAG_FAVOURITES_LIST_FRAGMENT)
                    .add(R.id.navHostFragment, battleFragment, TAG_BATTLE_FRAGMENT)
                    .commit();
        } else {
            superheroListFragment = (SuperheroListFragment) getSupportFragmentManager().findFragmentByTag(
                    TAG_SUPERHERO_LIST_FRAGMENT
            );
            favouritesListFragment = (FavouritesListFragment) getSupportFragmentManager().findFragmentByTag(
                    TAG_FAVOURITES_LIST_FRAGMENT
            );
            battleFragment = (BattleFragment) getSupportFragmentManager().findFragmentByTag(
                    TAG_BATTLE_FRAGMENT
            );

            selectedIndex = savedInstanceState.getInt(KEY_SELECTED_INDEX, 0);
        }

        selectFragment(getSelectedFragment());
    }


    @AfterViews
    public void init() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.nav_list: {
                    fragment = superheroListFragment;
                    break;
                }
                case R.id.nav_favourites: {
                    fragment = favouritesListFragment;
                    break;
                }
                case R.id.nav_battle: {
                    fragment = battleFragment;
                    break;
                }
                default:
                    throw new IllegalArgumentException("Unexpected itemId");
            }

            selectFragment(fragment);

            return true;
        });

        mainActivityRoot.getViewTreeObserver().addOnGlobalLayoutListener(
                () -> {
                    Rect r = new Rect();
                    mainActivityRoot.getWindowVisibleDisplayFrame(r);
                    int screenHeight = mainActivityRoot.getRootView().getHeight();

                    int keypadHeight = screenHeight - r.bottom;

                    if (keypadHeight > screenHeight * 0.15) {
                        if (!isKeyboardShowing) {
                            isKeyboardShowing = true;
                            onKeyboardVisibilityChanged(true);
                        }
                    } else {
                        if (isKeyboardShowing) {
                            isKeyboardShowing = false;
                            onKeyboardVisibilityChanged(false);
                        }
                    }
                });
    }

    private Fragment getSelectedFragment() {
        return fragments.get(selectedIndex);
    }

    private void selectFragment(Fragment selectedFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < fragments.size(); ++i) {
            if (selectedFragment == fragments.get(i)) {
                transaction = transaction.attach(fragments.get(i));
                selectedIndex = i;
            } else {
                transaction = transaction.detach(fragments.get(i));
            }
        }
        transaction.commit();
    }


    @UiThread
    public void hideKeyboard(View view) {
        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        if (view != null) {
            view.clearFocus();
        }
    }

    @Override
    public void onBackPressed() {
        switch (selectedIndex) {
            case 0: {
                bottomNavigationView.setSelectedItemId(R.id.nav_list);
                break;
            }
            case 1: {
                bottomNavigationView.setSelectedItemId(R.id.nav_favourites);
                break;
            }
            case 2: {
                bottomNavigationView.setSelectedItemId(R.id.nav_battle);
            }
        }
        super.onBackPressed();
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SELECTED_INDEX, selectedIndex);
    }

    void onKeyboardVisibilityChanged(boolean opened) {
        if (opened) {
            bottomNavigationView.setVisibility(GONE);
        } else {
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
    }


    public void setBottomNavigationViewVisibile(boolean visibile) {
        if (bottomNavigationView != null) {
            bottomNavigationView.setVisibility(visibile ? View.VISIBLE : View.GONE);
        }
    }
}
