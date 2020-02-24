package com.example.hydrus.user;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam And Rob (2020)
//
//----------------------S----------------------------------------------------------------------

import com.example.hydrus.system.LocalStorage;
import com.example.hydrus.security.encryption.EncryptedMessageIVPair;
import com.example.hydrus.security.encryption.SecureSymetricEncryption;
import com.example.hydrus.security.secure_memory.ConstantSecureMemory;
import com.example.hydrus.utilities.Base64Converter;
import com.example.hydrus.utilities.StringByteConverter;
import com.example.hydrus.utilities.hydrus_exceptions.InvalidContactListSetupException;

import java.util.ArrayList;

import functional.Maybe;

public class UserContactListOld {

    private static final String _DUMMY_KEY_STRING = "MIIBCgKCAQEAoI2UVXrY//t07pQbYgmWLpyyZSSDZNmgkQ5VIsiVbLCotrYsS84jirDk48KzRTnSG0ENidPDFQ51ML0Yd7KuFhI1zQ+fCNTCw5st9SK70K9d0HqrqzYYlE4toCOyjve8w7fzhloIVNDencAHtLSECKTXz5pSA/smrvruO3mWxPxcj0XxyrV8MzBlgkwTA1eL6A8roAUeddEbIPgF60tGWfHwOSgvkkTZW+OJtPiSYJYs4iRRRLjLfTe98Ic1xy2N7kMamwLWnY2pVTYEufZ/xatZa3o3EUxfvfIBzkSy5ck7xVCUPdTH1wyyNN/AIyACMk4C6S7zHC2Bpx0PVzUW3wIDAQAB";
    private static final String _DUMMY_ID_STRING = "dummy";
    private static final String _CONTACT_LIST_ENCRYPTED_KEY_ID = "_CONTACT_LIST_ENCRYPTED_KEY_ID";
    private static final String _CONTACT_LIST_LOCAL_STORAGE_KEY = "_CONTACT_LIST_LOCAL_STORAGE_KEY";
    private static final String _CONTACT_LIST_STRING_SEPERATION_CHARACTER = "@";

    private static String[] _contactList = null;
    private static ArrayList<ConstantSecureMemory> _contactNameList = new ArrayList<ConstantSecureMemory>();
    private static ArrayList<ConstantSecureMemory> _contactListKeys = new ArrayList<ConstantSecureMemory>();

    //--------------------------------------------------------------------------------------
    // PUBLIC STATIC
    //--------------------------------------------------------------------------------------
    public static void setupContactListFirstTime() {
        SecureSymetricEncryption.initializeSymetricKeyWithKeyID(_CONTACT_LIST_ENCRYPTED_KEY_ID);
        _setupContactListWithDummyAccount();
    }

    public static void setupContactList() {
        Maybe<String> maybeContactListStorageString = LocalStorage.getStringWithKey(_CONTACT_LIST_LOCAL_STORAGE_KEY);
        if (maybeContactListStorageString.isNotNothing()) {
            _setupContactListWithContactListStorageString(maybeContactListStorageString.object());
        } else {
            _EXCEPTION_InvalidContactListSetupException();
        }
    }

    public static void addNewContactWithSecureUsercodeAndSecurePublicKey(ConstantSecureMemory secureUsercode, ConstantSecureMemory securePublicKey) {
        _contactNameList.add(secureUsercode);
        _contactListKeys.add(securePublicKey);
        String contactListStorageString = _getUpdatedUserContactListString();
        LocalStorage.storeStringWithKey(contactListStorageString, _CONTACT_LIST_LOCAL_STORAGE_KEY);
    }

    public static Maybe<ConstantSecureMemory> getPublicKeyForUsercode(ConstantSecureMemory secureUsercodeBytes) {
        for (int index = 0; index < _contactNameList.size(); index++) {
            if(_contactNameList.get(index).hasMemoryEqualToMemoryIn(secureUsercodeBytes)) {
                return Maybe.asObject(_contactListKeys.get(index));
            }
        }
        return Maybe.asNothing();
    }

