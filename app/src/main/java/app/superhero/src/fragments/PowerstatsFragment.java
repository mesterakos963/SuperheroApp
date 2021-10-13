package app.superhero.src.fragments;

import android.graphics.Color;
import android.graphics.Typeface;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.ramijemli.percentagechartview.PercentageChartView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import app.superhero.R;
import app.superhero.src.dao.Powerstats;
import app.superhero.src.dao.SuperheroMasterData;
import app.superhero.src.viewmodels.PowerstatsViewModel;
import app.superhero.src.views.MarkerView;

@EFragment(R.layout.fragment_powerstats)
public class PowerstatsFragment extends BaseFragment {
    @FragmentArg
    SuperheroMasterData superheroMasterData;

    @Bean
    PowerstatsViewModel viewModel;

    @ViewById
    RadarChart radarChart;

    @ViewById
    PercentageChartView intelligenceChart;

    @ViewById
    PercentageChartView strengthChart;

    @ViewById
    PercentageChartView speedChart;

    @ViewById
    PercentageChartView durabilityChart;

    @ViewById
    PercentageChartView powerChart;

    @ViewById
    PercentageChartView combatChart;

    @AfterViews
    public void init() {
        observePowerstats();
        viewModel.getPowerstats(superheroMasterData.getId());
    }

    private void observePowerstats() {
        viewModel.powerstats.observe(this, powerstats -> {
            if (powerstats != null) {
                setRadarChart(powerstats);
                setPercentageCharts(powerstats);
            }
        });
    }

    private void setRadarChart(Powerstats powerstats) {
        Typeface typeface = ResourcesCompat.getFont(requireContext(), R.font.gotham_medium);
        ArrayList<RadarEntry> entries = new ArrayList<>();
        Description description = radarChart.getDescription();
        MarkerView marker = new MarkerView(getContext(), R.layout.marker_view);
        radarChart.setMarker(marker);
        YAxis yAxis = radarChart.getYAxis();
        XAxis xAxis = radarChart.getXAxis();
        String[] labels = {getContext().getString(R.string.Intelligence),
                getContext().getString(R.string.Strength),
                getContext().getString(R.string.Speed),
                getContext().getString(R.string.Durability),
                getContext().getString(R.string.Power),
                getContext().getString(R.string.Combat)};

        yAxis.setAxisMinimum(0);
        yAxis.setAxisMaximum(90);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(90);
        yAxis.setTextColor(ContextCompat.getColor(getContext(), R.color.transparent));

        entries.add(new RadarEntry(powerstats.getIntelligence()));
        entries.add(new RadarEntry(powerstats.getStrength()));
        entries.add(new RadarEntry(powerstats.getSpeed()));
        entries.add(new RadarEntry(powerstats.getDurability()));
        entries.add(new RadarEntry(powerstats.getPower()));
        entries.add(new RadarEntry(powerstats.getCombat()));

        RadarDataSet set = new RadarDataSet(entries, null);
        set.setColor(Color.rgb(255, 187, 59));
        set.setFillColor(Color.rgb(255, 187, 59));
        set.setDrawFilled(true);
        set.setFillAlpha(130);
        set.setLineWidth(3f);
        set.setDrawHighlightCircleEnabled(true);
        set.setDrawHighlightIndicators(false);
        description.setEnabled(false);
        ArrayList<IRadarDataSet> sets = new ArrayList<>();
        sets.add(set);

        RadarData data = new RadarData(sets);
        data.setDrawValues(false);

        radarChart.setRotationEnabled(false);
        radarChart.getLegend().setEnabled(false);
        radarChart.setWebColor(ContextCompat.getColor(getContext(), R.color.chart_text_color));
        radarChart.setWebColorInner(ContextCompat.getColor(getContext(), R.color.chart_text_color));
        radarChart.setWebLineWidth(1f);
        radarChart.setWebLineWidthInner(1f);

        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setTypeface(typeface);
        xAxis.setTextSize(14f);
        xAxis.setTextColor(ContextCompat.getColor(getContext(), R.color.chart_text_color));

        radarChart.setData(data);
        radarChart.invalidate();
    }

    private void setPercentageCharts(Powerstats powerstats) {
        intelligenceChart.setProgress(powerstats.getIntelligence(), true);
        strengthChart.setProgress(powerstats.getStrength(), true);
        speedChart.setProgress(powerstats.getSpeed(), true);
        durabilityChart.setProgress(powerstats.getDurability(), true);
        powerChart.setProgress(powerstats.getPower(), true);
        combatChart.setProgress(powerstats.getCombat(), true);
    }
}

