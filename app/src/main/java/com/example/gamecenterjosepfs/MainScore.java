package com.example.gamecenterjosepfs;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamecenterjosepfs.adapters.ListScoresAdapter;
import com.example.gamecenterjosepfs.db.DbScores;
import com.example.gamecenterjosepfs.entities.Scores;

import java.util.ArrayList;

public class MainScore extends AppCompatActivity {

    RecyclerView scoreList;

    ArrayList<Scores> scoresArrayList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_board);
        scoreList = findViewById(R.id.scoreList);
        scoreList.setLayoutManager(new LinearLayoutManager(this));

        DbScores dbScores = new DbScores(this);

        scoresArrayList = new ArrayList<>();

        ListScoresAdapter adapter = new ListScoresAdapter(dbScores.showScores());
        scoreList.setAdapter(adapter);
    }
}
