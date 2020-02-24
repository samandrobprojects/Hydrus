package com.example.hydrus.activities;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hydrus.R;

public class MainMenuActivity extends BaseActivity {

    private Button writeMessageButton;
    private Button addContectButton;
    private Button readAllMessagesButton;
    private Button settingsButton;

    @Override
    protected int activityViewXmlFileID() {
        return R.layout.activity_main;
    }

    @Override
    protected void setupActivity() {
        this._setupButtonsOnActivityView();
        //new JobInfo.Builder()
    }

    private void _setupButtonsOnActivityView() {
        this._setupSettingButton();
        this._setupAddContactButton();
        this._setupWriteMessagButton();
        this._setupReadAllMessageButton();
    }
    private void _setupSettingButton() {
        this.settingsButton = (Button)this.getViewFromActivityViewWithLayoutID(R.id.settingsButtonID);
        this.settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainMenuActivity.this, "faggot3", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void _setupWriteMessagButton() {
        this.writeMessageButton = (Button)this.getViewFromActivityViewWithLayoutID(R.id.writeMessageButtonID);
        this.writeMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainMenuActivity.this, "faggot2", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void _setupAddContactButton() {
        this.addContectButton = (Button)this.getViewFromActivityViewWithLayoutID(R.id.addContactButtonID);
        this.addContectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainMenuActivity.this, "faggot4", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void _setupReadAllMessageButton() {
        this.readAllMessagesButton = (Button)this.getViewFromActivityViewWithLayoutID(R.id.readAllMessagesButtonID);
        this.readAllMessagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainMenuActivity.this, "faggot1", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
