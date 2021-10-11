package app.superhero;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends FragmentActivity {

    boolean isKeyboardShowing = false;

    @ViewById
    ConstraintLayout mainActivityRoot;


    @ViewById
    BottomNavigationView bottomNavigationView;

    void onKeyboardVisibilityChanged(boolean opened) {
        if (opened) {
            bottomNavigationView.setVisibility(View.GONE);
        } else {
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
    }

    @AfterViews
    public void init() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());
        bottomNavigationView.setOnItemReselectedListener((BottomNavigationView.OnNavigationItemReselectedListener) item -> {
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
}
