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

@EViewGroup(R.layout.button_view2)
public class ButtonView2 extends FrameLayout{
    @ViewById
    protected FrameLayout button2Root;

    @ViewById
    protected TextView button2Label;

    public ButtonView2(@NonNull Context context) {
        super(context);
    }

    public ButtonView2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ButtonView2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (button2Root != null) {
            button2Root.setEnabled(enabled);
            button2Label.setEnabled(enabled);
        }
    }

    public void setButtonLabel(String label) {
        button2Label.setText(label);
    }
}
