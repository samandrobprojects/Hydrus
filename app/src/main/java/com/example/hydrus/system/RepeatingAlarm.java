package com.example.hydrus.system;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam And Rob (2020)
//
//--------------------------------------------------------------------------------------------

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class RepeatingAlarm {

    //--------------------------------------------------------------------------------------
    // PUBLIC STATIC
    //--------------------------------------------------------------------------------------
    public static void startAlarmToRepeatIntentEveryNMillisecondsWithAlarmServiceIDInContext(Intent intentToRepeat, long nMilliseconds, int alarmServiceID, Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent intentToRepeatAsAPendingIntent = PendingIntent.getBroadcast(context, alarmServiceID, intentToRepeat, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), nMilliseconds, intentToRepeatAsAPendingIntent);
    }

    public static boolean hasAlarmToRepeatWithIntentAndAlarmServiceIDInContext(Intent intentForAlarmService, int alarmServiceID, Context context) {
        boolean alarmServiuceIsCurrentlyRunning = (PendingIntent.getBroadcast(context, alarmServiceID, intentForAlarmService, PendingIntent.FLAG_NO_CREATE) != null);
        return alarmServiuceIsCurrentlyRunning;
    }
}
