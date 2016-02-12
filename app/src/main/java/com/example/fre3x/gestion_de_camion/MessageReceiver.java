package com.example.fre3x.gestion_de_camion;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MessageReceiver extends BroadcastReceiver {
    public MessageReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Cette méthode est appellé quand le broadcast s'acitve.

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            //on recupere
            SmsMessage[] msgs = Telephony.Sms.Intents.getMessagesFromIntent(intent);
            // Enfin, traiter les messages !
            for (SmsMessage smsMessage : msgs) {
                String from = smsMessage.getOriginatingAddress();
                String msg = smsMessage.getMessageBody();
                //on a recuperer le texte du message
                //on va le decouper en plusieurs parties delimite par une virgule
                //et la premiere partie doit etre "coordonnes gps"
                //si ce n'est pas ca, le sms ne correspond pas
                //si oui, on recupere les autre parties, donc le numero, et les coordonnes
                String[] separated = msg.split(",");
                if(separated[0].equals("coordonnesGps")){
                    String numero =separated[1].trim();
                    String longitude = separated[2].trim();
                    String latitude = separated[3].trim();
                    //On va ensuite afficher l'activite qui contient la map en passant les positions et le numéro en paramétre.
                    Intent map =new Intent(context, MapsActivity.class);
                    map.putExtra("longitude",longitude);
                    map.putExtra("latitude", latitude);
                    map.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(map);
                }


                    // Enfin, puisque le SMS concerne que notre application, stopper sa propagation aux autres applications !
                    abortBroadcast();
                }
            }
        }
}

