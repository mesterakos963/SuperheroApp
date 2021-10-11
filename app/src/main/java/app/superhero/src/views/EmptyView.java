package app.superhero.src.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import app.superhero.R;


@EViewGroup(R.layout.empty_view)
public class EmptyView extends LinearLayout {

    @ViewById
    LinearLayout emptyViewRoot;

    @ViewById
    TextView emptyViewText;

    @ViewById
    ImageView emptyViewImage;

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

    public void setOnClickListenerToEmptyView(OnClickListener onClickListener) {
        emptyViewRoot.setOnClickListener(onClickListener);
    }

    public void setPaddingBottom(int padding) {
        emptyViewRoot.setPadding(0, 0, 0, padding);
    }

    public void setText(String text) {
        emptyViewText.setText(text);
    }

    public void setEmptyViewImage(Drawable drawable) {
        emptyViewImage.setImageDrawable(drawable);
    }
}
