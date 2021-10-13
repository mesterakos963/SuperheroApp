package app.superhero.src.utils;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import app.superhero.src.dao.SuperheroMasterData;

public class DiffUtilCallback extends DiffUtil.Callback {
    List<SuperheroMasterData> newList;
    List<SuperheroMasterData> oldList;

    public DiffUtilCallback(List<SuperheroMasterData> newList, List<SuperheroMasterData> oldList) {
        this.newList = newList;
        this.oldList = oldList;
    }

    @Override
    public int getOldListSize() {
        return oldList != null ? oldList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newList != null ? newList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return newList.get(newItemPosition).getId() == oldList.get(oldItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        SuperheroMasterData oldItem = oldList.get(oldItemPosition);
        SuperheroMasterData newItem = newList.get(newItemPosition);
        return oldItem.getName().equals(newItem.getName()) && (oldItem.getUrl().equals(newItem.getUrl()));
    }
}

