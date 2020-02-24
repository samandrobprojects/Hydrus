package com.example.hydrus;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.hydrus.general_purpose.Pair;
import com.example.hydrus.security.secure_memory.ConstantSecureMemory;
import com.example.hydrus.system.GlobalContext;
import com.example.hydrus.user.UserContactList;
import com.example.hydrus.utilities.Base64Converter;
import com.example.hydrus.utilities.StringByteConverter;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UserContactListTest {

    @Test
    public void testUserContactListFirstTimeSetup() {
        String dummyID = "dummy";
        String dummyKey = "MIIBCgKCAQEAoI2UVXrY//t07pQbYgmWLpyyZSSDZNmgkQ5VIsiVbLCotrYsS84jirDk48KzRTnSG0ENidPDFQ51ML0Yd7KuFhI1zQ+fCNTCw5st9SK70K9d0HqrqzYYlE4toCOyjve8w7fzhloIVNDencAHtLSECKTXz5pSA/smrvruO3mWxPxcj0XxyrV8MzBlgkwTA1eL6A8roAUeddEbIPgF60tGWfHwOSgvkkTZW+OJtPiSYJYs4iRRRLjLfTe98Ic1xy2N7kMamwLWnY2pVTYEufZ/xatZa3o3EUxfvfIBzkSy5ck7xVCUPdTH1wyyNN/AIyACMk4C6S7zHC2Bpx0PVzUW3wIDAQAB";
        Context appContext = InstrumentationRegistry.getTargetContext();
        GlobalContext.setGlobalContext(appContext);
        Pair.pairWithFirstAndSecondObjects(dummyID, dummyKey);
        UserContactList.setupContactList();
        ConstantSecureMemory secureUsercode = ConstantSecureMemory.secureMemoryFromBytes(StringByteConverter.convertStringToByteArray(dummyID));
        Maybe<ConstantSecureMemory> maybeTestPublicKey = UserContactList.getPublicKeyForUsercode(secureUsercode);
        if(maybeTestPublicKey.isNotNothing()) {
            return;
        }
        assertTrue(false);
    }

    @Test
    public void testAddUserContact() {
        String dummyID = "dummy";
        String dummyKey = "MIIBCgKCAQEAoI2UVXrY//t07pQbYgmWLpyyZSSDZNmgkQ5VIsiVbLCotrYsS84jirDk48KzRTnSG0ENidPDFQ51ML0Yd7KuFhI1zQ+fCNTCw5st9SK70K9d0HqrqzYYlE4toCOyjve8w7fzhloIVNDencAHtLSECKTXz5pSA/smrvruO3mWxPxcj0XxyrV8MzBlgkwTA1eL6A8roAUeddEbIPgF60tGWfHwOSgvkkTZW+OJtPiSYJYs4iRRRLjLfTe98Ic1xy2N7kMamwLWnY2pVTYEufZ/xatZa3o3EUxfvfIBzkSy5ck7xVCUPdTH1wyyNN/AIyACMk4C6S7zHC2Bpx0PVzUW3wIDAQAB";
        ConstantSecureMemory secureKey = ConstantSecureMemory.secureMemoryFromBytes(Base64Converter.decryptBASE64(dummyKey));
        Context appContext = InstrumentationRegistry.getTargetContext();
        GlobalContext.setGlobalContext(appContext);
        Pair<String, String> usercodePublicKeyPair =  Pair.pairWithFirstAndSecondObjects(dummyID, dummyKey);
        List<Pair<String, String>> usercontactListPair = new ArrayList<Pair<String, String>>();
        usercontactListPair.add(usercodePublicKeyPair);
        UserContactList.setupContactList();
        UserContactList.setListOfUsercodeAndPublicKeyPairsAsContactList(usercontactListPair);
        ConstantSecureMemory secureUsercode = ConstantSecureMemory.secureMemoryFromBytes(StringByteConverter.convertStringToByteArray(dummyID));
        Maybe<ConstantSecureMemory> maybeTestPublicKey = UserContactList.getPublicKeyForUsercode(secureUsercode);
        if(maybeTestPublicKey.isNotNothing()) {
            assertTrue(secureKey.hasMemoryEqualToMemoryIn(maybeTestPublicKey.object()));
        }
        assertTrue(false);
    }
}