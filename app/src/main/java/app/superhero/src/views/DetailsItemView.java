package app.superhero.src.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import app.superhero.R;

@EViewGroup(R.layout.details_item_view)
public class DetailsItemView extends LinearLayout {
    @ViewById
    LinearLayout detailsItemViewRoot;

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

    public void setData(String label) {
        if (label == null || label.equals("-") || label.equals("null")) {
            data.setText("-");
        } else {
            data.setText(label);
        }
    }

    public void setData(List<String> strings) {
        StringBuilder stringBuilder = new StringBuilder();
        if (strings != null || !strings.isEmpty()) {
            if (strings.get(0).equals("-")) {
                data.setText("-");
            } else {
                for (int i = 0; i < strings.size(); i++) {
                    if (strings.size() - 1 == i) {
                        stringBuilder.append(strings.get(i)).append(".");
                    } else {
                        stringBuilder.append(strings.get(i)).append(", ");

                    }
                }
            }
        }
        data.setText(stringBuilder.toString());
    }
}
