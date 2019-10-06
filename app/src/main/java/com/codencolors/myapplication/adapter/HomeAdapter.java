package com.codencolors.myapplication.adapter;

import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codencolors.myapplication.R;
import com.codencolors.myapplication.helper.FullScreenVideoView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<String> videoList;
    private Context context;

    public HomeAdapter(List<String> videoList, Context context) {
        this.videoList = videoList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_video_home, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Uri path = Uri.parse(videoList.get(position));
        setVideoView(holder, path);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.video_view_home) FullScreenVideoView fullScreenVideoView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void setVideoView(ViewHolder holder, Uri videoPath){
        holder.fullScreenVideoView.seekTo(100);
        holder.fullScreenVideoView.setVideoURI(videoPath);
        holder.fullScreenVideoView.requestFocus();
        holder.fullScreenVideoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);

            //Set the surface holder height to the screen dimensions
            holder.fullScreenVideoView.start();
        });
    }
}
