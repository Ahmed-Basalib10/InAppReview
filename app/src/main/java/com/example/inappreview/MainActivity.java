package com.example.inappreview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.inappreview.databinding.ActivityMainBinding;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.Task;

public class MainActivity extends AppCompatActivity {
    /**
     * IN this project will implement in app review
     * Ahmed Basalib
     * 2021
     */
    private ReviewManager manager;
    private ReviewInfo reviewInfo;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // step 1 create instance of ReviewManager
        manager = ReviewManagerFactory.create(this);

        binding.reviewus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initialreview();
            }
        });
    }

    // step 2
    private void initialreview() {
        Task<ReviewInfo> request = manager.requestReviewFlow();
        request.addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
            @Override
            public void onComplete(@NonNull Task<ReviewInfo> task) {
                if (task.isSuccessful()) {
                    // We can get the ReviewInfo object
                    reviewInfo = task.getResult();
                    lunchappreview(reviewInfo);
                } else {
                    // There was some problem, log or handle the error code.
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // step 3
    private void lunchappreview(ReviewInfo reviewInfo) {
        if(reviewInfo != null){
            Task<Void> flow = manager.launchReviewFlow(this, reviewInfo);
            flow.addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d("Oncomplete()","Done");
                }
            });
        }
    }
}