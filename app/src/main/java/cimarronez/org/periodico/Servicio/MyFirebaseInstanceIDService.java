package cimarronez.org.periodico.Servicio;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;



/**
 * Created by miituo on 15/02/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    final String Tag="NOTICIAS";
    @Override
    public void onTokenRefresh() {

        String token = FirebaseInstanceId.getInstance().getToken();
        //Log.d(Tag,token);
        //sendRegistrationToServer(token);
    }
    private void sendRegistrationToServer(String Token) {
        //IinfoClient.getInfoClientObject().getClient().setToken(Token);
        //SharedPreferences app_preferences;
        //app_preferences= getSharedPreferences("miituo", Context.MODE_PRIVATE);
        /*AsyncTask<Void,Void,Void> sendToken=new AsyncTask<Void, Void, Void>() {
            String ErrorCode="";

            @Override
            protected void onCancelled() {
                super.onCancelled();
                Toast msg=Toast.makeText(getApplicationContext(),ErrorCode,Toast.LENGTH_LONG);
                msg.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                ApiClient client=new ApiClient();
                try
                {
                    client.updateToken("ClientUser");
                }
                catch (IOException ex)
                {
                    ErrorCode=ex.getMessage();
                    this.cancel(true);
                }
                return null;
            }
        };
        sendToken.execute();*/
    }

}
