package app.superhero.src.interfaces;

import java.util.List;

import app.superhero.src.dao.SuperheroMasterData;

public interface CustomCallback<T> {
    void onSuccess(List<SuperheroMasterData> results);

    void onError(List<T> fallbackResult, Throwable t);
}
