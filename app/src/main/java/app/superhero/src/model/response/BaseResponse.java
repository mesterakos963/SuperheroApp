package app.superhero.src.model.response;

public class BaseResponse {

    protected ResponseResult response;
    protected String error;

    public String getError() {
        return error;
    }

    public boolean isValid() {
        return this.response == response.SUCCESS;
    }
}
