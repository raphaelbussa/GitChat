package rebus.gitchat.http.response;

/**
 * Created by Raphael on 19/12/2015.
 */
public class CodeResponse {

    private boolean success;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
