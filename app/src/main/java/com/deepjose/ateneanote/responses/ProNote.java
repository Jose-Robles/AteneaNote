
package com.deepjose.ateneanote.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProNote {

    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("date")
    @Expose
    private Date date;
    @SerializedName("id")
    @Expose
    private long id;

    /**
     * No args constructor for use in serialization
     *
     */
    public ProNote(Note note) {
        this(note.getContent(), note.getDate(), note.getId());
    }

    /**
     *
     * @param date
     * @param id
     * @param content
     */
    public ProNote(String content, String date, long id) {
        super();
        this.content = content;
        DateFormat dateFormat = new SimpleDateFormat(
                "dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
        try {
            this.date = dateFormat.parse(date.substring(6));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ProNote withContent(String content) {
        this.content = content;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(String date){
        DateFormat dateFormat = new SimpleDateFormat(
                "EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
        try {
            this.date = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ProNote withId(long id) {
        this.id = id;
        return this;
    }

}
