package app.superhero.src.api;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import app.superhero.R;
import app.superhero.src.dto.SuperheroDto;
import app.superhero.src.views.SuperheroCardView;
import app.superhero.src.views.SuperheroCardView_;
import app.superhero.src.views.ViewWrapper;

public class SuperheroesAdapter extends RecyclerViewBaseAdapter<SuperheroDto, SuperheroCardView> {

    @Override
    protected SuperheroCardView onCreateItemView(ViewGroup parent, int viewType) {
        return SuperheroCardView_.build(parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewWrapper<SuperheroCardView> holder, int position) {
        holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.transparent));
        holder.getView().bind(getItem(position));
    }
}
