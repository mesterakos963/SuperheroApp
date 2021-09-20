package app.superhero.src.fragments;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

import app.superhero.R;

@EFragment(R.layout.fragment_characteristics)
public class CharacteristicsFragment extends BaseFragment{

    @FragmentArg
    int superHeroId;

}
