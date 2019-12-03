
package com.deepjose.ateneanote.responses;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseSubjects {

    @SerializedName("subjects")
    @Expose
    private List<Subject> subjects = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponseSubjects() {
    }

    /**
     * 
     * @param subjects
     */
    public ResponseSubjects(List<Subject> subjects) {
        super();
        this.subjects = subjects;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public ResponseSubjects withSubjects(List<Subject> subjects) {
        this.subjects = subjects;
        return this;
    }

}
