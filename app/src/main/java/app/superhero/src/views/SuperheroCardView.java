package app.superhero.src.views;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import app.superhero.R;
import app.superhero.src.dto.SuperheroDto;

@EViewGroup(R.layout.item_superhero)
public class SuperheroCardView extends CardView {

    @ViewById
    TextView nameText;

    @ViewById
    ImageView heroImage;

    public SuperheroCardView(@NonNull Context context) {
        super(context);
    }

    public void bind(SuperheroDto superheroDto) {
        nameText.setText(superheroDto.getName());
        Glide.with(heroImage.getContext())
                .load(superheroDto.getImageDto().getUrl())
                .placeholder(R.drawable.ic_place)
                .into(heroImage);
    }
}
