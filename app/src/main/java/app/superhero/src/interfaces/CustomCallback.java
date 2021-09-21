package app.superhero.src.interfaces;

import java.util.List;

public interface CustomCallback<T> {
    void onSuccess(List<T> results);

    void onError(List<T> fallbackResult, Throwable t);
}
