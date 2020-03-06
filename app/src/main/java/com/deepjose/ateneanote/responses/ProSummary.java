
package com.deepjose.ateneanote.responses;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class ProSummary {

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
    public ProSummary(Summary summary) {
        this(summary.getContent(), summary.getDate(), summary.getId());
    }

    /**
     *
     * @param date
     * @param id
     * @param content
     */
    public ProSummary(String content, String date, long id) {
        super();
        Log.d(TAG, "Summary: SOMEBODY HAS INVOQUED ME");
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

    public ProSummary withContent(String content) {
        this.content = content;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public ProSummary withId(long id) {
        this.id = id;
        return this;
    }

}
