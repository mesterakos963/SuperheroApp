package app.superhero.src.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;

import app.superhero.R;
import app.superhero.src.interfaces.TextChangedCallback;
import app.superhero.src.utils.OnFocusEvent;

@EViewGroup(R.layout.comment_view)
public class CommentView extends LinearLayout {

    @ViewById
    TextView characterCounter;

    @ViewById
    EditText commentInput;

    TextChangedCallback callback;

    private final int CHARACTER_LIMIT = 500;
    private String previousComment;
    private final boolean isFocused = false;

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
        onCommentTextTouchListener();
    }

    public void bind(String previousComment, TextChangedCallback callback) {
        this.previousComment = previousComment;
        this.callback = callback;
    }

    private void countCharacters() {
        characterCounter.setText(getContext().getResources()
                .getQuantityString(R.plurals.character_limit_label,
                        CHARACTER_LIMIT - commentInput.length(),
                        CHARACTER_LIMIT - commentInput.length()));
        commentInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                characterCounter
                        .setText(getContext().getResources()
                                .getQuantityString(R.plurals.character_limit_label,
                                        CHARACTER_LIMIT - commentInput.length(),
                                        CHARACTER_LIMIT - commentInput.length()));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (callback != null) {
                    callback.onTextChanged(!editable.toString().equals(previousComment));
                }
            }
        });
    }

    public String getComment() {
        return commentInput.getText().toString();
    }

    public void setCommentInput(String text) {
        commentInput.setText(text);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void onCommentTextTouchListener() {
        commentInput.setOnTouchListener((view, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                EventBus.getDefault().post(new OnFocusEvent(isFocused));
            }
            return false;
        });
    }

    public View getEditText() {
        return commentInput;
    }
}
