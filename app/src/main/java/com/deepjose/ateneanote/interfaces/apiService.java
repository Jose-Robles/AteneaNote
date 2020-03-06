package com.deepjose.ateneanote.interfaces;
import com.deepjose.ateneanote.responses.*;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface apiService
{
    //https://ateneanote.herokuapp.com/api/...

    //  CREATE

    @FormUrlEncoded
    @POST("new/user")
    Call<ResponseOk> newUser(@Field("FBUID") String fbuid,
                             @Field("name") String name,
                             @Field("surname") String surname,
                             @Field("email") String email,
                             @Field("pass") String pass
    ) ;


    @FormUrlEncoded
    @POST("new/course")
    Call<ResponseOk> newCourse(@Field("FBUID") String fbuid,
                               @Field("name") String name
                               ) ;

    @FormUrlEncoded
    @POST("new/subject")
    Call<ResponseOk> newSubject(@Field("course_id") String courseId,
                                @Field("name") String name,
                                @Field("color") String color
                                ) ;

    @FormUrlEncoded
    @POST("new/summary")
    Call<ResponseOk> newSummary(@Field("subject_id") String subjectId,
                                @Field("content") String content
                                ) ;

    @FormUrlEncoded
    @POST("new/note")
    Call<ResponseOk> newNote(@Field("subject_id") String subjectId,
                             @Field("content") String content
                             ) ;

    @FormUrlEncoded
    @POST("new/unit")
    Call<ResponseOk> newUnit(@Field("subject_id") String subjectId,
                             @Field("name") String name
                             ) ;


    //  READ

    @FormUrlEncoded
    @POST("get/user")
    Call<ResponseUser> getUser(@Field("FBUID") String fbuid) ;

    @FormUrlEncoded
    @POST("get/courses")
    Call<ResponseCourses> getCourses(@Field("FBUID") String fbuid) ;

    @FormUrlEncoded
    @POST("get/subjects")
    Call<ResponseSubjects> getSubjects(@Field("course_id") String courseId) ;

    @FormUrlEncoded
    @POST("get/summaries")
    Call<ResponseSummaries> getSummaries(@Field("subject_id") String subjectId) ;

    @FormUrlEncoded
    @POST("get/notes")
    Call<ResponseNotes> getNotes(@Field("subject_id") String subjectId) ;

    @FormUrlEncoded
    @POST("get/units")
    Call<ResponseUnits> getUnits(@Field("subject_id") String subjectId) ;


    //  DELETE

    @FormUrlEncoded
    @POST("delete/course")
    Call<ResponseOk> delCourse(@Field("course_id") String course_id) ;

    @FormUrlEncoded
    @POST("delete/subject")
    Call<ResponseOk> delSubject(@Field("subject_id") String subject_id) ;

    @FormUrlEncoded
    @POST("delete/summary")
    Call<ResponseOk> delSummary(@Field("summary_id") String summaryId) ;

    @FormUrlEncoded
    @POST("delete/note")
    Call<ResponseOk> delNote(@Field("note_id") String noteId) ;

    @FormUrlEncoded
    @POST("delete/unit")
    Call<ResponseOk> delUnit(@Field("unit_id") String unitId) ;


    //  UPDATE


    @FormUrlEncoded
    @POST("update/user")
    Call<ResponseOk> setUser(@Field("FBUID") String fbuid,
                             @Field("name") String name,
                             @Field("surname") String surname,
                             @Field("email") String email
                             ) ;

    @FormUrlEncoded
    @POST("update/course")
    Call<ResponseOk> setCourse(@Field("course_id") String course_id,
                               @Field("name") String name
                               ) ;

    @FormUrlEncoded
    @POST("update/subject")
    Call<ResponseOk> setSubject(@Field("subject_id") String subject_id,
                                @Field("name") String name,
                                @Field("color") String color
                                ) ;

    @FormUrlEncoded
    @POST("update/summary")
    Call<ResponseOk> setSummary(@Field("summary_id") String summaryId,
                                @Field("content") String content
                                ) ;

    @FormUrlEncoded
    @POST("update/note")
    Call<ResponseOk> setNote(@Field("note_id") String noteId,
                             @Field("content") String content
                             ) ;

    @FormUrlEncoded
    @POST("update/unit")
    Call<ResponseOk> setUnit(@Field("unit_id") String unitId,
                             @Field("name") String name
                             ) ;





    /*
    @GET("api/series")
    Call<List<Serie>> getAllShows() ;

    @GET("api/serie")
    Call<Serie> getShow(@Query("id") int id) ;

    @GET("api/capitulo")
    Call<Capitulo> getChapter(@Query("id") int id) ;




    @POST("api/login")
    @FormUrlEncoded
    Call<Usuario> doLogin(
       @Header("Authorization") String token,
       @Field("email")    String ema,
       @Field("password") String pas
    ) ;*/

}
