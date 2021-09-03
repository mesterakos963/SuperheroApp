package app.superhero.src.model.response;

import com.squareup.moshi.Json;

import java.io.Serializable;

public enum ResponseResult implements Serializable {
    @Json(name = "success")
    SUCCESS,
    @Json(name = "error")
    ERROR
}