    //--------------------------------------------------------------------------------------
    // PRIVATE STATIC
    //--------------------------------------------------------------------------------------
    private static void _EXCEPTION_InvalidContactListSetupException() {
        throw new InvalidContactListSetupException("Could not setup contact list");
    }

    private static void _setupContactListWithDummyAccount() {
        EncryptedMessageIVPair encryptedDummyKey = _getEncryptedDummyKey();
        EncryptedMessageIVPair encryptedDummyID = _getEncryptedDummyID();
        String contactListStorageString = encryptedDummyID.getEncryptedMessage() + _CONTACT_LIST_STRING_SEPERATION_CHARACTER + encryptedDummyID.getIV() + _CONTACT_LIST_STRING_SEPERATION_CHARACTER + encryptedDummyKey.getEncryptedMessage() + _CONTACT_LIST_STRING_SEPERATION_CHARACTER + encryptedDummyKey.getIV();
        LocalStorage.storeStringWithKey(contactListStorageString, _CONTACT_LIST_LOCAL_STORAGE_KEY);
    }

    private static EncryptedMessageIVPair _getEncryptedDummyKey() {
        Maybe<EncryptedMessageIVPair> maybeDummyKeyEncypted = _maybeGetEncryptedDummyKey();
        if(maybeDummyKeyEncypted.isNotNothing()) {
            return maybeDummyKeyEncypted.object();
        } else {
            _EXCEPTION_InvalidContactListSetupException();
            return null;
        }
    }

    private static Maybe<EncryptedMessageIVPair> _maybeGetEncryptedDummyKey() {
        ConstantSecureMemory dummyKeySecureBytes = ConstantSecureMemory.secureMemoryFromBytes(Base64Converter.decryptBASE64(_DUMMY_KEY_STRING));
        return SecureSymetricEncryption.encryptWithSecretKeyWithKeyID(dummyKeySecureBytes, _CONTACT_LIST_ENCRYPTED_KEY_ID);
    }

    private static EncryptedMessageIVPair _getEncryptedDummyID() {
        Maybe<EncryptedMessageIVPair> maybeDummyIDEncypted = _maybeGetEncryptedDummyID();
        if(maybeDummyIDEncypted.isNotNothing()) {
            return maybeDummyIDEncypted.object();
        } else {
            _EXCEPTION_InvalidContactListSetupException();
            return null;
        }
    }

    private static Maybe<EncryptedMessageIVPair> _maybeGetEncryptedDummyID() {
        ConstantSecureMemory dummyIDSecureBytes = ConstantSecureMemory.secureMemoryFromBytes(StringByteConverter.convertStringToByteArray(_DUMMY_ID_STRING));
        return SecureSymetricEncryption.encryptWithSecretKeyWithKeyID(dummyIDSecureBytes, _CONTACT_LIST_ENCRYPTED_KEY_ID);
    }

    private static void _setupContactFromContactListStorageComponentsWithGivenIndex(String[] contactListStorageStringComponents, int contactListComponentsIndex) {
        String encryptedName = contactListStorageStringComponents[contactListComponentsIndex];
        String encryptedNameIV = contactListStorageStringComponents[contactListComponentsIndex + 1];
        String encryptedKey = contactListStorageStringComponents[contactListComponentsIndex + 2];
        String encryptedKeyIV = contactListStorageStringComponents[contactListComponentsIndex + 3];
        _setupContactForNameAndNameIVandKeyandKeyIV(encryptedName, encryptedNameIV, encryptedKey, encryptedKeyIV);

    }

