package app.superhero.src.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import org.androidannotations.annotations.EViewGroup;

import app.superhero.R;

@EViewGroup(R.layout.comment_view)
public class CommentView extends LinearLayout {
    public CommentView(Context context) {
        super(context);
    }

    public CommentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CommentView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
