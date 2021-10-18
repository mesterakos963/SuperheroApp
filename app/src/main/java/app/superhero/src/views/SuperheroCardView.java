package app.superhero.src.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;

import net.orandja.shadowlayout.ShadowLayout;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import app.superhero.R;
import app.superhero.src.dao.SuperheroMasterData;
import app.superhero.src.interfaces.ItemClickListener;
import app.superhero.src.interfaces.StarClickCallback;

@EViewGroup(R.layout.item_superhero)
public class SuperheroCardView extends CardView {
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
        root.setOnClickListener(v -> {
            itemClickListener.onItemClick(superhero);
        });
        starClickTrap.setOnClickListener(v -> {
            starClickCallback.onStarClick(superhero, position);
            star.setSelected(superhero.getIsFavourite());
        });
        star.setSelected(superhero.getIsFavourite());
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
}
