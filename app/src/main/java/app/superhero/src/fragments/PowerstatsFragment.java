package app.superhero.src.fragments;

import android.graphics.Color;
import android.graphics.Typeface;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
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

    @ViewById
    HorizontalBarChart barChart;
    List<String> xAxisLabels;

    @AfterViews
    public void init() {
        observePowerstats();
        viewModel.getPowerstats(superheroMasterData.getId());
    }

    private void observePowerstats() {
        viewModel.powerstats.observe(this, powerstats -> {
            setRadarChart(powerstats);
            setHorizontalBarChart(powerstats);
        });
    }

    private void setHorizontalBarChart(Powerstats powerstats) {
        Description description = barChart.getDescription();
        List<BarEntry> values = new ArrayList<>();

        values.add(new BarEntry(0, powerstats.getIntelligence()));
        values.add(new BarEntry(1, powerstats.getStrength()));
        values.add(new BarEntry(2, powerstats.getSpeed()));
        values.add(new BarEntry(3, powerstats.getDurability()));
        values.add(new BarEntry(4, powerstats.getPower()));
        values.add(new BarEntry(5, powerstats.getCombat()));

        BarDataSet set = new BarDataSet(values, "Powerstats");

        List<IBarDataSet> sets = new ArrayList<>();
        sets.add(set);

        BarData data = new BarData(sets);
        data.setValueTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(R.color.chart_text_color);
        description.setEnabled(false);

        getXAxisValues();
        barChart.setDrawGridBackground(false);
        barChart.setDragEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setScaleEnabled(false);
        barChart.isDoubleTapToZoomEnabled();
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(false);
        barChart.getDescription().setEnabled(false);
        barChart.setDrawGridBackground(false);
        set.setColor(getContext().getResources().getColor(R.color.bar_chart_color));

        barChart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return xAxisLabels.get((int)value);
            }

            @Override
            public String getBarLabel(BarEntry barEntry) {
                return super.getBarLabel(barEntry);
            }
        });


        barChart.setData(data);
        barChart.invalidate();
    }

    private List<String> getXAxisValues() {
        xAxisLabels = new ArrayList<>();
        xAxisLabels.add(getContext().getString(R.string.Intelligence));
        xAxisLabels.add(getContext().getString(R.string.Strength));
        xAxisLabels.add(getContext().getString(R.string.Speed));
        xAxisLabels.add(getContext().getString(R.string.Durability));
        xAxisLabels.add(getContext().getString(R.string.Power));
        xAxisLabels.add(getContext().getString(R.string.Combat));
        return xAxisLabels;
    }

    private void setRadarChart(Powerstats powerstats) {

        ArrayList<RadarEntry> entries = new ArrayList<>();

        entries.add(new RadarEntry(powerstats.getIntelligence()));
        entries.add(new RadarEntry(powerstats.getStrength()));
        entries.add(new RadarEntry(powerstats.getSpeed()));
        entries.add(new RadarEntry(powerstats.getDurability()));
        entries.add(new RadarEntry(powerstats.getPower()));
        entries.add(new RadarEntry(powerstats.getCombat()));

        RadarDataSet set = new RadarDataSet(entries, "Powerstats");
        set.setColor(Color.rgb(255, 187, 59));
        set.setFillColor(Color.rgb(255, 187, 59));
        set.setDrawFilled(true);
        set.setFillAlpha(180);
        set.setLineWidth(2f);
        set.setDrawHighlightCircleEnabled(true);
        set.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<>();
        sets.add(set);

        RadarData data = new RadarData(sets);
        data.setValueTypeface(Typeface.SANS_SERIF);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(R.color.chart_text_color);

        radarChart.setData(data);
        radarChart.invalidate();
    }
}
