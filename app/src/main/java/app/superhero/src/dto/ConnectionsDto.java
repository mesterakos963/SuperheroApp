package app.superhero.src.dto;

import com.squareup.moshi.Json;

import app.superhero.src.model.response.BaseResponse;
import app.superhero.src.model.response.ResponseResult;
import app.superhero.src.utils.ZeroWhenNull;

public class ConnectionsDto extends BaseResponse {

    @ZeroWhenNull
    int id;

    String name;

    @Json(name = "group-affiliation")
    String groupAffiliation;
    String relatives;

    public ResponseResult getResponse() {
        return response;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGroupAffiliation() {
        return groupAffiliation;
    }

    public String getRelatives() {
        return relatives;
    }
}
