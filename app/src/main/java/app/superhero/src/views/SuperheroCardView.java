package app.superhero.src.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.bumptech.glide.Glide;

import net.orandja.shadowlayout.ShadowLayout;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import app.superhero.R;
import app.superhero.src.interfaces.ItemClickListener;
import app.superhero.src.interfaces.StarClickCallback;
import app.superhero.src.model.dao.SuperheroMasterData;

@EViewGroup(R.layout.item_superhero)
public class SuperheroCardView extends FrameLayout {
    @ViewById
    TextView nameText;

    @ViewById
    ImageView heroImage;

    @ViewById
    ShadowLayout root;

    @ViewById
    ImageView star;

    @ViewById
    ImageView starBackground;

    @ViewById
    View starClickTrap;

    @ViewById
    ImageView hpBackground;

    @ViewById
    ConstraintLayout cardViewContainer;

    @ViewById
    TextView hpText;

    @ViewById
    ImageView heroHp;

    @ViewById
    ConstraintLayout hpBarContainer;

    @ViewById
    ImageView loserShape;

    @ViewById
    TextView result;

    public SuperheroCardView(@NonNull Context context) {
        super(context);
    }

    public SuperheroCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SuperheroCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void bind(SuperheroMasterData superhero, ItemClickListener itemClickListener, StarClickCallback starClickCallback, int position) {
        nameText.setText(superhero.getName());
        loadImage(superhero);
        root.setOnClickListener(v -> itemClickListener.onItemClick(superhero));
        starClickTrap.setOnClickListener(v -> {
            starClickCallback.onStarClick(superhero, position);
            star.setSelected(superhero.getIsFavourite());
        });
        star.setSelected(superhero.getIsFavourite());
    }

    public void bind(SuperheroMasterData superhero) {
        nameText.setText(superhero.getName());
        loadImage(superhero);
    }

    private void loadImage(SuperheroMasterData superhero) {
        Glide.with(heroImage.getContext())
                .load(superhero.getUrl())
                .placeholder(R.drawable.ic_place)
                .into(heroImage);
    }

    public void setCardWidth(int width) {
        ViewGroup.LayoutParams params = root.getLayoutParams();
        params.width = width;
        root.setLayoutParams(params);
    }

    public void hideStar() {
        star.setEnabled(false);
        star.setVisibility(GONE);
        starBackground.setEnabled(false);
        starBackground.setVisibility(GONE);
        starClickTrap.setEnabled(false);
    }

    public void hideBackground() {
        root.setBackgroundColor(getResources().getColor(R.color.transparent));
    }

    public void hideHpComponents() {
        hpText.setVisibility(GONE);
        hpBackground.setVisibility(GONE);
        heroHp.setVisibility(GONE);
    }

    public void showHpComponents() {
        hpText.setVisibility(VISIBLE);
        hpBackground.setVisibility(VISIBLE);
        heroHp.setVisibility(VISIBLE);
    }

    public void setHpText(String text) {
        hpText.setText(text);
    }

    public void setHpBar(int hp) {
        ConstraintSet set = new ConstraintSet();
        set.clone(hpBarContainer);
        if (hp <= 0) {
            hpBarContainer.setVisibility(GONE);
            loserShape.setVisibility(VISIBLE);
        } else {
            set.constrainPercentHeight(R.id.heroHp, (float) hp / 100);
            set.applyTo(hpBarContainer);
        }
    }

    public void reInitCardView() {
        loserShape.setVisibility(GONE);
        hpBarContainer.setVisibility(VISIBLE);
    }

    public void setResult(String text, int color) {
        hpText.setVisibility(GONE);
        hpBarContainer.setVisibility(GONE);
        result.setVisibility(VISIBLE);
        result.setText(text);
        result.setTextColor(color);
    }

    public void hideResult() {
        result.setVisibility(GONE);
    }
}
