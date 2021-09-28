package app.superhero.src.fragments;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import app.superhero.R;
import app.superhero.src.dao.SuperheroMasterData;
import app.superhero.src.viewmodels.CommentsViewModel;

@EFragment(R.layout.fragment_comments)
public class CommentsFragment extends BaseFragment {

    @Bean
    CommentsViewModel viewModel;

    @FragmentArg
    SuperheroMasterData superheroMasterData;

    @ViewById
    Button button;

    @ViewById
    EditText comment;

    @ViewById
    TextView commentText;

    @AfterViews
    public void init() {
        viewModel.getComment(superheroMasterData.getId());
        observeComments();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setComment(superheroMasterData.getId(), comment.getText().toString());
            }
        });
    }

    private void observeComments() {
        viewModel.comments.observe(this, commentText -> {
            comment.setText(commentText);
        });
    }
}
