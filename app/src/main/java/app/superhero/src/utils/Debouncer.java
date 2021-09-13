package app.superhero.src.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

public class Debouncer {

    public Debouncer(int timeout, TimeUnit timeUnit, app.superhero.src.interfaces.Debouncer debouncerCallback) {
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.debouncerCallback = debouncerCallback;
    }

    private final int MESSAGE_WHAT = 4533;
    private int timeout;
    private TimeUnit timeUnit;
    private app.superhero.src.interfaces.Debouncer debouncerCallback;

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what != MESSAGE_WHAT) {
                return;
            }
            debouncerCallback.callback(msg.obj.toString());
        }
    };

    public void process(String text) {
        handler.removeMessages(MESSAGE_WHAT);
        Message message = handler.obtainMessage(MESSAGE_WHAT, text);
        handler.sendMessageDelayed(message, timeUnit.toMillis(timeout));
    }
}