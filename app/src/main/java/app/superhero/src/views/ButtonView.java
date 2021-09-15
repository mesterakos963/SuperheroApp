package app.superhero.src.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import app.superhero.R;
import app.superhero.src.interfaces.TabButtonCallback;

@EViewGroup(R.layout.button_view)
public class ButtonView extends FrameLayout {

    @ViewById
    FrameLayout buttonViewRoot;

    TabButtonCallback tabButtonCallback;

    @ViewById
    TextView buttonViewText;

    String label;

    public ButtonView(@NonNull Context context) {
        super(context);
    }

    public ButtonView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initWithAttribute(attrs);
    }

    public ButtonView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWithAttribute(attrs);

    }

    public ButtonView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initWithAttribute(attrs);
    }

    @AfterViews
    protected void init() {
        buttonViewText.setText(label);
        buttonViewRoot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tabButtonCallback != null) {
                    tabButtonCallback.onButtonClicked(ButtonView.this.getId(), buttonViewRoot.isSelected());
                }
            }
        });
    }

    private void initWithAttribute(AttributeSet attrs) {
        TypedArray attribute = getContext().obtainStyledAttributes(attrs, R.styleable.ButtonView);
        label = attribute.getString(R.styleable.ButtonView_label);
        attribute.recycle();
    }

    public void bind(TabButtonCallback callback) {
        tabButtonCallback = callback;
    }

    public void setButtonSelected(boolean isSelected) {
        buttonViewRoot.setSelected(isSelected);
    }
}
