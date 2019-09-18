package cimarronez.org.periodico.Servicio;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import cimarronez.org.periodico.MainActivity;
import cimarronez.org.periodico.R;


/**
 * Created by miituo on 15/02/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public String donde;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Map<String,String> data = remoteMessage.getData();
        String idPush = data.get("idPush");
        String title=data.get("title");
        String msg=data.get("text");
        String tarifa=data.get("tarifa");
        donde = "reporte_mensual";
//        sendNotification(remoteMessage.getNotification().getBody());
        sendNotification(idPush,title,msg,tarifa);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
    }

    private void sendNotification(String idPush,String title,String msg,String tarifa)
    {
        Intent intent;

            intent = new Intent(this,MainActivity.class);
            intent.putExtra("idPush",idPush);
            intent.putExtra("tarifa",tarifa);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pending=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        //Uri sound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //Uri sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.pushfin);
        Uri sound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder noti=new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true)
                .setContentIntent(pending)
                .setSound(sound);
        NotificationManager nmg=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        nmg.notify(0,noti.build());
    }
}
