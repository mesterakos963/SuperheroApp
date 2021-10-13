package app.superhero.src.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import app.superhero.R;

@EViewGroup(R.layout.save_comment_button)
public class SaveCommentButtonView extends FrameLayout {
    @ViewById
    protected FrameLayout saveCommentButtonRoot;

    @ViewById
    protected TextView saveCommentButtonLabel;

    public SaveCommentButtonView(@NonNull Context context) {
        super(context);
    }

    public SaveCommentButtonView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SaveCommentButtonView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (saveCommentButtonRoot != null) {
            saveCommentButtonRoot.setEnabled(enabled);
            saveCommentButtonLabel.setEnabled(enabled);
        }
    }
}
