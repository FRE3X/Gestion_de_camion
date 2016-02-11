package com.example.fre3x.gestion_de_camion;

import android.telephony.SmsManager;

/**
 * Created by laura on 11/02/2016.
 */
public class EnvoieMessage {
    public  EnvoieMessage (String tel, String message){
        SmsManager.getDefault().sendTextMessage(tel, null, message, null, null);

    }
}
