package com.myapplicationdev.android.demodialog.p11_knowyournationalday;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class MessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // SMS messages are retrieved from the intent's extra using the key "pdus"
        Bundle extras = intent.getExtras();
        Object[] smsExtra = (Object[]) extras.get("pdus");

        // There could be more than one message received,
        // therefore looped through each message.
        for (int i = 0; i < smsExtra.length; i++) {

            // Reconstruct the SMS message
            SmsMessage sms = SmsMessage.createFromPdu((byte[]) smsExtra[i]);

            // Obtain the Originate Phone Number
            String address = sms.getOriginatingAddress();

            // Obtain the Body Message
            String body = sms.getMessageBody();

        }

    }
}
