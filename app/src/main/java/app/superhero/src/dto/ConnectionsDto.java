package app.superhero.src.dto;

import com.squareup.moshi.Json;

import java.io.Serializable;

import app.superhero.src.utils.ZeroWhenNull;

public class ConnectionsDto implements Serializable {
    String response;

    @ZeroWhenNull
    int id;

    String name;

    @Json(name = "group-affiliation")
    String groupAffiliation;
    String relatives;
}
