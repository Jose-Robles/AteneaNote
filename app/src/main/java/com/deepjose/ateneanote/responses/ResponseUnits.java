
package com.deepjose.ateneanote.responses;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseUnits {

    @SerializedName("units")
    @Expose
    private List<Unit> units = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponseUnits() {
    }

    /**
     * 
     * @param units
     */
    public ResponseUnits(List<Unit> units) {
        super();
        this.units = units;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public ResponseUnits withUnits(List<Unit> units) {
        this.units = units;
        return this;
    }

}