    private static void _setupContactForNameAndNameIVandKeyandKeyIV(String encryptedName, String encryptedNameIV, String encryptedKey, String encryptedKeyIV) {
        EncryptedMessageIVPair encryptedNamePair = EncryptedMessageIVPair.getPairWithEncryptedMessageAndIV(encryptedName, encryptedNameIV);
        EncryptedMessageIVPair encryptedKeyPair = EncryptedMessageIVPair.getPairWithEncryptedMessageAndIV(encryptedKey, encryptedKeyIV);
        Maybe<ConstantSecureMemory> maybeDecryptedName = SecureSymetricEncryption.decryptWithSecretKeyWithKeyID(encryptedNamePair, _CONTACT_LIST_ENCRYPTED_KEY_ID);
        Maybe<ConstantSecureMemory> maybeDecryptedKey = SecureSymetricEncryption.decryptWithSecretKeyWithKeyID(encryptedKeyPair, _CONTACT_LIST_ENCRYPTED_KEY_ID);
        _addContactIfItExistsWithMaybeNameAndMaybeKey(maybeDecryptedName, maybeDecryptedKey);
    }

    private static void _addContactIfItExistsWithMaybeNameAndMaybeKey(Maybe<ConstantSecureMemory> maybeDecryptedName,  Maybe<ConstantSecureMemory> maybeDecryptedKey) {
        if (maybeDecryptedKey.isNotNothing() && maybeDecryptedName.isNotNothing()) {
            _contactNameList.add(maybeDecryptedName.object());
            _contactListKeys.add(maybeDecryptedKey.object());
        } else {
            _EXCEPTION_InvalidContactListSetupException();
        }
    }

    private static void _setupContactListWithContactListStorageString(String contactListStorageString) {
        String[] contactListStorageStringComponents = contactListStorageString.split(_CONTACT_LIST_STRING_SEPERATION_CHARACTER);
        for (int contactListStorageStringComponentIndex = 0; contactListStorageStringComponentIndex < contactListStorageStringComponents.length - 3; contactListStorageStringComponentIndex++) {
            _setupContactFromContactListStorageComponentsWithGivenIndex(contactListStorageStringComponents, contactListStorageStringComponentIndex);
        }
    }
    private static String _getUpdatedUserContactListString() {
        String contactListStorageString = "";
        for(int index = 0; index < _contactNameList.size(); index++) {
            contactListStorageString += _getContactStorageListStringForContactWithIndex(index);
        }
        return contactListStorageString;
    }

    private static String _getContactStorageListStringForContactWithIndex(int index) {
        ConstantSecureMemory contactSecureName = _contactNameList.get(index);
        ConstantSecureMemory contactSecureKey = _contactListKeys.get(index);
        Maybe<EncryptedMessageIVPair> maybeEncryptedSecureName = SecureSymetricEncryption.encryptWithSecretKeyWithKeyID(contactSecureName, _CONTACT_LIST_ENCRYPTED_KEY_ID);
        Maybe<EncryptedMessageIVPair> maybeEncryptedSecureKey = SecureSymetricEncryption.encryptWithSecretKeyWithKeyID(contactSecureKey, _CONTACT_LIST_ENCRYPTED_KEY_ID);
        return _createContactStorageListStringComponentFromMaybeEncryptedNameAndMaybeEncryptedKey(maybeEncryptedSecureName, maybeEncryptedSecureKey);
    }

    private static String _createContactStorageListStringComponentFromMaybeEncryptedNameAndMaybeEncryptedKey(Maybe<EncryptedMessageIVPair> maybeEncryptedSecureName,  Maybe<EncryptedMessageIVPair> maybeEncryptedSecureKey) {
        if(maybeEncryptedSecureKey.isNotNothing() && maybeEncryptedSecureName.isNotNothing()) {
            return maybeEncryptedSecureName.object().getEncryptedMessage() + _CONTACT_LIST_STRING_SEPERATION_CHARACTER + maybeEncryptedSecureName.object().getIV() + _CONTACT_LIST_STRING_SEPERATION_CHARACTER + maybeEncryptedSecureKey.object().getEncryptedMessage() + _CONTACT_LIST_STRING_SEPERATION_CHARACTER + maybeEncryptedSecureKey.object().getIV();
        } else {
            _EXCEPTION_InvalidContactListSetupException();
            return null;
        }
    }
}
