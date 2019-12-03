
package com.deepjose.ateneanote.responses;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseSummaries {

    @SerializedName("summaries")
    @Expose
    private List<Summary> summaries = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponseSummaries() {
    }

    /**
     * 
     * @param summaries
     */
    public ResponseSummaries(List<Summary> summaries) {
        super();
        this.summaries = summaries;
    }

    public List<Summary> getSummaries() {
        return summaries;
    }

    public void setSummaries(List<Summary> summaries) {
        this.summaries = summaries;
    }

    public ResponseSummaries withSummaries(List<Summary> summaries) {
        this.summaries = summaries;
        return this;
    }

}
