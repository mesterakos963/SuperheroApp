package app.superhero.src.fragments;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import app.superhero.R;
import app.superhero.src.dao.SuperheroMasterData;
import app.superhero.src.viewmodels.CommentsViewModel;
import app.superhero.src.views.CommentView;
import app.superhero.src.views.SaveCommentButtonView;

@EFragment(R.layout.fragment_comments)
public class CommentsFragment extends BaseFragment {

    @Bean
    CommentsViewModel viewModel;

    @FragmentArg
    SuperheroMasterData superheroMasterData;

    @ViewById
    SaveCommentButtonView button;

    @ViewById
    CommentView commentView;

    boolean isFirstInit = true;

    @AfterViews
    public void init() {
        viewModel.getComment(superheroMasterData.getId());
        observeComments();
        button.setOnClickListener(view -> viewModel.setComment(superheroMasterData.getId(), commentView.getComment()));
    }

    private void observeComments() {
        viewModel.comments.observe(this, commentText -> {
            commentView.setCommentText(commentText);
            if(isFirstInit) {
                bindCommentComponent(commentText);
                isFirstInit = false;
            }
        });
    }

    private void bindCommentComponent(String commentText) {
        commentView.bind(commentText, isChanged -> {
            button.setEnabled(isChanged);
        });
    }
}
