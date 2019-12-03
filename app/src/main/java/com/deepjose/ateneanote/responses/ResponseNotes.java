
package com.deepjose.ateneanote.responses;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseNotes {

    @SerializedName("notes")
    @Expose
    private List<Note> notes = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponseNotes() {
    }

    /**
     * 
     * @param notes
     */
    public ResponseNotes(List<Note> notes) {
        super();
        this.notes = notes;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public ResponseNotes withNotes(List<Note> notes) {
        this.notes = notes;
        return this;
    }

}
