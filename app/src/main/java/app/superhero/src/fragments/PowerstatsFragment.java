package app.superhero.src.fragments;

import android.graphics.Color;
import android.graphics.Typeface;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import app.superhero.R;
import app.superhero.src.dao.Powerstats;
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

    List<RadarEntry> entries = new ArrayList<>();

    @AfterViews
    public void init() {
        observePowerstats();
        viewModel.getPowerstats(superheroMasterData.getId());
    }

    private void observePowerstats() {
        viewModel.powerstats.observe(this, powerstats -> {
            /*intelligence.setText(Integer.toString(powerstats.getIntelligence()));
            strength.setText(Integer.toString(powerstats.getStrength()));
            speed.setText(Integer.toString(powerstats.getSpeed()));
            durability.setText(Integer.toString(powerstats.getDurability()));
            power.setText(Integer.toString(powerstats.getPower()));
            combat.setText(Integer.toString(powerstats.getCombat()));*/
            setData(powerstats);
        });
    }

    private void setData(Powerstats powerstats) {
        ArrayList<RadarEntry> entries1 = new ArrayList<>();

        entries1.add(new RadarEntry(powerstats.getIntelligence()));
        entries1.add(new RadarEntry(powerstats.getStrength()));
        entries1.add(new RadarEntry(powerstats.getSpeed()));
        entries1.add(new RadarEntry(powerstats.getDurability()));
        entries1.add(new RadarEntry(powerstats.getPower()));
        entries1.add(new RadarEntry(powerstats.getCombat()));

        RadarDataSet set1 = new RadarDataSet(entries1, "Powerstats");
        set1.setColor(Color.rgb(255, 187, 59));
        set1.setFillColor(Color.rgb(255, 187, 59));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<>();
        sets.add(set1);

        RadarData data = new RadarData(sets);
        data.setValueTypeface(Typeface.SANS_SERIF);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        radarChart.setData(data);
        radarChart.invalidate();
    }

}
