package app.superhero.src.models;

import com.google.gson.annotations.SerializedName;
import com.squareup.moshi.Json;

import java.io.Serializable;

public class Connections implements Serializable {

    @Json(name = "group-affiliation")
    String groupAffiliation;
    String relatives;
}
