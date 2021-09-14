package app.superhero.src.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

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

    public ButtonView(Context context) {
        super(context);
    }

    public ButtonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ButtonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @AfterViews
    public void init() {
        buttonViewRoot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tabButtonCallback != null) {
                    tabButtonCallback.onButtonClicked(ButtonView.this.getId(), buttonViewRoot.isSelected());
                }
            }
        });
    }

    public void bind(TabButtonCallback callback) {
        tabButtonCallback = callback;
    }

    public void setButtonSelected(boolean isSelected) {
        buttonViewRoot.setSelected(isSelected);
    }
}
