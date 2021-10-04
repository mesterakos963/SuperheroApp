package app.superhero.src.fragments;

import com.github.mikephil.charting.charts.RadarChart;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import app.superhero.R;
import app.superhero.src.dao.SuperheroMasterData;
import app.superhero.src.viewmodels.PowerstatsViewModel;

@EFragment(R.layout.fragment_powerstats)
public class PowerstatsFragment extends BaseFragment {

    @FragmentArg
    SuperheroMasterData superheroMasterData;

    @Bean
    PowerstatsViewModel viewModel;

    @ViewById
    RadarChart radarChart;

    @AfterViews
    public void init() {
        observePowerstats();
        viewModel.getPowerstats(superheroMasterData.getId());
    }

    private void observePowerstats() {
        viewModel.powerstats.observe(this, powerstats -> {
        });
    }
}
