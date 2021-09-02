package app.superhero.src.api;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class NullGsonConverter extends TypeAdapter<String> {
    @Override
    public String read(JsonReader in) throws IOException {
        if (in.nextString().equals("null")) {
            return null;
        }
        return in.toString();
    }

    @Override
    public void write(JsonWriter jsonWriter, String s) throws IOException {
    }
}

