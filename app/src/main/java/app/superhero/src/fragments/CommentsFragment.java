package app.superhero.src.fragments;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import app.superhero.R;
import app.superhero.src.viewmodels.CommentsViewModel;

@EFragment(R.layout.fragment_comments)
public class CommentsFragment extends BaseFragment {

    @Bean
    CommentsViewModel viewModel;

    @FragmentArg
    int superheroId;

    @ViewById
    Button button;

    @ViewById
    EditText comment;

    @AfterViews
    public void init() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setComment(superheroId, comment.getText().toString());
            }
        });
    }

    private void ObserveComments() {
        viewModel.comments.observe(this, comment -> {
        });
    }
}
