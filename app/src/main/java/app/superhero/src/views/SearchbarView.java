package app.superhero.src.views;

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import app.superhero.R;

@EViewGroup(R.layout.searchbar)
public class SearchbarView extends FrameLayout {

    @ViewById
    EditText textField;

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

    public void bind(String hint, TextWatcher textWatcher) {
        textField.setHint(hint);
        textField.addTextChangedListener(textWatcher);
    }

}
