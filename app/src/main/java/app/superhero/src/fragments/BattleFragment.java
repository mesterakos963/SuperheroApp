package app.superhero.src.fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
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
import app.superhero.src.interfaces.ItemClickListener;
import app.superhero.src.interfaces.StarClickCallback;
import app.superhero.src.model.dao.Powerstats;
import app.superhero.src.model.dao.SuperheroMasterData;
import app.superhero.src.viewmodels.BattleViewModel;
import app.superhero.src.views.ButtonView2;
import app.superhero.src.views.EmptyView;
import app.superhero.src.views.LoadingView;
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
    RecyclerView recyclerView;

    @ViewById
    ButtonView2 startButton;

    @ViewById
    EmptyView emptyView;

    @ViewById
    LottieAnimationView animation;

    @ViewById
    ProgressBar battleProgress;

    @ViewById
    LoadingView battleFragmentLoading;

    private SuperheroesAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private StarClickCallback starClickCallback;
    private CountDownTimer countDownTimer;
    private final Handler handler = new Handler();
    private float firstHeroMultiplier;
    private float secondHeroMultiplier;
    private final int PROGRESS = 100;
    private final int PROGRESS_DECREASE = 10;
    private final int MILLIS_IN_FUTURE = 10000;
    private final int COUNT_DOWN_INTERVAL = 1000;
    private final float ANIMATION_SPEED = 1.5f;
    private final int HANDLER_DELAY = 500;
    private int firstHeroHp;
    private int secondHeroHp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        observeLiveData();
    }

    @AfterViews
    void init() {
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        adapter = new SuperheroesAdapter(new ArrayList<>(), this, starClickCallback, true, false, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        viewModel.fetchSuperheroes();
        clearBattleState();
    }

    private void clearBattleState() {
        startButton.setButtonLabel(getResources().getString(R.string.start_label));
        customizeSuperheroesCardViews();
        leftSuperhero.hideHpComponents();
        rightSuperhero.hideHpComponents();
        startButton.setOnClickListener(view -> {
            setComponentsVisibilityOnStartButtonClick();
            animation.setSpeed(ANIMATION_SPEED);
            viewModel.setDefender(viewModel.secondSuperhero.getValue().getId());
            startBattle();
        });
    }

    private void startBattle() {
        handler.postDelayed(() -> countDownTimer = new CountDownTimer(MILLIS_IN_FUTURE, COUNT_DOWN_INTERVAL) {
            int progress = PROGRESS;

            @Override
            public void onTick(long l) {
                progress -= PROGRESS_DECREASE;
                setProgressBar(progress);
                setDamage(progress);
            }

            @Override
            public void onFinish() {
                animation.cancelAnimation();
            }
        }.start(), HANDLER_DELAY);
    }

    private void setWinner(SuperheroCardView superhero) {
        if (superhero == leftSuperhero) {
            leftSuperhero.setResult(getString(R.string.winner_label), ContextCompat.getColor(requireContext(), R.color.green));
            rightSuperhero.setResult(getString(R.string.loser_label), ContextCompat.getColor(requireContext(), R.color.red));
        } else {
            rightSuperhero.setResult(getString(R.string.winner_label), ContextCompat.getColor(requireContext(), R.color.green));
            leftSuperhero.setResult(getString(R.string.loser_label), ContextCompat.getColor(requireContext(), R.color.red));
        }
    }

    private void setDamage(int progress) {
        Random rand = new Random(System.currentTimeMillis());
        float damage;

        if (progress == 0) {
            if (firstHeroHp != 0 && secondHeroHp != 0) {
                if (firstHeroHp > secondHeroHp) {
                    damage = secondHeroHp;
                    viewModel.refreshHp(viewModel.secondSuperhero.getValue().getId(), (int) damage);
                    finishBattle(true);
                } else {
                    damage = firstHeroHp;
                    viewModel.refreshHp(viewModel.firstSuperhero.getValue().getId(), (int) damage);
                    finishBattle(false);
                }
            }
            cancelTimerTask();
        } else {
            damage = rand.nextInt(20 + 1);
            if (viewModel.defender.getValue() == viewModel.firstSuperhero.getValue().getId()) {
                damage *= firstHeroMultiplier;
            } else {
                damage *= secondHeroMultiplier;
            }
            viewModel.refreshHp(viewModel.defender.getValue(), (int) damage);
            if (viewModel.defender.getValue() == viewModel.firstSuperhero.getValue().getId()) {
                viewModel.setDefender(viewModel.secondSuperhero.getValue().getId());
            } else {
                viewModel.setDefender(viewModel.firstSuperhero.getValue().getId());
            }
        }
    }

    private void finishBattle(boolean isFirstHasWon) {
        cancelTimerTask();
        handler.postDelayed(() -> {
            setWinner(isFirstHasWon ? leftSuperhero : rightSuperhero);
            setRestartButton();
        }, HANDLER_DELAY);
    }

    private void setRestartButton() {
        startButton.setEnabled(true);
        startButton.setButtonLabel(getString(R.string.restart_label));
        startButton.setOnClickListener(view -> {
            leftSuperhero.hideResult();
            rightSuperhero.hideResult();
            animation.setVisibility(View.GONE);
            battleProgress.setVisibility(View.GONE);
            leftSuperhero.setHpBar(firstHeroHp);
            rightSuperhero.setHpBar(secondHeroHp);
            leftSuperhero.reInitCardView();
            rightSuperhero.reInitCardView();
            init();
        });
    }

    private void cancelTimerTask() {
        setProgressBar(0);
        countDownTimer.cancel();
        animation.cancelAnimation();
    }

    private void setProgressBar(int progress) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            battleProgress.setProgress(progress, true);
        } else {
            battleProgress.setProgress(progress);
        }
    }

    private void observeLiveData() {
        observeSuperheroes();
        observePowerstats();
        observeFightingSuperheroes();
        observeHeroWithHp();
        observeLoading();
    }

    private void observeLoading() {
        viewModel.isLoading.observe(this, isLoading -> battleFragmentLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE));
    }

    private void observeHeroWithHp() {
        viewModel.heroWithHp.observe(this, heroWithHp -> {
            if (viewModel.firstSuperhero.getValue() != null
                    && viewModel.secondSuperhero.getValue() != null
                    && getContext() != null
            ) {
                leftSuperhero.setHpText(
                        getContext().getString(
                                R.string.hp_text,
                                heroWithHp.get(viewModel.firstSuperhero.getValue().getId()).toString()
                        )
                );
                firstHeroHp = viewModel.heroWithHp.getValue().get(viewModel.firstSuperhero.getValue().getId());
                rightSuperhero.setHpText(
                        getContext().getString(
                                R.string.hp_text,
                                heroWithHp.get(viewModel.secondSuperhero.getValue().getId()).toString()
                        )
                );
                secondHeroHp = viewModel.heroWithHp.getValue().get(viewModel.secondSuperhero.getValue().getId());
                leftSuperhero.setHpBar(firstHeroHp);
                rightSuperhero.setHpBar(secondHeroHp);
                if (firstHeroHp == 0) {
                    finishBattle(false);
                }
                if (secondHeroHp == 0) {
                    finishBattle(true);
                }
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
        leftSuperhero.showHpComponents();
        rightSuperhero.showHpComponents();
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
                emptyView.setVisibility(View.GONE);
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

    @Override
    public void onPause() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        super.onPause();
    }
}
