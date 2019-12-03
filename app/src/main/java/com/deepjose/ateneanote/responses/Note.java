
package com.deepjose.ateneanote.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Note {

    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("id")
    @Expose
    private long id;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Note() {
    }

    /**
     * 
     * @param date
     * @param id
     * @param content
     */
    public Note(String content, String date, long id) {
        super();
        this.content = content;
        this.date = date;
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Note withContent(String content) {
        this.content = content;
        return this;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Note withDate(String date) {
        this.date = date;
        return this;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Note withId(long id) {
        this.id = id;
        return this;
    }

}
