package app.superhero.src.views;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;

import net.orandja.shadowlayout.ShadowLayout;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import app.superhero.R;
import app.superhero.src.dao.SuperheroMasterData;
import app.superhero.src.interfaces.ItemClickListener;

@EViewGroup(R.layout.item_superhero)
public class SuperheroCardView extends CardView {

    @ViewById
    TextView nameText;

    @ViewById
    ImageView heroImage;

    @ViewById
    ShadowLayout root;

    public SuperheroCardView(@NonNull Context context) {
        super(context);
    }

    public void bind(SuperheroMasterData superhero, ItemClickListener itemClickListener) {
        nameText.setText(superhero.getName());
        Glide.with(heroImage.getContext())
                .load(superhero.getUrl())
                .placeholder(R.drawable.ic_place)
                .into(heroImage);
        root.setOnClickListener(v -> {
            itemClickListener.onItemClick(superhero);
        });
    }
}
