package app.superhero.src.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Connections implements Serializable {

    @SerializedName("group-affiliation")
    String groupAffiliation;
    String relatives;
}
