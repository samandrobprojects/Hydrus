package com.example.hydrus.system;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam And Rob (2020)
//
//--------------------------------------------------------------------------------------------

import android.content.Context;

import com.example.hydrus.utilities.hydrus_exceptions.NoGlobalContextSetException;

public class GlobalContext {

    private static Context _context = null;

    //--------------------------------------------------------------------------------------
    // PUBLIC STATIC
    //--------------------------------------------------------------------------------------
    public static void setGlobalContext(Context context) {
        _context = context;
        return;
    }

    public static Context getGlobalContext() {
        _EXCEPTION_throwNewExceptionIfGlobalContextNotSet(_context);
        return _context;
    }

    //--------------------------------------------------------------------------------------
    // PRIVATE STATIC
    //--------------------------------------------------------------------------------------
    private static void _EXCEPTION_throwNewExceptionIfGlobalContextNotSet(Context context) {
        if(context == null) {
            throw new NoGlobalContextSetException("No Context Set");
        }
    }
}