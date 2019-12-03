package com.deepjose.ateneanote.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.deepjose.ateneanote.MainActivity;
import com.deepjose.ateneanote.R;
import com.deepjose.ateneanote.User;
import com.deepjose.ateneanote.api.apiClient;
import com.deepjose.ateneanote.interfaces.apiService;
import com.deepjose.ateneanote.responses.Course;
import com.deepjose.ateneanote.responses.Note;
import com.deepjose.ateneanote.responses.ResponseUser;
import com.deepjose.ateneanote.responses.Subject;
import com.deepjose.ateneanote.responses.Summary;

import org.w3c.dom.Text;

import java.net.HttpURLConnection;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllAdapter extends RecyclerView.Adapter<AllAdapter.AllViewHolder> {

    private List<Object> list;
    private int layout;


    public AllAdapter(List<Object> list, int layout) {

        /*
        if(list.get(0) instanceof Course){
            this.setListCourses(((List<Course>)(Object)list));
        } else if(list.get(0) instanceof Subject){
            this.setListSubjects(((List<Subject>)(Object)list));
        } else if(list.get(0) instanceof Summary){
            this.setListSummaries(((List<Summary>)(Object)list));
        } else if(list.get(0) instanceof Note){
            this.setListNotes(((List<Note>)(Object)list));
        }
        */

        this.list = list;
        this.layout = layout;
    }

    /*
    private void setListNotes(List<Note> list) {}
    private void setListSummaries(List<Summary> list) {}
    private void setListSubjects(List<Subject> list) {}
    private void setListCourses(List<Course> list) {}
     */


    @NonNull
    @Override
    public AllViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context)
                                  .inflate(layout, parent, false);
        return new AllViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllViewHolder holder, int position) {
        holder.BindHolder(list.get(position)) ;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AllViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView poster;

        public AllViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.itemTitle);
            poster = itemView.findViewById(R.id.itemPoster);
        }


        public void BindHolder(Object o) {
            if(o instanceof Course){
                this.BindHolder((Course)o);
            } else if(o instanceof Subject){
                this.BindHolder((Subject)o);
            } else if(o instanceof Summary){
                this.BindHolder((Summary)o);
            } else if(o instanceof Note){
                this.BindHolder((Note)o);
            }
        }

        public void BindHolder(Course course) {
            title.setText(course.getName());
        }
        public void BindHolder(Subject subject) {
            title.setText(subject.getName());
        }
        public void BindHolder(Summary summary) {
            title.setText(summary.getContent());
        }
        public void BindHolder(Note note) {
            title.setText(note.getContent());
        }

    }
}