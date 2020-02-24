package com.example.hydrus.backend;

import android.content.Context;
import android.content.Intent;

import com.example.hydrus.system.RepeatingAlarm;

public class BackendCommunicationController {

    private static int BACKEND_MESSAGE_DOWNLOAD_ALARM_SERVICE_ID = 12478;
    private static int BACKEND_MESSAGE_UPLOAD_ALARM_SERVICE_ID = 5634;
    private static long BACKEND_MESSAGE_DOWNLOAD_ALARM_SERVICE_MILLISECOND_DELAY = 3600000;
    private static long BACKEND_MESSAGE_UPLOAD_ALARM_SERVICE_MILLISECOND_DELAY = 3600000;

    private final Intent BACKEND_MESSAGE_DOWNLOAD_ACTION_INTENT_TO_REPEAT;
    private final Intent BACKEND_MESSAGE_UPLOAD_ACTION_INTENT_TO_REPEAT;
    private final Context controllerApplicationContext;

    private BackendCommunicationController(Context context) {
        this.controllerApplicationContext = context.getApplicationContext();
        this.BACKEND_MESSAGE_DOWNLOAD_ACTION_INTENT_TO_REPEAT = new Intent(this.controllerApplicationContext, BackendMessageDownloadAction.class);
        this.BACKEND_MESSAGE_UPLOAD_ACTION_INTENT_TO_REPEAT = new Intent(this.controllerApplicationContext, BackendMessageUploadAction.class);
    }

    private static BackendCommunicationController _getGlobalBackendComunicationControllerForContext(Context context) {
        return new BackendCommunicationController(context);
    }

    public static void startBackendCommunicationIfNotAlreadyRunning(Context context) {
        BackendCommunicationController globalBackendCommunicationDaemon = BackendCommunicationController._getGlobalBackendComunicationControllerForContext(context);
        globalBackendCommunicationDaemon._startRepeatingBackendMessageDownloadActionIfNotAlreadyRunning();
        globalBackendCommunicationDaemon._startRepeatingBackendMessageUploadActionIfNotAlreadyRunning();
    }

    private void _startRepeatingBackendMessageDownloadActionIfNotAlreadyRunning() {
        boolean messageDownloadingServiceHasStarted = RepeatingAlarm.hasAlarmToRepeatWithIntentAndAlarmServiceIDInContext(BACKEND_MESSAGE_DOWNLOAD_ACTION_INTENT_TO_REPEAT, BACKEND_MESSAGE_DOWNLOAD_ALARM_SERVICE_ID, this.controllerApplicationContext);
        if (!messageDownloadingServiceHasStarted) {
            this._startRepeatingBackendMessageDownloadAction();
        }
    }

    private void _startRepeatingBackendMessageDownloadAction() {
        RepeatingAlarm.startAlarmToRepeatIntentEveryNMillisecondsWithAlarmServiceIDInContext(BACKEND_MESSAGE_DOWNLOAD_ACTION_INTENT_TO_REPEAT, BACKEND_MESSAGE_DOWNLOAD_ALARM_SERVICE_MILLISECOND_DELAY, BACKEND_MESSAGE_DOWNLOAD_ALARM_SERVICE_ID, this.controllerApplicationContext);
    }

    private void _startRepeatingBackendMessageUploadActionIfNotAlreadyRunning() {
        boolean messageQueueUploadingServiceHasStarted = RepeatingAlarm.hasAlarmToRepeatWithIntentAndAlarmServiceIDInContext(BACKEND_MESSAGE_UPLOAD_ACTION_INTENT_TO_REPEAT, BACKEND_MESSAGE_UPLOAD_ALARM_SERVICE_ID, this.controllerApplicationContext);
        if (!messageQueueUploadingServiceHasStarted) {
            this._startRepeatingBackendMessageUploadAction();
        }
    }

    private void _startRepeatingBackendMessageUploadAction() {
        RepeatingAlarm.startAlarmToRepeatIntentEveryNMillisecondsWithAlarmServiceIDInContext(BACKEND_MESSAGE_UPLOAD_ACTION_INTENT_TO_REPEAT, BACKEND_MESSAGE_UPLOAD_ALARM_SERVICE_MILLISECOND_DELAY, BACKEND_MESSAGE_UPLOAD_ALARM_SERVICE_ID, this.controllerApplicationContext);
    }
}
