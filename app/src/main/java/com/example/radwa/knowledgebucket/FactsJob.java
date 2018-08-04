package com.example.radwa.knowledgebucket;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.radwa.knowledgebucket.model.Fact;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.radwa.knowledgebucket.MainActivity.Facts_key;

public class FactsJob extends JobService {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
  private ChildEventListener mChildEventListener;

    @Override
    public boolean onStartJob(JobParameters job) {
        mFirebaseDatabase= FirebaseDatabase.getInstance();

        Log.v("DDSDDS","ddjdj")  ;
//
        mMessagesDatabaseReference = mFirebaseDatabase.getReference("Facts").child("1");

        mMessagesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String fact=dataSnapshot.getValue().toString();
                SharedPreferencesMethods.savePreferencesString(getApplicationContext(),Facts_key,fact);
                Log.d("DDSDDS",fact)  ;
                Intent intent = new Intent(getApplicationContext(), FactsAppWidget.class);
                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), FactsAppWidget.class));
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
                sendBroadcast(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });



        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
