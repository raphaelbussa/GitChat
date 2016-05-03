package rebus.gitchat.http.response;

import java.io.Serializable;

/**
 * Created by raphaelbussa on 22/01/16.
 */
public class SampleResponse implements Serializable {

    private boolean success;
    private String error;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
