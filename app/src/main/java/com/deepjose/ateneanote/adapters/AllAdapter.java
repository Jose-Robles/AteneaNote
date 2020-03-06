package com.deepjose.ateneanote.adapters;

import android.content.Context;
import android.view.ContextMenu;
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
import com.deepjose.ateneanote.responses.ProNote;
import com.deepjose.ateneanote.responses.ProSummary;
import com.deepjose.ateneanote.responses.ResponseUser;
import com.deepjose.ateneanote.responses.Subject;
import com.deepjose.ateneanote.responses.Summary;

import org.w3c.dom.Text;

import java.net.HttpURLConnection;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<? extends Object> list;
    private int layout;
    private MyItemsListener myItemsListenerRecycler;
    private static int TYPE_OTHER = 1;
    private static int TYPE_SUMMARY = 2;


    public AllAdapter(List<? extends Object> list, int layout, MyItemsListener myItemsListener) {

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
        this.myItemsListenerRecycler = myItemsListener;
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
        View view;
        if(viewType == TYPE_OTHER) {
            view = LayoutInflater.from(context)
                    .inflate(layout, parent, false);
        } else {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.layout_summary, parent, false);
        }
        return new AllViewHolder(view, myItemsListenerRecycler);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((AllViewHolder) holder).BindHolder(list.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) instanceof ProSummary) {
            return TYPE_SUMMARY;

        } else {
            return TYPE_OTHER;
        }
    }

    public void onBindViewHolder(@NonNull AllViewHolder holder, int position) {
        holder.BindHolder(list.get(position)) ;
        //holder.title.setText(((Course)list.get(position)).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AllViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
        public TextView title;
        public ImageView poster;
        CardView cardview;
        MyItemsListener myItemsListener;

        public AllViewHolder(@NonNull View itemView, MyItemsListener myItemsListener) {
            super(itemView);
            title = itemView.findViewById(R.id.itemTitle);
            poster = itemView.findViewById(R.id.itemPoster);

            this.myItemsListener = myItemsListener;

            itemView.setOnClickListener(this);
        }


        public void BindHolder(Object o) {
            if(o instanceof Course){
                this.BindHolder((Course)o);
            } else if(o instanceof Subject){
                this.BindHolder((Subject)o);
            } else if(o instanceof ProSummary){
                this.BindHolder((ProSummary)o);
            } else if(o instanceof ProNote){
                this.BindHolder((ProNote)o);
            }
        }

        public void BindHolder(Course course) {
            title.setText(course.getName());
            itemView.findViewById(R.id.layout_course).setOnCreateContextMenuListener(this);
        }
        public void BindHolder(Subject subject) {
            title.setText(subject.getName());
            itemView.findViewById(R.id.layout_subject).setOnCreateContextMenuListener(this);
        }
        public void BindHolder(ProSummary summary) {
            title.setText(summary.getContent());
            itemView.findViewById(R.id.layout_summary).setOnCreateContextMenuListener(this);
        }
        public void BindHolder(ProNote note) {
            title.setText(note.getContent());
            itemView.findViewById(R.id.layout_note).setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View view) {
            myItemsListener.OnItemClick(getAdapterPosition());
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(this.getAdapterPosition(), 121, 0, "Update");
            contextMenu.add(this.getAdapterPosition(), 122, 1, "Delete");
        }
    }

    public interface MyItemsListener{
        void OnItemClick(int position);
    }

}