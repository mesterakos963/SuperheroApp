package app.superhero.src.fragments;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewsById;

import java.util.List;

import app.superhero.R;
import app.superhero.src.viewmodels.SuperheroDetailsViewModel;
import app.superhero.src.views.ButtonView;

@EFragment(R.layout.fragment_superhero_details)
public class SuperheroDetailsFragment extends BaseFragment {

    @Bean
    SuperheroDetailsViewModel viewModel;

    @ViewsById({R.id.powerstatsButton, R.id.characteristicsButton, R.id.commentsButton})
    protected List<ButtonView> buttons;

    @AfterViews
    public void init() {
        bindButtons();
    }

    private void bindButtons() {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).bind((buttonId, isSelected) -> {
                for (int j = 0; j < buttons.size(); j++) {
                    if (buttons.get(j).getId() == buttonId) {
                        viewModel.setSelectedPage(j);
                    }
                    buttons.get(j).setButtonSelected(buttons.get(j).getId() == buttonId);
                }
            });
        }
    }
}
