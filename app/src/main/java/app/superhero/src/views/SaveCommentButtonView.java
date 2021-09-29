package app.superhero.src.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.androidannotations.annotations.EViewGroup;

import app.superhero.R;

@EViewGroup(R.layout.save_comment_button)
public class SaveCommentButtonView extends FrameLayout {

    protected FrameLayout saveCommentButtonRoot;

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
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        saveCommentButtonRoot.setSelected(selected);
    }
}
