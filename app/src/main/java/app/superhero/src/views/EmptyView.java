package app.superhero.src.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import org.androidannotations.annotations.EViewGroup;

import app.superhero.R;

@EViewGroup(R.layout.empty_view)
public class EmptyView extends LinearLayout {
    public EmptyView(Context context) {
        super(context);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
