
package com.deepjose.ateneanote.responses;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseCourses {

    @SerializedName("courses")
    @Expose
    private List<Course> courses = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponseCourses() {
    }

    /**
     * 
     * @param courses
     */
    public ResponseCourses(List<Course> courses) {
        super();
        this.courses = courses;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public ResponseCourses withCourses(List<Course> courses) {
        this.courses = courses;
        return this;
    }

}
