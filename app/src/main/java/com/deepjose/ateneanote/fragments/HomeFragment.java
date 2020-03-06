package com.deepjose.ateneanote.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.deepjose.ateneanote.MainActivity;
import com.deepjose.ateneanote.MainDrawer;
import com.deepjose.ateneanote.R;
import com.deepjose.ateneanote.Register;
import com.deepjose.ateneanote.adapters.AllAdapter;
import com.deepjose.ateneanote.api.apiClient;
import com.deepjose.ateneanote.interfaces.apiService;
import com.deepjose.ateneanote.responses.Course;
import com.deepjose.ateneanote.responses.Note;
import com.deepjose.ateneanote.responses.ProNote;
import com.deepjose.ateneanote.responses.ProSummary;
import com.deepjose.ateneanote.responses.ResponseCourses;
import com.deepjose.ateneanote.responses.ResponseNotes;
import com.deepjose.ateneanote.responses.ResponseOk;
import com.deepjose.ateneanote.responses.ResponseSubjects;
import com.deepjose.ateneanote.responses.ResponseSummaries;
import com.deepjose.ateneanote.responses.ResponseUser;
import com.deepjose.ateneanote.responses.Subject;
import com.deepjose.ateneanote.responses.Summary;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment implements AllAdapter.MyItemsListener {

    private ArrayList listaCosas;
    private ArrayList<? extends Object> currentlist;
    private apiService api;
    private int howDeep = 0;
    private int courseId = 0;
    private int subjectId = 0;
    private View view;
    private ArrayList<ProNote> notes;
    private ArrayList<ProSummary> summaries;
    private static int TYPE_NOTE = 1;
    private static int TYPE_SUMMARY = 2;
    Boolean noteCheck = false;
    Boolean summaryChek = false;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        api = apiClient.getService("http://ateneanote.herokuapp.com/api/");

        view = inflater.inflate(R.layout.fragment_home, container, false);

        FloatingActionButton back = view.findViewById(R.id.back);

        refreshListCourses();
        back.setVisibility(view.INVISIBLE);
        howDeep++; //level 1

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            private int type = 0;
            @Override
            public void onClick (View view){
                switch(howDeep){
                    case 1:
                        SharedPreferences sharedPref = getActivity().getSharedPreferences("fbuid", Context.MODE_PRIVATE);
                        final EditText input = (new EditText(getContext()));
                        input.setElegantTextHeight(true);
                        input.setHint(getResources().getString(R.string.dialogLevel1Course));
                        new MaterialAlertDialogBuilder(getContext())
                                .setTitle(getResources().getString(R.string.dialogLevel1TitleCreate))
                                .setView(input)
                                .setCancelable(false)
                                .setNegativeButton(R.string.label_cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                    })
                                .setPositiveButton(R.string.label_ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        api.newCourse(sharedPref.getString("fbuid", ""), input.getText().toString()).enqueue(new Callback<ResponseOk>() {
                                            @Override
                                            public void onResponse(Call<ResponseOk> call, Response<ResponseOk> response) {
                                                refreshListCourses();
                                            }

                                            @Override
                                            public void onFailure(Call<ResponseOk> call, Throwable t) {
                                                Toast.makeText(getContext(), "Ha habido un problema de conexión", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                })
                                .show();
                        break;
                    case 2:
                        final EditText input2 = (new EditText(getContext()));
                        input2.setElegantTextHeight(true);
                        input2.setHint(getResources().getString(R.string.dialogLevel2Subject));
                        new MaterialAlertDialogBuilder(getContext())
                            .setTitle(getResources().getString(R.string.dialogLevel2Title))
                            .setView(input2)
                            .setCancelable(false)
                            .setNegativeButton(R.string.label_cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .setPositiveButton(R.string.label_ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    api.newSubject("" + courseId, input2.getText().toString(), "123456").enqueue(new Callback<ResponseOk>() {
                                        @Override
                                        public void onResponse(Call<ResponseOk> call, Response<ResponseOk> response) {
                                            refreshListSubjects();
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseOk> call, Throwable t) {
                                            Toast.makeText(getContext(), "Ha habido un problema de conexión", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            })
                            .show();

                        break;
                    case 3:
                        final EditText input3 = (new EditText(getContext()));
                        input3.setElegantTextHeight(true);
                        input3.setHint("Lorem ipsum dolor sit amet, ...");
                        new MaterialAlertDialogBuilder(getContext())
                                .setTitle(getResources().getString(R.string.dialogLevel3Title))
                                .setView(input3)
                                .setCancelable(false)
                                .setSingleChoiceItems(new String[]{getResources().getString(R.string.dialogLevel3Note), getResources().getString(R.string.dialogLevel3Summary)}, 0, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //Toast.makeText(getContext(), "i = " + i, Toast.LENGTH_SHORT).show();
                                        //if(i == 0){
                                        //    RecyclerView recyclerView = view.findViewById(R.id.edit_recycler);
                                        //    recyclerView.not
                                        //}
                                        type = i;
                                    }
                                })
                                .setNegativeButton(R.string.label_cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                })
                                .setPositiveButton(R.string.label_ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if(type == 0){ // Note
                                            api.newNote("" + subjectId, input3.getText().toString()).enqueue(new Callback<ResponseOk>() {
                                                @Override
                                                public void onResponse(Call<ResponseOk> call, Response<ResponseOk> response) {
                                                    refreshListsMaterials("" + subjectId);
                                                }

                                                @Override
                                                public void onFailure(Call<ResponseOk> call, Throwable t) {
                                                    Toast.makeText(getContext(), "Ha habido un problema de conexión", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        } else{
                                            api.newSummary("" + subjectId, input3.getText().toString()).enqueue(new Callback<ResponseOk>() {
                                                @Override
                                                public void onResponse(Call<ResponseOk> call, Response<ResponseOk> response) {
                                                    refreshListsMaterials("" + subjectId);
                                                }

                                                @Override
                                                public void onFailure(Call<ResponseOk> call, Throwable t) {
                                                    Toast.makeText(getContext(), "Ha habido un problema de conexión", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                        type = 0;
                                    }
                                })
                                .show();

                        break;
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (howDeep){
                    case 1:
                        break;
                    case 2:
                        refreshListCourses();
                        back.setVisibility(view.INVISIBLE);
                        howDeep--;
                        break;
                    case 3:
                        //view subjects getting courseId
                        refreshListSubjects();
                        howDeep--;
                        break;
                }
            }
        });



        listaCosas = new ArrayList<>();
        listaCosas.add(new Course(1, "1 DAM"));
        listaCosas.add(new Course(2, "2 DAM"));
        listaCosas.add(new Course(3, "1 DAW"));
        listaCosas.add(new Course(4, "2 DAW"));
        listaCosas.add(new Course(5, "1 UNI"));

        return view;
    }



    private void refreshListCourses() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("fbuid", Context.MODE_PRIVATE);
        api.getCourses(sharedPref.getString("fbuid", "")).enqueue(new Callback<ResponseCourses>() {
            @Override
            public void onResponse(Call<ResponseCourses> call, Response<ResponseCourses> response) {
                Log.println(Log.VERBOSE, "WWWWWWWWWWWWWWWWWW", "MMMMMMMMMMMMMMMMMMMMMM");
                Log.println(Log.VERBOSE, "Response", response.toString());
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    RecyclerView recyclerView = view.findViewById(R.id.edit_recycler);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                    //here goes outside stuff like create the list
                    currentlist = new ArrayList<>(response.body().getCourses());
                    recyclerView.setAdapter(new AllAdapter(response.body().getCourses(), R.layout.layout_course, HomeFragment.this::OnItemClick));
                } else {
                    this.onFailure(call, new Exception());

                }
            }

            @Override
            public void onFailure(Call<ResponseCourses> call, Throwable t) {

            }
        });
    }

    private void refreshListSubjects() {
        api.getSubjects("" + courseId).enqueue(new Callback<ResponseSubjects>() {
            @Override
            public void onResponse(Call<ResponseSubjects> call, Response<ResponseSubjects> response) {
                Log.println(Log.VERBOSE, "WWWWWWWWWWWWWWWWWW", "MMMMMMMMMMMMMMMMMMMMMM");
                Log.println(Log.VERBOSE, "Response", response.toString());
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    RecyclerView recyclerView = view.findViewById(R.id.edit_recycler);
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

                    //here goes outside stuff like create the list
                    currentlist = new ArrayList<>(response.body().getSubjects());
                    recyclerView.setAdapter(new AllAdapter(response.body().getSubjects(), R.layout.layout_subject, HomeFragment.this::OnItemClick));
                } else {
                    this.onFailure(call, new Exception());

                }
            }

            @Override
            public void onFailure(Call<ResponseSubjects> call, Throwable t) {

            }
        });
    }

    @Override
    public void OnItemClick(int position) {
        switch (howDeep){
            case 1:
                Log.d(TAG, "OnItemClick: position --> " + position);
                Log.d(TAG, "OnItemClick: how deep --> " + howDeep);
                courseId = Integer.parseInt(((Course) currentlist.get(position)).getId());
                refreshListSubjects();
                view.findViewById(R.id.back).setVisibility(view.VISIBLE);
                howDeep++; //into subjects (level 2)
                break;

            case 2:
                Log.d(TAG, "OnItemClick: position --> " + position);
                Log.d(TAG, "OnItemClick: how deep --> " + howDeep);


                subjectId = Integer.parseInt(((Subject) currentlist.get(position)).getId());
                //  Get independently each one of the studying stuff lists
                refreshListsMaterials(position);
                howDeep++; //into idk (level 3)

                break;

            case 3:
                break;

            case 4:
                break;

            default:
                break;


        }
    }

    private void refreshListsMaterials(int position) {
        api.getNotes((((Subject) currentlist.get(position)).getId())).enqueue(new Callback<ResponseNotes>() {
            @Override
            public void onResponse(Call<ResponseNotes> call, Response<ResponseNotes> response) {
                Log.println(Log.VERBOSE, "WWWWWWWWWWWWWWWWWW", "MMMMMMMMMMMMMMMMMMMMMM");
                Log.println(Log.VERBOSE, "Response", response.toString());
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    // = new ArrayList(response.body().getNotes());
                    notes = (ArrayList<ProNote>) response.body().getNotes().stream().map(note -> new ProNote(note)).collect(Collectors.toList());
                    whenComplete(TYPE_NOTE);
                } else {
                }
            }

            @Override
            public void onFailure(Call<ResponseNotes> call, Throwable t) {
                Log.d(TAG, "ONFAILURE TRIGGERED ON NOTES CALL: " + t);
            }
        });

        api.getSummaries((((Subject) currentlist.get(position)).getId())).enqueue(new Callback<ResponseSummaries>() {
            @Override
            public void onResponse(Call<ResponseSummaries> call, Response<ResponseSummaries> response) {
                Log.println(Log.VERBOSE, "WWWWWWWWWWWWWWWWWW", "MMMMMMMMMMMMMMMMMMMMMM");
                Log.println(Log.VERBOSE, "Response", response.toString());
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    //summaries = new ArrayList(response.body().getSummaries());
                    summaries = (ArrayList<ProSummary>) response.body().getSummaries().stream().map(summary -> new ProSummary(summary)).collect(Collectors.toList());
                    whenComplete(TYPE_SUMMARY);
                } else {
                }
            }


            @Override
            public void onFailure(Call<ResponseSummaries> call, Throwable t) {
                Log.d(TAG, "ONFAILURE TRIGGERED ON SUMMARIES CALL: " + t);
            }
        });
    }

    private void refreshListsMaterials(String id) {
        api.getNotes(id).enqueue(new Callback<ResponseNotes>() {
            @Override
            public void onResponse(Call<ResponseNotes> call, Response<ResponseNotes> response) {
                Log.println(Log.VERBOSE, "WWWWWWWWWWWWWWWWWW", "MMMMMMMMMMMMMMMMMMMMMM");
                Log.println(Log.VERBOSE, "Response", response.toString());
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    // = new ArrayList(response.body().getNotes());
                    notes = (ArrayList<ProNote>) response.body().getNotes().stream().map(note -> new ProNote(note)).collect(Collectors.toList());
                    whenComplete(TYPE_NOTE);
                } else {
                }
            }

            @Override
            public void onFailure(Call<ResponseNotes> call, Throwable t) {
                Log.d(TAG, "ONFAILURE TRIGGERED ON NOTES CALL: " + t);
            }
        });

        api.getSummaries(id).enqueue(new Callback<ResponseSummaries>() {
            @Override
            public void onResponse(Call<ResponseSummaries> call, Response<ResponseSummaries> response) {
                Log.println(Log.VERBOSE, "WWWWWWWWWWWWWWWWWW", "MMMMMMMMMMMMMMMMMMMMMM");
                Log.println(Log.VERBOSE, "Response", response.toString());
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    //summaries = new ArrayList(response.body().getSummaries());
                    summaries = (ArrayList<ProSummary>) response.body().getSummaries().stream().map(summary -> new ProSummary(summary)).collect(Collectors.toList());
                    whenComplete(TYPE_SUMMARY);
                } else {
                }
            }


            @Override
            public void onFailure(Call<ResponseSummaries> call, Throwable t) {
                Log.d(TAG, "ONFAILURE TRIGGERED ON SUMMARIES CALL: " + t);
            }
        });
    }

    public void whenComplete(int type){
        Log.d(TAG, "whenComplete: DENTRO DEL WHENCOMPLETE");
        if(type == TYPE_NOTE){
            noteCheck = true;
        } else if(type == TYPE_SUMMARY){
            summaryChek = true;
        }
        Log.d(TAG, "whenComplete: ANTES DEL WHILEEEE");
        while(noteCheck && summaryChek){
            currentlist = new ArrayList();
            Log.d(TAG, "whenComplete: NOTES:\n" + notes.toString());
            Log.d(TAG, "whenComplete: SUMMARIES:\n" + summaries.toString());
            notes.sort(Comparator.comparing(ProNote::getDate));
            summaries.sort(Comparator.comparing(ProSummary::getDate));
            ArrayList agregated = new ArrayList<>();
            if(notes.size()>summaries.size()){
                int i = 0;
                for (int j = 0; j < notes.size();) {
                    if(!(i==summaries.size())) {
                        if ((notes.get(j).getDate().compareTo(summaries.get(i).getDate())) < 0) {
                            agregated.add(notes.get(j++));
                        } else {
                            agregated.add(summaries.get(i++));
                        }
                    } else {
                        agregated.add(notes.get(j++));
                    }
                }
                while (i<summaries.size()){agregated.add(summaries.get(i++));}
            } else { // notes, summaries
                int i = 0;
                for (int j = 0; j < summaries.size();) {
                    if(!(i==notes.size())) {
                        if ((summaries.get(j).getDate().compareTo(notes.get(i).getDate())) < 0) {
                            agregated.add(summaries.get(j++));
                        } else {
                            agregated.add(notes.get(i++));
                        }
                    }else {
                        agregated.add(summaries.get(j++));
                    }
                }
                while (i<notes.size()){agregated.add(notes.get(i++));}
            }

            currentlist = agregated;
            Log.d(TAG, "whenComplete: " + currentlist.toString());

            //that was huge but now that its done we'll update our recyclerView to display
            //all the notes and summaries.
            RecyclerView recyclerView = view.findViewById(R.id.edit_recycler);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(new AllAdapter(currentlist, R.layout.layout_note, HomeFragment.this::OnItemClick));
            noteCheck = false;
            summaryChek = false;
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (howDeep){
            case 1:
                if(item.getItemId()==121){ // update
                    updateCourse(((Course)currentlist.get(item.getGroupId())));
                }else{ // delete
                    deleteCourse("" + ((Course)currentlist.get(item.getGroupId())).getId());
                }
                break;
            case 2:
                if(item.getItemId()==121){ // update
                    updateSubject(((Subject)currentlist.get(item.getGroupId())));
                }else{ // delete
                    deleteSubject("" + ((Subject)currentlist.get(item.getGroupId())).getId());
                }
                break;
            case 3:
                if(item.getItemId()==121){ // update
                    if(currentlist.get(item.getGroupId()) instanceof ProNote){
                        updateNote((ProNote) currentlist.get(item.getGroupId()));
                    } else{
                        updateSummary((ProSummary) currentlist.get(item.getGroupId()));
                    }
                }else{ // delete
                    if(currentlist.get(item.getGroupId()) instanceof ProNote){
                        deleteNote("" + ((ProNote) currentlist.get(item.getGroupId())).getId());
                    } else{
                        deleteSummary("" + ((ProSummary) currentlist.get(item.getGroupId())).getId());
                    }
                }
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void updateSummary(ProSummary summary) {
        final EditText input3 = (new EditText(getContext()));
        input3.setElegantTextHeight(true);
        input3.setText(summary.getContent());
        new MaterialAlertDialogBuilder(getContext())
                .setTitle(getResources().getString(R.string.dialogLevel3TitleUpdate2))
                .setView(input3)
                .setCancelable(false)
                .setNegativeButton(R.string.label_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton(R.string.label_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        api.setSummary("" + summary.getId(), input3.getText().toString()).enqueue(new Callback<ResponseOk>() {
                            @Override
                            public void onResponse(Call<ResponseOk> call, Response<ResponseOk> response) {
                                refreshListsMaterials("" + subjectId);
                            }

                            @Override
                            public void onFailure(Call<ResponseOk> call, Throwable t) {
                                Toast.makeText(getContext(), "Ha habido un problema de conexión", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .show();
    }

    private void updateNote(ProNote note) {
        final EditText input3 = (new EditText(getContext()));
        input3.setElegantTextHeight(true);
        input3.setText(note.getContent());
        new MaterialAlertDialogBuilder(getContext())
                .setTitle(getResources().getString(R.string.dialogLevel3TitleUpdate))
                .setView(input3)
                .setCancelable(false)
                .setNegativeButton(R.string.label_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton(R.string.label_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        api.setNote("" + note.getId(), input3.getText().toString()).enqueue(new Callback<ResponseOk>() {
                            @Override
                            public void onResponse(Call<ResponseOk> call, Response<ResponseOk> response) {
                                refreshListsMaterials("" + subjectId);
                            }

                            @Override
                            public void onFailure(Call<ResponseOk> call, Throwable t) {
                                Toast.makeText(getContext(), "Ha habido un problema de conexión", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .show();
    }

    private void updateSubject(Subject subject) {
        final EditText input2 = (new EditText(getContext()));
        input2.setElegantTextHeight(true);
        input2.setText(subject.getName());
        new MaterialAlertDialogBuilder(getContext())
                .setTitle(getResources().getString(R.string.dialogLevel2TitleUpdate))
                .setView(input2)
                .setCancelable(false)
                .setNegativeButton(R.string.label_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton(R.string.label_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        api.setSubject("" + courseId, input2.getText().toString(), "123456").enqueue(new Callback<ResponseOk>() {
                            @Override
                            public void onResponse(Call<ResponseOk> call, Response<ResponseOk> response) {
                                refreshListSubjects();
                            }

                            @Override
                            public void onFailure(Call<ResponseOk> call, Throwable t) {
                                Toast.makeText(getContext(), "Ha habido un problema de conexión", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .show();
    }

    private void updateCourse(Course course) {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("fbuid", Context.MODE_PRIVATE);
        final EditText input = (new EditText(getContext()));
        input.setElegantTextHeight(true);
        input.setText(course.getName());
        new MaterialAlertDialogBuilder(getContext())
                .setTitle(getResources().getString(R.string.dialogLevel1Title))
                .setView(input)
                .setCancelable(false)
                .setNegativeButton(R.string.label_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton(R.string.label_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        api.setCourse(course.getId(), input.getText().toString()).enqueue(new Callback<ResponseOk>() {
                            @Override
                            public void onResponse(Call<ResponseOk> call, Response<ResponseOk> response) {
                                refreshListCourses();
                            }

                            @Override
                            public void onFailure(Call<ResponseOk> call, Throwable t) {
                                Toast.makeText(getContext(), "Ha habido un problema de conexión", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .show();
    }

    private void deleteCourse(String id){
        api.delCourse(id).enqueue(new Callback<ResponseOk>() {
            @Override
            public void onResponse(Call<ResponseOk> call, Response<ResponseOk> response) {
                refreshListCourses();
            }

            @Override
            public void onFailure(Call<ResponseOk> call, Throwable t) {

            }
        });
    }
    private void deleteSubject(String id){
        api.delSubject(id).enqueue(new Callback<ResponseOk>() {
            @Override
            public void onResponse(Call<ResponseOk> call, Response<ResponseOk> response) {
                refreshListSubjects();
            }

            @Override
            public void onFailure(Call<ResponseOk> call, Throwable t) {

            }
        });
    }
    private void deleteNote(String id){
        api.delNote(id).enqueue(new Callback<ResponseOk>() {
            @Override
            public void onResponse(Call<ResponseOk> call, Response<ResponseOk> response) {
                refreshListsMaterials("" + subjectId);
            }

            @Override
            public void onFailure(Call<ResponseOk> call, Throwable t) {

            }
        });
    }
    private void deleteSummary(String id){
        api.delSummary(id).enqueue(new Callback<ResponseOk>() {
            @Override
            public void onResponse(Call<ResponseOk> call, Response<ResponseOk> response) {
                refreshListsMaterials("" + subjectId);
            }

            @Override
            public void onFailure(Call<ResponseOk> call, Throwable t) {

            }
        });
    }
}
