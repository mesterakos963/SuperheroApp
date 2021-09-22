package app.superhero.src.fragments;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import app.superhero.R;
import app.superhero.src.viewmodels.CharacteristicsViewModel;
import app.superhero.src.views.DetailsItemView;

@EFragment(R.layout.fragment_characteristics)
public class CharacteristicsFragment extends BaseFragment {

    @FragmentArg
    int superHeroId;

    @ViewById
    DetailsItemView fullName;

    @ViewById
    DetailsItemView alterEgos;

    @ViewById
    DetailsItemView aliases;

    @ViewById
    DetailsItemView placeOfBirth;

    @ViewById
    DetailsItemView firstAppearance;

    @ViewById
    DetailsItemView publisher;

    @ViewById
    DetailsItemView alignment;

    @ViewById
    DetailsItemView gender;

    @ViewById
    DetailsItemView race;

    @ViewById
    DetailsItemView height;

    @ViewById
    DetailsItemView weight;

    @ViewById
    DetailsItemView eyeColor;

    @ViewById
    DetailsItemView hairColor;

    @ViewById
    DetailsItemView occupation;

    @ViewById
    DetailsItemView base;

    @ViewById
    DetailsItemView groupAffiliation;

    @ViewById
    DetailsItemView relatives;


    @Bean
    CharacteristicsViewModel viewModel;

    @AfterViews
    protected void init() {
        observeCharacteristics();
        viewModel.getCharacteristics(superHeroId);
    }

    public void observeCharacteristics() {
        viewModel.superhero.observe(this, superhero -> {
            fullName.setData(superhero.biography.getFullName());
            alterEgos.setData(superhero.biography.getAlterEgos());
            aliases.setData(superhero.biography.getAliases());
            placeOfBirth.setData(superhero.biography.getPlaceOfBirth());
            firstAppearance.setData(superhero.biography.getFirstAppearance());
            publisher.setData(superhero.biography.getPublisher());
            alignment.setData(superhero.biography.getAlignment());

            gender.setData(superhero.appearance.getGender());
            race.setData(superhero.appearance.getRace());
            height.setData(superhero.appearance.getHeight());
            weight.setData(superhero.appearance.getWeight());
            eyeColor.setData(superhero.appearance.getEyeColor());
            hairColor.setData(superhero.appearance.getHairColor());

            occupation.setData(superhero.work.getOccupation());
            base.setData(superhero.work.getBase());

            groupAffiliation.setData(superhero.connections.getGroupAffiliation());
            relatives.setData(superhero.connections.getRelatives());
        });
    }


}
