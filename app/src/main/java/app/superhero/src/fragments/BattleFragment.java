package app.superhero.src.fragments;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
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
import app.superhero.src.dao.Powerstats;
import app.superhero.src.dao.SuperheroMasterData;
import app.superhero.src.interfaces.ItemClickListener;
import app.superhero.src.interfaces.StarClickCallback;
import app.superhero.src.utils.RecyclerViewEmptySupport;
import app.superhero.src.viewmodels.BattleViewModel;
import app.superhero.src.views.ButtonView2;
import app.superhero.src.views.EmptyView;
import app.superhero.src.views.SuperheroCardView;

@EFragment(R.layout.fragment_battle)
public class BattleFragment extends BaseFragment implements ItemClickListener {
    @Bean
    BattleViewModel viewModel;

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

    @ViewById
    EmptyView emptyView;

    @ViewById
    LottieAnimationView animation;

    @ViewById
    ProgressBar battleProgress;

    private SuperheroesAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private StarClickCallback starClickCallback;
    private float firstHeroMultiplier;
    private float secondHeroMultiplier;

    @AfterViews
    void init() {
        customizeSuperheroesCardViews();
        startButton.setButtonLabel(getResources().getString(R.string.start_label));
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        adapter = new SuperheroesAdapter(new ArrayList<>(), this, starClickCallback, true, false, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        viewModel.fetchSuperheroes();
        observeSuperheroes();
        observePowerstats();
        observeFightingSuperheroes();
        observeHeroWithHp();

        startButton.setOnClickListener(view -> {
            setComponentsVisibilityOnStartButtonClick();
            viewModel.setDefender(viewModel.secondSuperhero.getValue().getId());
            new CountDownTimer(10000, 1000) {
                @Override
                public void onTick(long l) {
                    Random rand = new Random(System.currentTimeMillis());
                    float damage = rand.nextInt(20 - 0 + 1);
                    if(viewModel.defender.getValue() == viewModel.firstSuperhero.getValue().getId()) {
                        damage = damage * firstHeroMultiplier;
                    } else {
                        damage = damage * secondHeroMultiplier;
                    }
                    viewModel.refreshHp(viewModel.defender.getValue(), (int) damage);
                    Log.d("TAG", "VALAMI damage " + damage);
                    Log.d("TAG", "VALAMI id " + viewModel.defender.getValue());
                    Log.d("TAG", "VALAMI hp " + viewModel.heroWithHp.getValue().get(viewModel.defender.getValue()));
                    if (viewModel.defender.getValue() == viewModel.firstSuperhero.getValue().getId()) {
                        viewModel.setDefender(viewModel.secondSuperhero.getValue().getId());
                    } else {
                        viewModel.setDefender(viewModel.firstSuperhero.getValue().getId());
                    }
                }

                @Override
                public void onFinish() {
                    animation.cancelAnimation();
                }
            }.start();
        });
    }

    private void observeHeroWithHp() {
        viewModel.heroWithHp.observe(this, heroWithHp -> {
            if (viewModel.firstSuperhero.getValue() != null && viewModel.secondSuperhero.getValue() != null) {
                //leftHeroHp.setText((heroWithHp.get(viewModel.firstSuperhero.getValue().getId())).toString());
                //rightHeroHp.setText((heroWithHp.get(viewModel.secondSuperhero.getValue().getId())).toString());
            }
        });
    }

    private float calculateBattleMultiplier(Powerstats powerstats) {
        return calculateOverallStat(powerstats) / 100 + 1;
    }

    private void setComponentsVisibilityOnStartButtonClick() {
        startButton.setEnabled(false);
        recyclerView.setVisibility(View.GONE);
        animation.setVisibility(View.VISIBLE);
        animation.playAnimation();
        battleProgress.setVisibility(View.VISIBLE);
    }

    private void observePowerstats() {
        viewModel.firstHeroPowerstats.observe(this, powerstats -> {
            if (powerstats != null) {
                setChart(powerstats, leftChart);
                firstHeroMultiplier = calculateBattleMultiplier(powerstats);
            }
        });
        viewModel.secondHeroPowerstats.observe(this, powerstats -> {
            if (powerstats != null) {
                setChart(powerstats, rightChart);
                secondHeroMultiplier = calculateBattleMultiplier(powerstats);
            }
        });
    }

    @Override
    public void onItemClick(SuperheroMasterData superheroDto) {

    }

    private void observeSuperheroes() {
        viewModel.superheroes.observe(this, superheroes -> {
            if (superheroes.size() < 2) {
                showEmptyView();
            } else {
                refreshAdapter(superheroes);
                getRandomSuperheroes(superheroes);
                leftSuperhero.setVisibility(View.VISIBLE);
                rightSuperhero.setVisibility(View.VISIBLE);
                leftChart.setVisibility(View.VISIBLE);
                rightChart.setVisibility(View.VISIBLE);
                battleText.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                startButton.setVisibility(View.VISIBLE);
            }
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void showEmptyView() {
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setText(getResources().getString(R.string.empty_view_battle_screen));
        emptyView.setEmptyViewImage(getResources().getDrawable(R.drawable.ic_undraw_be_the_hero_ssr2));
        leftSuperhero.setVisibility(View.GONE);
        rightSuperhero.setVisibility(View.GONE);
        leftChart.setVisibility(View.GONE);
        rightChart.setVisibility(View.GONE);
        battleText.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        startButton.setVisibility(View.GONE);
    }

    private void observeFightingSuperheroes() {
        viewModel.firstSuperhero.observe(this, superhero -> {
            leftSuperhero.bind(superhero);
            viewModel.getFirstPowerstats(superhero.getId());
        });

        viewModel.secondSuperhero.observe(this, superhero -> {
            rightSuperhero.bind(superhero);
            viewModel.getSecondPowerstats(superhero.getId());
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
        viewModel.getFirstPowerstats(firstSuperhero.getId());
        viewModel.getSecondPowerstats(secondSuperhero.getId());
    }

    private float calculateOverallStat(Powerstats powerstats) {
        return (float) (powerstats.getIntelligence() + powerstats.getCombat() + powerstats.getDurability()
                + powerstats.getPower() + powerstats.getSpeed() + powerstats.getStrength()) / 6;
    }

    private void setChart(Powerstats powerstats, PercentageChartView chart) {
        chart.setProgress(calculateOverallStat(powerstats), true);
    }
}
