package app.superhero.src.views;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import app.superhero.R;

@EViewGroup(R.layout.comment_view)
public class CommentView extends LinearLayout {

    @ViewById
    TextView characterCounter;

    @ViewById
    EditText commentText;

    private final int CHARACTER_LIMIT = 500;

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

    @AfterViews
    void init() {
        countCharacters();
    }

    private void countCharacters() {
        characterCounter.setText(String.valueOf(CHARACTER_LIMIT- commentText.length()));
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                characterCounter
                        .setText(getContext().getResources()
                                .getQuantityString(R.plurals.character_limit_label,
                                        CHARACTER_LIMIT - commentText.length(),
                                        CHARACTER_LIMIT - commentText.length()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}