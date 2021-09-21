package app.superhero.src.interfaces;

import java.util.List;

public interface ListCallback<T> {
    void onSuccess(List<T> results);

    void onError(List<T> fallbackResult, Throwable t);
}
