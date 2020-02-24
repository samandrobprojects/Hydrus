package com.example.hydrus.activities;
// -----------------------------------------------------------------------------
// This class is used as a substitute to AppCompatActivity, and wraps methods
// provided by Android into methods that are more appropriate to writing better
// code.
// This is our attempt at cleaning up the code in this class.
//
// AUTHOR: Sam and Rob (2020)
// -----------------------------------------------------------------------------
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.hydrus.system.GlobalContext;
import com.example.hydrus.backend.BackendCommunicationController;

public class BaseActivity extends AppCompatActivity {

    public static final int ERROR_NO_XML_ID_SET = -1;

    public static String convertBaseActivityErrorCodeToHumanReadableString(int baseActivityErrorCode) {
        switch(baseActivityErrorCode) {
            case ERROR_NO_XML_ID_SET:
                return "Error: No XML File ID Set For Activity View";
            default:
                return "Invalid Error Code For BaseActivity";
        }
    }

    protected void startActivityAsOnlyTask(Class<?> classOfActivity) {
        Intent intent = new Intent(this, classOfActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    // -----------------------------------------------------------------------------
    // Methods To Overwrite
    // -----------------------------------------------------------------------------
    protected int activityViewXmlFileID() {
        return ERROR_NO_XML_ID_SET;
    }

    protected void setupActivity() {
        return;
    }

    protected void unloadActivity() { return; }

    protected void loadActivity() { return; }

    // -----------------------------------------------------------------------------
    // Wrapped Android Methods
    // -----------------------------------------------------------------------------

    // Wrapping findViewById()
    protected View getViewFromActivityViewWithLayoutID(int layoutID) {
        return this.findViewById(layoutID);
    }

    // Wrapping setContentView()
    private void _setViewBasedOnXMLFileWithID(int activityViewXMLFileID) {
        this.setContentView(activityViewXMLFileID);
    }

    // -----------------------------------------------------------------------------
    // Methods That Are Made Obselete
    // -----------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this._setViewBasedOnXMLFileWithID(this.activityViewXmlFileID());
        this.setupActivity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GlobalContext.setGlobalContext(this.getApplicationContext());
        BackendCommunicationController.startBackendCommunicationIfNotAlreadyRunning(this.getApplicationContext());
        System.out.println("LOAD");
        this.loadActivity();
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("UNLOAD");
        this.unloadActivity();
    }
}
