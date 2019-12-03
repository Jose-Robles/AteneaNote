
package com.deepjose.ateneanote.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Unit {

    @SerializedName("finalDate")
    @Expose
    private String finalDate;
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("initDate")
    @Expose
    private String initDate;
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Unit() {
    }

    /**
     * 
     * @param finalDate
     * @param initDate
     * @param name
     * @param id
     */
    public Unit(String finalDate, long id, String initDate, String name) {
        super();
        this.finalDate = finalDate;
        this.id = id;
        this.initDate = initDate;
        this.name = name;
    }

    public String getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(String finalDate) {
        this.finalDate = finalDate;
    }

    public Unit withFinalDate(String finalDate) {
        this.finalDate = finalDate;
        return this;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Unit withId(long id) {
        this.id = id;
        return this;
    }

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }

    public Unit withInitDate(String initDate) {
        this.initDate = initDate;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Unit withName(String name) {
        this.name = name;
        return this;
    }

}
