package app.superhero.src.fragments;

import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import app.superhero.R;
import app.superhero.src.viewmodels.PowerstatsViewModel;

@EFragment(R.layout.fragment_powerstats)
public class PowerstatsFragment extends BaseFragment {

    @FragmentArg
    int superHeroId;

    @Bean
    PowerstatsViewModel viewModel;

    @ViewById
    TextView intelligence;

    @ViewById
    TextView strength;

    @ViewById
    TextView speed;

    @ViewById
    TextView durability;

    @ViewById
    TextView power;

    @ViewById
    TextView combat;


    @AfterViews
    public void init(){
        observePowerstats();
        viewModel.getPowerstats(superHeroId);
    }

    private void observePowerstats() {
        viewModel.powerstats.observe(this, powerstats -> {
            intelligence.setText(Integer.toString(powerstats.getIntelligence()));
            strength.setText(Integer.toString(powerstats.getStrength()));
            speed.setText(Integer.toString(powerstats.getSpeed()));
            durability.setText(Integer.toString(powerstats.getDurability()));
            power.setText(Integer.toString(powerstats.getPower()));
            combat.setText(Integer.toString(powerstats.getCombat()));
        });
    }
}
