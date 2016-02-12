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
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            //on recupere
            SmsMessage[] msgs = Telephony.Sms.Intents.getMessagesFromIntent(intent);
            // Enfin, traiter les messages !
            for (SmsMessage smsMessage : msgs) {
                String from = smsMessage.getOriginatingAddress();
                // Filtre très basique des SMS sur n° de tel. (Une espèce de liste noire en somme !)
                if (from.compareTo("+33672425178") == 0) {
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
                        Toast.makeText(context, "Message de " + from + " numero : " + numero + " longitude : " + longitude + " latitude : " + latitude, Toast.LENGTH_LONG).show();
                    }


                    // Enfin, puisque le SMS concerne que notre application, stopper sa propagation aux autres applications !
                    abortBroadcast();
                }
            }
        }
    }
}

