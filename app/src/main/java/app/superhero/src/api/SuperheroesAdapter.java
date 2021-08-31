package app.superhero.src.api;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.superhero.R;
import app.superhero.src.interfaces.ItemClickListener;
import app.superhero.src.models.Superhero;

public class SuperheroesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Superhero> superheroes;
    private ItemClickListener itemClickListener;

    public SuperheroesAdapter(@NonNull Context context, List<Superhero> superheroes, ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        this.superheroes = superheroes;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_superhero, parent, false);
        return new SuperheroViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return superheroes.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Superhero superhero = superheroes.get(position);
        ((SuperheroViewHolder) holder).bind(superhero, itemClickListener);
    }

    public void update(List<Superhero> superheroList) {
        superheroes = superheroList;
        notifyDataSetChanged();
    }

    public static class SuperheroViewHolder extends RecyclerView.ViewHolder {

        private final TextView nameText;

        public SuperheroViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.nameText);
        }

        public void bind(Superhero superhero, ItemClickListener itemClickListener) {
            nameText.setText(superhero.getName());
            itemView.setOnClickListener(view -> {
                try {
                    itemClickListener.onItemClick(superhero);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
