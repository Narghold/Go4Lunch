package com.dubois.yann.go4lunch.controller.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.dubois.yann.go4lunch.Go4Lunch;
import com.dubois.yann.go4lunch.R;
import com.dubois.yann.go4lunch.controller.MainActivity;
import com.dubois.yann.go4lunch.model.UserChoice;
import com.dubois.yann.go4lunch.model.details.RestaurantDetails;
import com.dubois.yann.go4lunch.model.details.ResultDetails;
import com.dubois.yann.go4lunch.model.list.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        PendingIntent contentPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        //Get current user
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mCurrentUser = mAuth.getCurrentUser();

        //Notification manager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Reminder";
            String description ="Reminder for choice";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        //Get database info
        FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
        //Get user choice from database
        assert mCurrentUser != null;
        mDatabase.collection("user_choice").whereEqualTo("userId", mCurrentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    //Serialize result to UserChoice.class
                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                    UserChoice userChoice = documentSnapshot.toObject(UserChoice.class);
                    //Get restaurant information
                    String key = getString(R.string.google_place_key);
                    //Language for call
                    String language = Locale.getDefault().getLanguage();
                    if(!language.equals("fr")){
                        //If language is not fr then language is "en" by default
                        language = "en";
                    }
                    //Call for restaurant information
                    assert userChoice != null;
                    Call<ResultDetails> call = Go4Lunch.createRetrofitClient().getPlaceInformation(userChoice.getPlaceId(), key, language);
                    call.enqueue(new Callback<ResultDetails>() {
                        @Override
                        public void onResponse(Call<ResultDetails> call, Response<ResultDetails> response) {
                            if (response.isSuccessful()){
                                ResultDetails result = response.body();
                                if (result != null){
                                    RestaurantDetails restaurant = result.getRestaurantDetails();
                                    //Notification builder
                                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), "channel")
                                            .setSmallIcon(R.drawable.ic_go4lunch)
                                            .setContentTitle(getString(R.string.notification_title))
                                            .setContentText(getString(R.string.eat_notification) + " " + restaurant.getName())
                                            .setContentIntent(contentPendingIntent)
                                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                            .setAutoCancel(true);
                                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                                    // notificationId is a unique int for each notification
                                    notificationManager.notify(0, notificationBuilder.build());
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<ResultDetails> call, Throwable t) {
                            Log.d("Null", t.getMessage());
                        }
                    });
                }
            }
        });

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true; //The job is rescheduled if it fails
    }

    public static void scheduleJob(Context context) {
        ComponentName serviceComponent = new ComponentName(context, NotificationJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(0, serviceComponent)
                .setMinimumLatency(1000)
                .build();
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo);
    }
}
