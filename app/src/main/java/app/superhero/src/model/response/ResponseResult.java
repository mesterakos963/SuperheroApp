package app.superhero.src.model.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public enum ResponseResult implements Serializable {
    @SerializedName("success")
    SUCCESS,
    @SerializedName("error")
    ERROR
}
