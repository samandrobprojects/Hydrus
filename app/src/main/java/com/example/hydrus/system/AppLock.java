package com.example.hydrus.system;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam And Rob (2020)
//
//--------------------------------------------------------------------------------------------

import com.example.hydrus.security.secure_memory.SecureEraser;
import com.example.hydrus.user.UserSecureMessage;
import com.example.hydrus.user.UserSecureToken;
import com.example.hydrus.user.Usercode;

public class AppLock {

    private static boolean _shouldLock = true;
    private static boolean _appIsUnlocked = false;

    //--------------------------------------------------------------------------------------
    // PUBLIC STATIC
    //--------------------------------------------------------------------------------------
    public static void setShouldLock(boolean shouldLock) {
        _shouldLock = shouldLock;
        return;
    }

    public static void lockIfShouldLockSet() {
        if(_shouldLock) {
            _lock();
        }
    }

    public static boolean appIsUnlocked() {
        boolean unlocked = _appIsUnlocked;
        return unlocked;
    }

    public static void unlock() {
        UserSecureToken.restoreSecureTokenFromEncryptedMemory();
    }

    //--------------------------------------------------------------------------------------
    // PRIVATE STATIC
    //--------------------------------------------------------------------------------------
    private static void _lock() {
        UserSecureToken.eraseSecureTokenFromUnencryptedMemory();
        UserSecureMessage.securelyEraseMessage();
        SecureEraser.eraseAllApplciationMemory();
        _appIsUnlocked = false;
    }
}