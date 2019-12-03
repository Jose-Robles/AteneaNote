
package com.deepjose.ateneanote.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseUser {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("fbuid")
    @Expose
    private String fbuid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("surname")
    @Expose
    private String surname;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponseUser() {
    }

    /**
     * 
     * @param surname
     * @param name
     * @param email
     * @param fbuid
     */
    public ResponseUser(String email, String fbuid, String name, String surname) {
        super();
        this.email = email;
        this.fbuid = fbuid;
        this.name = name;
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ResponseUser withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFbuid() {
        return fbuid;
    }

    public void setFbuid(String fbuid) {
        this.fbuid = fbuid;
    }

    public ResponseUser withFbuid(String fbuid) {
        this.fbuid = fbuid;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResponseUser withName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public ResponseUser withSurname(String surname) {
        this.surname = surname;
        return this;
    }

}
