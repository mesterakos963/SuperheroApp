package app.superhero.src.model.response;

import java.io.Serializable;

public class BaseResponse implements Serializable {

    protected ResponseResult response;
    protected String error;

    public String getError() {
        return error;
    }

    public boolean isValid() {
        return this.response == ResponseResult.SUCCESS;
    }
}
