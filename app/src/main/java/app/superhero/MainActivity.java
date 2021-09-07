package app.superhero;

import android.graphics.Rect;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
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
        mainActivityRoot.getViewTreeObserver().addOnGlobalLayoutListener(
                () -> {
                    Rect r = new Rect();
                    mainActivityRoot.getWindowVisibleDisplayFrame(r);
                    int screenHeight = mainActivityRoot.getRootView().getHeight();

                    int keypadHeight = screenHeight - r.bottom;

                    if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                        // keyboard is opened
                        if (!isKeyboardShowing) {
                            isKeyboardShowing = true;
                            onKeyboardVisibilityChanged(true);
                        }
                    } else {
                        // keyboard is closed
                        if (isKeyboardShowing) {
                            isKeyboardShowing = false;
                            onKeyboardVisibilityChanged(false);
                        }
                    }
                });
    }
}
