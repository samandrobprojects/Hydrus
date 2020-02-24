package com.example.hydrus.system;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam And Rob (2020)
//
//--------------------------------------------------------------------------------------------

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.hydrus.backend.BackendCommunicationController;

public class OnBootBroadcastReceiver extends BroadcastReceiver {

    //--------------------------------------------------------------------------------------
    // PUBLIC
    //--------------------------------------------------------------------------------------
    @Override
    public void onReceive(Context context, Intent intentBeingReceived) {
        BackendCommunicationController.startBackendCommunicationIfNotAlreadyRunning(context.getApplicationContext());
    }
}
