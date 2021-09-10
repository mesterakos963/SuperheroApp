package app.superhero.src.api;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.superhero.src.views.ViewWrapper;

public abstract class RecyclerViewBaseAdapter<T, V extends View> extends RecyclerView.Adapter<ViewWrapper<V>> {

    protected List<T> items = new ArrayList<>();

    private AdapterChanged callback;

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public final ViewWrapper<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<V>(onCreateItemView(parent, viewType));
    }

    protected abstract V onCreateItemView(ViewGroup parent, int viewType);

    public void add(T item) {
        items.add(item);
        onChange();
    }

    public void add(int position, T item) {
        items.add(position, item);
        onChange();
    }

    public void addAll(List<? extends T> item) {
        items.addAll(item);
        onChange();
    }

    public void addAll(int position, List<? extends T> item) {
        items.addAll(position, item);
        onChange();
    }

    public void refreshData(List<? extends T> items) {
        clear();
        this.items.addAll(items);
        notifyDataSetChanged();
        onChange();
    }

    public void refresh(int position, T item) {
        items.set(position, item);
        notifyItemChanged(position);
        onChange();
    }

    public void clear() {
        items.clear();
        onChange();
    }

    public void remove(int position) {
        items.remove(position);
    }

    public T getItem(int index) {
        return items.get(index);
    }

    public int size() {
        return items.size();
    }

    public void setOnChangeListener(AdapterChanged callback) {
        this.callback = callback;
    }


    protected void onChange() {
        if (callback != null) {
            callback.onChange();
        }
    }

    public interface AdapterChanged {
        void onChange();
    }
}