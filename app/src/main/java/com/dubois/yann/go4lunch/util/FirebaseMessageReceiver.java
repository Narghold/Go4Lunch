package com.dubois.yann.go4lunch.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.dubois.yann.go4lunch.R;
import com.dubois.yann.go4lunch.controller.MainActivity;
import com.dubois.yann.go4lunch.controller.SplashScreen;
import com.dubois.yann.go4lunch.model.User;
import com.dubois.yann.go4lunch.model.UserChoice;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessageReceiver extends FirebaseMessagingService {

    private String messageBody;

    // Override onMessageReceived() method to extract the title and body from the message passed in FCM
    @Override
    public void
    onMessageReceived(RemoteMessage message) {
        if (message.getNotification() != null) {
            setMessageBody();
            showNotification(message.getNotification().getTitle());
        }
    }

    // Method to display the notifications
    public void showNotification(String title) {

        Intent intent = new Intent(this, SplashScreen.class);
        String channel_id = "notification_channel";
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = null;
        while(messageBody == null){
             builder = new NotificationCompat.Builder(getApplicationContext(), channel_id)
                    .setSmallIcon(R.drawable.ic_go4lunch)
                    .setAutoCancel(true)
                    .setContentText(messageBody)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setOnlyAlertOnce(true)
                    .setContentIntent(pendingIntent);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // Check if the Android Version is greater than Oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channel_id, "firebase_notification", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        assert builder != null;
        notificationManager.notify(0, builder.build());
    }

    //Personalized the message notification in function of user choice place
    public void setMessageBody(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        assert firebaseUser != null;
        database.collection("user_choice").whereEqualTo("userId", firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    if (!task.getResult().getDocuments().isEmpty()){
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        UserChoice userChoice = documentSnapshot.toObject(UserChoice.class);
                        assert userChoice != null;
                        messageBody = getString(R.string.eat_notification) + " " + userChoice.getPlaceName();
                    }else {
                        messageBody = getString(R.string.default_notification_text);
                    }
                }
            }
        });
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }
}
