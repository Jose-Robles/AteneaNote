package com.deepjose.ateneanote.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.deepjose.ateneanote.MainDrawer;
import com.deepjose.ateneanote.R;
import com.deepjose.ateneanote.adapters.AllAdapter;
import com.deepjose.ateneanote.api.apiClient;
import com.deepjose.ateneanote.interfaces.apiService;
import com.deepjose.ateneanote.responses.Course;
import com.deepjose.ateneanote.responses.ResponseCourses;

import java.util.List;

public class HomeFragment extends Fragment {

    private static List<Object> list;
    private static boolean coursesOk = false;
    private HomeViewModel homeViewModel;
    private apiService api;
    private RecyclerView recycler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //My code now

        api = apiClient.getService("http://ateneanote.deepjose/flaskapp/api/");

        recycler = (RecyclerView) this.getActivity().findViewById(R.id.edit_recycler);
        recycler.setLayoutManager(new GridLayoutManager(this.getContext(), 2));

        SharedPreferences localData = this.getActivity().getSharedPreferences("localData", Context.MODE_PRIVATE);
        this.getCourses(localData.getString("FBUID", ""));
        if(coursesOk) {
            recycler.setAdapter(new AllAdapter(list, R.layout.layout_course));
        }
        return root;
    }

    private void getCourses (String fbuid) {
        api.getCourses(fbuid).enqueue(new Callback<ResponseCourses>() {
            @Override
            public void onResponse(Call<ResponseCourses> call, Response<ResponseCourses> response) {
                HomeFragment.list = (List<Object>) response.body();
                HomeFragment.coursesOk = true;

            }

            @Override
            public void onFailure(Call<ResponseCourses> call, Throwable t) {
                Toast.makeText(getContext(), "Fallo al cargar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}