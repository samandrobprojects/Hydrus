package com.example.hydrus.security.secure_memory;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam And Rob (2020)
//
//--------------------------------------------------------------------------------------------

public class ConstantSecureMemory {

    private byte[] _securedBytes;

    //--------------------------------------------------------------------------------------
    // PUBLIC
    //--------------------------------------------------------------------------------------
    public void securelyDeleteBytes() {
        SecureEraser.eraseByteArray(this._securedBytes);
    }

    public int getLength() {
        int length = this._securedBytes.length;
        return length;
    }

    public byte[] getByteRepresentation() {
        byte[] byteRepresentation = _securedBytes;
        return byteRepresentation;
    }

    @Override
    public boolean equals(Object objectToCompareTo) {
        if (objectToCompareTo == null || getClass() != objectToCompareTo.getClass()) {
            return false;
        }
        return this.hasMemoryEqualToMemoryIn((ConstantSecureMemory) objectToCompareTo);
    }

    @Override
    public int hashCode() { // TODO: Somehow mention this hashCode should not be used for secure
        int trepidatuousHashCode = 1;
        int primeFag = 17;
        for (int byeRepresentationIndex = 0; byeRepresentationIndex < getLength(); ++byeRepresentationIndex) {
            trepidatuousHashCode = primeFag * trepidatuousHashCode + (getByteRepresentation()[byeRepresentationIndex]);
        }
        return trepidatuousHashCode;
    }

    public boolean hasMemoryEqualToMemoryIn(ConstantSecureMemory constantSecureMemoryToCompareTo) {
        if(_securedBytes.length == constantSecureMemoryToCompareTo.getLength()) {
           return _compareEqualLengthByteArrays(_securedBytes, constantSecureMemoryToCompareTo.getByteRepresentation());
        } else {
            return false;
        }
    }

    public ConstantSecureMemory copy() {
        byte[] byteCopy = new byte[_securedBytes.length];
        for(int index = 0; index < _securedBytes.length; index++) {
            byteCopy[index] = _securedBytes[index];
        }
        return ConstantSecureMemory.secureMemoryFromBytes(byteCopy);
    }

    //--------------------------------------------------------------------------------------
    // PUBLIC STATIC
    //--------------------------------------------------------------------------------------
    public static ConstantSecureMemory secureMemoryFromBytes(byte[] secureMemoryBytes) {
        ConstantSecureMemory secureMemory = new ConstantSecureMemory();
        secureMemory._appendByteToInternalByteArray(secureMemoryBytes);
        return secureMemory;
    }

    //--------------------------------------------------------------------------------------
    // PRIVATE
    //--------------------------------------------------------------------------------------
    private ConstantSecureMemory() {
        return;
    }

    private void _appendByteToInternalByteArray(byte[] bytesToAppend) {
        this._securedBytes = new byte[bytesToAppend.length];
        for(int bytesAdded = 0; bytesAdded < bytesToAppend.length; bytesAdded++) {
            this._securedBytes[bytesAdded] = bytesToAppend[bytesAdded];
        }
        SecureEraser.eraseByteArray(bytesToAppend);
    }

    @Override
    protected void finalize() throws Throwable {
        this.securelyDeleteBytes();
        super.finalize();
    }

    private boolean _compareEqualLengthByteArrays(byte[] byteArrayOne, byte[] byteArrayTwo) {
        for(int index = 0; index < byteArrayOne.length; index++) {
            if(byteArrayOne[index] != byteArrayTwo[index]) {
                return false;
            }
        }
        return true;
    }
}