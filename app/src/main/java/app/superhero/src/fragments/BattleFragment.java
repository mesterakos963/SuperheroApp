package app.superhero.src.fragments;

import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ramijemli.percentagechartview.PercentageChartView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import app.superhero.R;
import app.superhero.src.api.SuperheroesAdapter;
import app.superhero.src.dao.SuperheroMasterData;
import app.superhero.src.interfaces.ItemClickListener;
import app.superhero.src.interfaces.StarClickCallback;
import app.superhero.src.utils.RecyclerViewEmptySupport;
import app.superhero.src.viewmodels.SuperheroListViewModel;
import app.superhero.src.views.ButtonView2;
import app.superhero.src.views.SuperheroCardView;

@EFragment(R.layout.fragment_battle)
public class BattleFragment extends BaseFragment implements ItemClickListener {
    @Bean
    SuperheroListViewModel viewModel;

    @ViewById
    ConstraintLayout battleRoot;

    @ViewById
    SuperheroCardView leftSuperhero;

    @ViewById
    SuperheroCardView rightSuperhero;

    @ViewById
    PercentageChartView leftChart;

    @ViewById
    PercentageChartView rightChart;

    @ViewById
    TextView battleText;

    @ViewById
    RecyclerViewEmptySupport recyclerView;

    @ViewById
    ButtonView2 startButton;

    private SuperheroesAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private StarClickCallback starClickCallback;

    @AfterViews
    void init() {
        customizeSuperheroesCardViews();
        startButton.setButtonLabel(getResources().getString(R.string.start_label));
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        adapter = new SuperheroesAdapter(new ArrayList<>(), this, starClickCallback, true, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        viewModel.fetchFavourites();
        observeSuperheroes();
        observeFirstSuperhero();
        observeSecondSuperheroes();
    }

    @Override
    public void onItemClick(SuperheroMasterData superheroDto) {

    }

    private void observeSuperheroes() {
        viewModel.favourites.observe(this, superheroes -> {
            refreshAdapter(superheroes);
            getRandomSuperheroes(superheroes);
        });
    }

    private void observeFirstSuperhero() {
        viewModel.firstSuperhero.observe(this, superhero -> {
            leftSuperhero.bind(superhero);
        });
    }

    private void observeSecondSuperheroes() {
        viewModel.secondSuperhero.observe(this, superhero -> {
            rightSuperhero.bind(superhero);
        });
    }

    private void refreshAdapter(List<SuperheroMasterData> superheroList) {
        adapter.setData(superheroList);
    }

    private void customizeSuperheroesCardViews() {
        rightSuperhero.hideStar();
        leftSuperhero.hideStar();
        rightSuperhero.hideBackground();
        leftSuperhero.hideBackground();
    }

    private void getRandomSuperheroes(List<SuperheroMasterData> superheroes) {
        Random rand = new Random(System.currentTimeMillis());
        SuperheroMasterData firstSuperhero = superheroes.get(rand.nextInt(superheroes.size()));
        SuperheroMasterData secondSuperhero;
        viewModel.setFirstSuperhero(firstSuperhero);
        do {
            secondSuperhero = superheroes.get(rand.nextInt(superheroes.size()));
        } while (firstSuperhero.equals(secondSuperhero));
        viewModel.setSecondSuperhero(secondSuperhero);
    }


}
