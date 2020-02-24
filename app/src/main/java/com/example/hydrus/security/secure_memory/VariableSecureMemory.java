package com.example.hydrus.security.secure_memory;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam And Rob (2020)
//
//--------------------------------------------------------------------------------------------

public class VariableSecureMemory {

    private static final int _MAX_BYTE_LENGTH = 99;

    private byte[] _securedBytes;
    private int _currentLength;

    //--------------------------------------------------------------------------------------
    // PUBLIC
    //--------------------------------------------------------------------------------------
    public void securelyDeleteBytes() {
        SecureEraser.eraseByteArray(this._securedBytes);
        _setSecureByteArrayToDefault();
    }

    public int getLength() {
        int length = _currentLength;
        return length;
    }

    public byte[] getByteRepresentation() {
        byte[] byteRepresentation = _securedBytes;
        return byteRepresentation;
    }

    // TODO: Potentially throw an exception on overflow here
    public void appendBytes(byte[] bytesToAppend) {
        if(this._currentLength + bytesToAppend.length > _MAX_BYTE_LENGTH) {
            System.out.println("TOO MANY");
            return;
        }
        _appendByteToInternalByteArray(bytesToAppend);
        SecureEraser.eraseByteArray(bytesToAppend);
    }

    public void removeByte() {
        if(this._currentLength == 0) return;
        this._currentLength--;
    }

    //--------------------------------------------------------------------------------------
    // PUBLIC STATIC
    //--------------------------------------------------------------------------------------
    public static VariableSecureMemory secureBytesFromBytes(byte[] bytes) {
        if(bytes.length > _MAX_BYTE_LENGTH) return null;
        VariableSecureMemory secureBytes = new VariableSecureMemory();
        secureBytes.appendBytes(bytes);
        SecureEraser.eraseByteArray(bytes);
        return secureBytes;
    }

    // TODO: Setup trim function but messages all need to be same size anyway

    public static VariableSecureMemory emptySecureBytes() {
        VariableSecureMemory emptySecureBytes = new VariableSecureMemory();
        return emptySecureBytes;
    }

    //--------------------------------------------------------------------------------------
    // PRIVATE
    //--------------------------------------------------------------------------------------
    private VariableSecureMemory() {
        _setSecureByteArrayToDefault();
        return;
    }

    private void _appendByteToInternalByteArray(byte[] bytesToAppend) {
        for(int bytesAdded = 0; bytesAdded < bytesToAppend.length; bytesAdded++) {
            this._securedBytes[bytesAdded+this._currentLength] = bytesToAppend[bytesAdded];
        }
    }

    @Override
    protected void finalize() throws Throwable {
        this.securelyDeleteBytes();
        super.finalize();
    }

    private void _setSecureByteArrayToDefault() {
        this._securedBytes = new byte[_MAX_BYTE_LENGTH];
        for(int byteIndex = 0; byteIndex < _MAX_BYTE_LENGTH; byteIndex++) {
            this._securedBytes[byteIndex] = (byte)'c';
        }
        this._currentLength = 0;
    }
}