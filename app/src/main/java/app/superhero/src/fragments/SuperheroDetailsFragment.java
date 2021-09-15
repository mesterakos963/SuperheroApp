package app.superhero.src.fragments;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager2.widget.ViewPager2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

import java.util.List;

import app.superhero.R;
import app.superhero.src.utils.ViewPagerAdapter;
import app.superhero.src.viewmodels.SuperheroDetailsViewModel;
import app.superhero.src.views.ButtonView;

import static app.superhero.src.utils.ViewPagerAdapter.NUM_PAGES;

@EFragment(R.layout.fragment_superhero_details)
public class SuperheroDetailsFragment extends BaseFragment {

    ViewPagerAdapter adapter;

    @ViewById
    ViewPager2 viewPager;

    @Bean
    SuperheroDetailsViewModel viewModel;

    @ViewsById({R.id.powerstatsButton, R.id.characteristicsButton, R.id.commentsButton})
    protected List<ButtonView> buttons;

    @AfterViews
    public void init() {
        int currentPage = viewModel.getSelectedPageAsInt();
        bindButtons();
        adapter = new ViewPagerAdapter(getActivity());
        viewPager.setOffscreenPageLimit(NUM_PAGES);
        viewPager.setAdapter(adapter);
        observeSelectedPage();
        viewPager.setCurrentItem(currentPage);
        buttons.get(currentPage).setButtonSelected(true);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                selectButtonByPosition(position);
                View view = adapter.getFragments().get(position).getView();

                int wMeasureSpec = View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY);
                int hMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                view.measure(wMeasureSpec, hMeasureSpec);

                if (viewPager.getLayoutParams().height != view.getMeasuredHeight()) {
                    ViewGroup.LayoutParams params = (androidx.constraintlayout.widget.ConstraintLayout.LayoutParams) viewPager.getLayoutParams();
                    params.height = view.getMeasuredHeight();
                    viewPager.setLayoutParams(params);
                    viewPager.requestLayout();
                }

            }
        });

    }

    private void observeSelectedPage() {
        viewModel.getSelectedPage().observe(this, selectedPage -> {
            viewPager.setCurrentItem(selectedPage);
        });
    }

    private void bindButtons() {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).bind((buttonId, isSelected) -> {
                selectButtoByViewId(buttonId);
            });
        }
    }

    private void selectButtoByViewId(int buttonId) {
        for (int j = 0; j < buttons.size(); j++) {
            if (buttons.get(j).getId() == buttonId) {
                viewModel.setSelectedPage(j);
            }
            buttons.get(j).setButtonSelected(buttons.get(j).getId() == buttonId);
        }
    }

    private void selectButtonByPosition(int position) {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setSelected(i==position);
        }
    }
}
