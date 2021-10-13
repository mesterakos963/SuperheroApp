package app.superhero.src.api;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.superhero.R;
import app.superhero.src.dao.SuperheroMasterData;
import app.superhero.src.interfaces.ItemClickListener;
import app.superhero.src.interfaces.StarClickCallback;
import app.superhero.src.utils.DiffUtilCallback;
import app.superhero.src.views.SuperheroCardView;
import app.superhero.src.views.SuperheroCardView_;
import app.superhero.src.views.ViewWrapper;

public class SuperheroesAdapter extends RecyclerView.Adapter<ViewWrapper<SuperheroCardView>> {
    private final ItemClickListener itemClickListener;
    private final StarClickCallback starClickCallback;
    private final ArrayList<SuperheroMasterData> data;

    public SuperheroesAdapter(ArrayList<SuperheroMasterData> data,
                              ItemClickListener itemClickListener,
                              StarClickCallback starClickCallback) {
        this.itemClickListener = itemClickListener;
        this.starClickCallback = starClickCallback;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewWrapper<SuperheroCardView> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SuperheroCardView superheroCardView = SuperheroCardView_.build(parent.getContext());
        return new ViewWrapper<>(superheroCardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewWrapper<SuperheroCardView> holder, int position) {
        holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.transparent));
        holder.getView().bind(data.get(position), itemClickListener, starClickCallback, position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<SuperheroMasterData> newData) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtilCallback(newData, data));
        data.clear();
        data.addAll(newData);
        diffResult.dispatchUpdatesTo(this);
    }

    public void deleteItem(int index) {
        data.remove(index);
        notifyItemRemoved(index);
        notifyItemRangeChanged(index, getItemCount());
    }
}

