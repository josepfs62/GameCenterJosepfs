package com.example.gamecenterjosepfs.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamecenterjosepfs.R;
import com.example.gamecenterjosepfs.entities.Scores;

import java.util.ArrayList;

public class ListScoresAdapter extends RecyclerView.Adapter<ListScoresAdapter.ScoreViewHolder> {

    ArrayList<Scores> scoresList;

    public ListScoresAdapter(ArrayList<Scores> scoresList) {
        this.scoresList = scoresList;
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_item, null, false);
        return new ScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        holder.viewName.setText(scoresList.get(position).getName() + "  " + scoresList.get(position).getTime() + "  " + scoresList.get(position).getScore() + "  " + scoresList.get(position).getGame());
    }

    @Override
    public int getItemCount() {
        return scoresList.size();
    }

    public class ScoreViewHolder extends RecyclerView.ViewHolder {

        TextView viewName;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);

            viewName = itemView.findViewById(R.id.viewName);
        }
    }
}
