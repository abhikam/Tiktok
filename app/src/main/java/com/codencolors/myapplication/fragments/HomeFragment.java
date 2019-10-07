package com.codencolors.myapplication.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.codencolors.myapplication.R;
import com.codencolors.myapplication.adapter.HomeAdapter;
import com.codencolors.myapplication.helper.FullScreenVideoView;
import com.codencolors.myapplication.helper.SnapHelperOneByOne;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    @BindView(R.id.rv_home) RecyclerView recyclerView;

    private static final String TAG = "HomeFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestForRead();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        setRecyclerView();

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            new VideosAsync(getContext()).execute();
        else
            requestForRead();

        return view;
    }

    private void setRecyclerView(){
        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    //get list of videos
    private List<String> getAllMedia() {
        HashSet<String> videoItemHashSet = new HashSet<>();
        String[] projection = { MediaStore.Video.VideoColumns.DATA};
        Cursor cursor = getContext().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection
                , null, null, null);
        try {
            cursor.moveToFirst();
            do{
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                videoItemHashSet.add(path);
            }
            while(cursor.moveToNext());

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>(videoItemHashSet);
    }


    private void requestForRead(){
        ActivityCompat.requestPermissions( getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
    }

    public class VideosAsync extends AsyncTask<Void, Void, List<String>> {
        private Context context;
        private List<String> videoList;

        public VideosAsync(Context context) {
            this.context = context;
            this.videoList = new ArrayList<>();
        }

        @Override
        protected List<String> doInBackground(Void... params) {
            videoList.addAll(getAllMedia());
            return videoList;
        }

        @Override
        protected void onPostExecute(List<String> videos) {
            HomeAdapter adapter = new HomeAdapter(videos, context);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onPreExecute() {
        }
    }
}