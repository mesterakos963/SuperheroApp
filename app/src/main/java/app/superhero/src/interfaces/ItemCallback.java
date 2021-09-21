package app.superhero.src.interfaces;

public interface ItemCallback<T> {
    void onSuccess(T result);

    void onError(T fallbackResult, Throwable t);
}
