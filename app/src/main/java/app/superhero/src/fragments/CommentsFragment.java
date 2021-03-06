package app.superhero.src.fragments;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import app.superhero.MainActivity;
import app.superhero.R;
import app.superhero.src.model.dao.SuperheroMasterData;
import app.superhero.src.viewmodels.CommentsViewModel;
import app.superhero.src.views.ButtonView2;
import app.superhero.src.views.CommentView;

@EFragment(R.layout.fragment_comments)
public class CommentsFragment extends BaseFragment {
    @Bean
    CommentsViewModel viewModel;

    @FragmentArg
    SuperheroMasterData superheroMasterData;

    @ViewById
    ButtonView2 button;

    @ViewById
    CommentView commentView;

    boolean isFirstInit = true;

    @AfterViews
    public void init() {
        button.setButtonLabel(getResources().getString(R.string.save_comment_button_label));
        observeComments();
        viewModel.getComment(superheroMasterData.getId());
        button.setEnabled(false);
        button.setOnClickListener(view -> clickButton());
    }

    private void clickButton() {
        viewModel.setComment(superheroMasterData.getId(), commentView.getComment());
        ((MainActivity) requireActivity()).hideKeyboard(commentView.getEditText());
    }

    private void observeComments() {
        viewModel.comments.observe(this, commentText -> {
            commentView.setCommentInput(commentText);
            if (isFirstInit) {
                bindCommentComponent(commentText);
                isFirstInit = false;
            }
        });
    }

    private void bindCommentComponent(String commentText) {
        commentView.bind(commentText, isChanged -> button.setEnabled(isChanged));
    }
}
