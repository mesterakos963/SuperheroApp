package app.superhero.src.views;

import android.content.Context;
import android.graphics.PorterDuff;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import app.superhero.R;

@EViewGroup(R.layout.searchbar)
public class SearchbarView extends FrameLayout {

    @ViewById
    EditText textField;

    @ViewById
    ImageView searchIcon;

    public SearchbarView(@NonNull Context context) {
        super(context);
    }

    public SearchbarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchbarView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SearchbarView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @AfterViews
    protected void init() {
        textField.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(hasFocus()) {
                    searchIcon.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.searchbar_text_focused), PorterDuff.Mode.SRC_IN);
                } else {
                    searchIcon.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.searchbar_text_not_focused), PorterDuff.Mode.SRC_IN);
                }
            }
        });
    }

    public void bind(TextWatcher textWatcher) {
        textField.addTextChangedListener(textWatcher);
    }

}
