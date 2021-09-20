package app.superhero.src.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import app.superhero.R;

@EViewGroup(R.layout.details_item_view)
public class DetailsItemView extends LinearLayout {

    @ViewById
    ConstraintLayout detailsItemViewRoot;

    @ViewById
    TextView title;

    @ViewById
    TextView data;

    String label;

    public DetailsItemView(@NonNull Context context) {
        super(context);
    }

    public DetailsItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initWithAttribute(attrs);
    }

    public DetailsItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWithAttribute(attrs);

    }

    public DetailsItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initWithAttribute(attrs);
    }

    @AfterViews
    protected void init() {
        title.setText(label);
    }

    private void initWithAttribute(AttributeSet attrs) {
        TypedArray attribute = getContext().obtainStyledAttributes(attrs, R.styleable.DetailsItemView);
        label = attribute.getString(R.styleable.DetailsItemView_itemTitle);
        attribute.recycle();
    }
}
