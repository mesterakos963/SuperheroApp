package app.superhero.src.views;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class ViewWrapper<V extends View> extends RecyclerView.ViewHolder {

    private final V view;

    public ViewWrapper(V itemView) {
        super(itemView);
        view = itemView;
    }

    public V getView() {
        return view;
    }
}
