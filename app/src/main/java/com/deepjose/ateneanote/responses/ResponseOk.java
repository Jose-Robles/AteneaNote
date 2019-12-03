
package com.deepjose.ateneanote.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseOk {

    @SerializedName("success")
    @Expose
    private boolean success;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponseOk() {
    }

    /**
     * 
     * @param success
     */
    public ResponseOk(boolean success) {
        super();
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ResponseOk withSuccess(boolean success) {
        this.success = success;
        return this;
    }

}
