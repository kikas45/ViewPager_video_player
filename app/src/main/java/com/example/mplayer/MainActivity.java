package com.example.mplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MainActivity extends AppCompatActivity {
    ViewPager2 viewPager2;
    videoadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(R.style.Theme_MPlayer);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        /// ofline proprty


        DatabaseReference scoresRef = FirebaseDatabase.getInstance().getReference().child("look");
        scoresRef.keepSynced(true);


        viewPager2=(ViewPager2)findViewById(R.id.vpager);

        Query query = FirebaseDatabase.getInstance().getReference().child("look");
        FirebaseRecyclerOptions<videomodel> options =
                new FirebaseRecyclerOptions.Builder<videomodel>()
                        .setQuery(query, videomodel.class)
                        .build();


        adapter=new videoadapter(options);
        viewPager2.setAdapter(adapter);

        ProgressBar progressBar = findViewById(R.id.videoProgressBar);

    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        setProgress(100);
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}

