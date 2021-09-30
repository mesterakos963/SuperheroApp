package app.superhero.src.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import app.superhero.R;
import app.superhero.src.dao.SuperheroMasterData;
import app.superhero.src.utils.OnFocusEvent;
import app.superhero.src.utils.ViewPagerAdapter;
import app.superhero.src.viewmodels.SuperheroDetailsViewModel;
import app.superhero.src.views.ButtonView;

import static app.superhero.src.utils.ViewPagerAdapter.CHARACTERISTICS_POSITION;
import static app.superhero.src.utils.ViewPagerAdapter.NUM_PAGES;

@EFragment(R.layout.fragment_superhero_details)
public class SuperheroDetailsFragment extends BaseFragment {

    @ViewsById({R.id.powerstatsButton, R.id.characteristicsButton, R.id.commentsButton})

    protected List<ButtonView> buttons;
    ViewPagerAdapter adapter;

    @ViewById
    ViewPager2 viewPager;

    @ViewById
    ImageView backButton;

    @ViewById
    ImageView profileImage;

    @ViewById
    TextView superheroNameText;

    @ViewById
    ImageView star;

    @ViewById
    MotionLayout motionLayout;

    @Bean
    SuperheroDetailsViewModel viewModel;

    ViewPager2.OnPageChangeCallback pageChangeCallback;
    int currentPage;
    private boolean measured;
    private SuperheroMasterData superhero;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            superhero = SuperheroDetailsFragment_Args.fromBundle(getArguments()).getSuperheroMasterData();
        }
    }

    @AfterViews
    public void init() {
        EventBus.getDefault().register(this);
        star.setOnClickListener(view -> superhero.setFavourite(true));
        backButton.setOnClickListener(view -> {
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }
        });
        if (viewModel.selectedPage.getValue() == null || viewModel.selectedPage.getValue() == 0) {
            currentPage = 0;
        } else {
            currentPage = viewModel.selectedPage.getValue();
        }
        bindButtons();
        adapter = new ViewPagerAdapter(getActivity(), superhero);
        viewPager.setOffscreenPageLimit(NUM_PAGES);
        viewPager.setAdapter(adapter);
        observeSelectedPage();
        viewPager.setCurrentItem(currentPage);
        selectButtonByPosition(currentPage);
        pageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                selectButtonByPosition(position);
                if (adapter.getFragments().size() > position && !measured) {
                    View view = adapter.getFragments().get(CHARACTERISTICS_POSITION).getView();
                    measureViewPager(view);
                    measured = true;
                }
            }
        };
        viewPager.registerOnPageChangeCallback(pageChangeCallback);
        observeSuperheroMasterData();
        if (viewModel.superheroMasterData.getValue() == null) {
            viewModel.setSuperheroMasterData(superhero);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(OnFocusEvent event) {
        Log.d("VALAMI", "VALAMI " + motionLayout.getProgress());
        if(motionLayout.getProgress() < 1.0) {
            motionLayout.transitionToEnd();
        }
    }

    private void loadImage(String profileImageUrl) {
        Glide.with(profileImage.getContext())
                .load(profileImageUrl)
                .into(profileImage);
    }

    private void observeSelectedPage() {
        viewModel.selectedPage.observe(this, selectedPage -> {
            viewPager.setCurrentItem(selectedPage);
        });
    }

    private void observeSuperheroMasterData() {
        viewModel.superheroMasterData.observe(this, superheroMasterData -> {
            superheroNameText.setText(superheroMasterData.getName());
            loadImage(superheroMasterData.getUrl());
            star.setSelected(superheroMasterData.getIsFavourite());
        });
    }

    private void bindButtons() {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).bind((buttonId, isSelected) -> {
                selectButtonByViewId(buttonId);
            });
        }
    }

    private void selectButtonByViewId(int buttonId) {
        for (int j = 0; j < buttons.size(); j++) {
            if (buttons.get(j).getId() == buttonId) {
                viewModel.setSelectedPage(j);
            }
            buttons.get(j).setButtonSelected(buttons.get(j).getId() == buttonId);
        }
    }

    private void selectButtonByPosition(int position) {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setSelected(i == position);
        }
    }

    public void measureViewPager(View view) {
        int wMeasureSpec = View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY);
        int hMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(wMeasureSpec, hMeasureSpec);

        ViewGroup.LayoutParams params = viewPager.getLayoutParams();
        params.height = view.getMeasuredHeight();
        viewPager.setLayoutParams(params);
        viewPager.requestLayout();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        viewPager.registerOnPageChangeCallback(pageChangeCallback);
    }
}
