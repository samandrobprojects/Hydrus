package com.example.hydrus.activities;

import com.example.hydrus.system.AppLock;

public class SecureBaseActivity extends BaseActivity {

    @Override
    protected void unloadActivity() {
        super.unloadActivity();
        AppLock.lockIfShouldLockSet();
        AppLock.setShouldLock(true);
    }

    @Override
    protected void loadActivity() {
        super.loadActivity();
        if(!AppLock.appIsUnlocked()) {
            AppLock.unlock();
        }
    }

    protected void securelyStartActivity(Class<?> activity) {
        AppLock.setShouldLock(false);
        System.out.println("SECURELY STARTING");
        this.startActivityAsOnlyTask(activity);
    }
}
