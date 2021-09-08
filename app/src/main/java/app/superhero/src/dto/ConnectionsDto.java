package app.superhero.src.dto;

import com.squareup.moshi.Json;

import java.io.Serializable;

public class ConnectionsDto implements Serializable {

    @Json(name = "group-affiliation")
    String groupAffiliation;
    String relatives;
}
