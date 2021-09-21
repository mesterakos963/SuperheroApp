package app.superhero.src.fragments;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

import app.superhero.R;
import app.superhero.src.viewmodels.CharacteristicsViewModel;

@EFragment(R.layout.fragment_characteristics)
public class CharacteristicsFragment extends BaseFragment{

    @FragmentArg
    int superHeroId;

    @Bean
    CharacteristicsViewModel viewModel;

    @AfterViews
    protected void init() {
        viewModel.getCharacteristics(superHeroId);
    }

}
