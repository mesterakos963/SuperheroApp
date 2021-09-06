package app.superhero.src.api;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

public class ZeroWhenNullAdapter {
    @ToJson
    String toJson(@ZeroWhenNull int n) {
        return Integer.toString(n);
    }

    @FromJson
    @ZeroWhenNull
    int fromJson(String n) {
        return n.equals("null") ? 0 : Integer.parseInt(n);
    }
}