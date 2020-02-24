package com.example.hydrus.backend;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam and Rob (2020)
//
//--------------------------------------------------------------------------------------------

import com.example.hydrus.security.secure_memory.SecureToken;
import com.example.hydrus.security.secure_memory.VariableSecureMemory;
import com.example.hydrus.user.UserSecureToken;
import com.example.hydrus.utilities.hydrus_exceptions.InvalidHydrusHTTPSResponseException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HydrusHttpsRequest {

    //--------------------------------------------------------------------------------------------
    // PUBLIC STATIC
    //--------------------------------------------------------------------------------------------
    public static void makeHydrusSecuredHTTPSPOSTRequestFromURLWithParameterToValueMappingAndCallback(final String stringUrlToMakeHTTPSPOSTRequestTo, final HashMap<String, String> mappingOfParametersToValues, final HttpsRequestCallback httpsRequestCallback) {
        Thread threadToSendMesasgeToServer = new Thread(_runnableActionToMakeHydrusSecuredHTTPSPOSTRequestFromURLWithParameterToValueMappingAndCallback(stringUrlToMakeHTTPSPOSTRequestTo, mappingOfParametersToValues, httpsRequestCallback));
        threadToSendMesasgeToServer.start();
    }

    //--------------------------------------------------------------------------------------------
    // PRIVATE STATIC
    //--------------------------------------------------------------------------------------------
    private static HttpsURLConnection _getHttpsPOSTConnectionWithoutHostnameVerificationWithURLString(String urlStringInQuestion) throws Exception {
        URL urlToConnectTo = new URL(urlStringInQuestion);
        HttpsURLConnection httpsPostConnection = (HttpsURLConnection) urlToConnectTo.openConnection();
        httpsPostConnection.setReadTimeout(15000);
        httpsPostConnection.setConnectTimeout(15000);
        httpsPostConnection.setRequestMethod("POST");
        httpsPostConnection.setDoInput(true);
        httpsPostConnection.setDoOutput(true);
        httpsPostConnection.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        return httpsPostConnection;
    }

    private static void _configureHttpsConnectionToTrustAllSSLCertificates(HttpsURLConnection urlHttpsConnection) throws Exception {
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, _getTrustManagerWhoTrustsAllCertificates(), new java.security.SecureRandom());
        urlHttpsConnection.setSSLSocketFactory(sslContext.getSocketFactory());
    }

    private static VariableSecureMemory _getSecureSectionOfPOSTRequestAsBytes() {
        byte[] hiddenIdentifierParameterBytes = {(byte)'&', (byte)'h', (byte)'i', (byte)'d', (byte)'d', (byte)'e',(byte)'n',(byte)'_',(byte)'i',(byte)'d',(byte)'e',(byte)'n',(byte)'t',(byte)'i',(byte)'f',(byte)'i',(byte)'e',(byte)'r',(byte)'='};
        VariableSecureMemory secureHiddenIdentifierParameterBytes = VariableSecureMemory.secureBytesFromBytes(hiddenIdentifierParameterBytes);
        SecureToken hiddenIdentifier = UserSecureToken.getSecureToken().copy();
        secureHiddenIdentifierParameterBytes.appendBytes(hiddenIdentifier.getByteRepresentation());
        return secureHiddenIdentifierParameterBytes;
    }

    private static void _sendSecureSectionOfPOSTRequestThroughHTTPSOutputStreamWriter(VariableSecureMemory secureHiddenIdentifierBytes, BufferedWriter httpsURLConnectionOutputStreamWriter) throws Exception {
        for(int count = 0; count < secureHiddenIdentifierBytes.getByteRepresentation().length; count++) {
            httpsURLConnectionOutputStreamWriter.write((int) secureHiddenIdentifierBytes.getByteRepresentation()[count]);
        }
        secureHiddenIdentifierBytes.securelyDeleteBytes();
    }

    private static void _writeParameterValueMappingToHTTPSUrlConnection(final HashMap<String, String> mappingOfParametersToValues, HttpsURLConnection urlHttpsConnection) throws Exception {
        OutputStream httpsURLConnectionOutputStream = urlHttpsConnection.getOutputStream();
        BufferedWriter httpsURLConnectionOutputStreamWriter = new BufferedWriter(new OutputStreamWriter(httpsURLConnectionOutputStream, "UTF-8"));
        String postDataForHTTPSRequest = _constructPOSTDataForHTTPRequestWithParameterToValueMapping(mappingOfParametersToValues);
        httpsURLConnectionOutputStreamWriter.write(postDataForHTTPSRequest);
        VariableSecureMemory secureHiddenIdentifierBytes = _getSecureSectionOfPOSTRequestAsBytes();
        _sendSecureSectionOfPOSTRequestThroughHTTPSOutputStreamWriter(secureHiddenIdentifierBytes,httpsURLConnectionOutputStreamWriter);
        httpsURLConnectionOutputStreamWriter.flush();
        httpsURLConnectionOutputStreamWriter.close();
        httpsURLConnectionOutputStream.close();
    }

    private static int _retriveHTTPResponseCodeFromHTTPSURLConnection(HttpsURLConnection urlHttpsConnection) throws Exception {
        int responseCode = urlHttpsConnection.getResponseCode();
        return responseCode;
    }

    private static Maybe<String> _maybeRetriveOKHTTPResponseBodyAsStringFromHTTPSURLConnection(HttpsURLConnection urlHttpsConnection) throws Exception {
        int httpResponseCode = _retriveHTTPResponseCodeFromHTTPSURLConnection(urlHttpsConnection);
        if (httpResponseCode == HttpsURLConnection.HTTP_OK) {
            return Maybe.asObject(_retriveOKHTTPResponseBodyAsStringFromHTTPSURLConnection(urlHttpsConnection));
        } else {
            return Maybe.asNothing();
        }
    }

    private static String _retriveOKHTTPResponseBodyAsStringFromHTTPSURLConnection(HttpsURLConnection urlHttpsConnection) throws Exception {
        String extractedHTTPSResponseBody = "";
        String line;
        BufferedReader br=new BufferedReader(new InputStreamReader(urlHttpsConnection.getInputStream()));
        while ((line=br.readLine()) != null) {
            extractedHTTPSResponseBody += line;
        }
        return extractedHTTPSResponseBody;
    }

    private static void _handleStringResponseWithCallback(String stringResponseBody,final HttpsRequestCallback httpsRequestCallback) {
        Maybe<JSON> apiResponseJSON = JSON.maybeJSONFromString(stringResponseBody);
        if(apiResponseJSON.isNotNothing()) {
            httpsRequestCallback.receivedResponseFromHTTPSServer(apiResponseJSON.object());
        } else {
            throw new InvalidHydrusHTTPSResponseException("No valid response");
        }
    }

    private static void _handleResponseFromHTTPSUrlConnectionWithCallback(HttpsURLConnection urlHttpsConnection, final HttpsRequestCallback httpsRequestCallback) throws Exception {
        Maybe<String> maybeResponseBodyFromOKHTTPResponse = _maybeRetriveOKHTTPResponseBodyAsStringFromHTTPSURLConnection(urlHttpsConnection);
        if(maybeResponseBodyFromOKHTTPResponse.isNotNothing()) {
            _handleStringResponseWithCallback(maybeResponseBodyFromOKHTTPResponse.object(), httpsRequestCallback);
        } else {
            throw new InvalidHydrusHTTPSResponseException("No valid response");
        }
    }

    private static Runnable _runnableActionToMakeHydrusSecuredHTTPSPOSTRequestFromURLWithParameterToValueMappingAndCallback(final String stringUrlToMakeHTTPSPOSTRequestTo, final HashMap<String, String> mappingOfParametersToValues, final HttpsRequestCallback httpsRequestCallback) {
        return new Runnable() {
            public void run() {
                try {
                    _makeHydrusSecuredHTTPSPOSTRequestFromURLWithParameterToValueMappingAndCallback(stringUrlToMakeHTTPSPOSTRequestTo, mappingOfParametersToValues, httpsRequestCallback);
                } catch (Exception exception) {
                    _handleHydrusHTTPSRequestExceptionWithCallback(httpsRequestCallback);
                }
            }
        };
    }

    private static void _handleHydrusHTTPSRequestExceptionWithCallback( final HttpsRequestCallback httpsRequestCallback) {
        //System.out.println("ERRORSKDJHD2: "+exception.getLocalizedMessage());
        HttpsRequestErrorProtocol error = HttpsRequestErrorProtocol.newConnectionError();
        httpsRequestCallback.receivedErrorWhenAttemptingHTTPSConnection(error);
    }

    private static void _makeHydrusSecuredHTTPSPOSTRequestFromURLWithParameterToValueMappingAndCallback(final String stringUrlToMakeHTTPSPOSTRequestTo, final HashMap<String, String> mappingOfParametersToValues, final HttpsRequestCallback httpsRequestCallback) throws Exception {
        HttpsURLConnection httpsPostRequestConnection = _getHttpsPOSTConnectionWithoutHostnameVerificationWithURLString(stringUrlToMakeHTTPSPOSTRequestTo);
        _configureHttpsConnectionToTrustAllSSLCertificates(httpsPostRequestConnection);
        _writeParameterValueMappingToHTTPSUrlConnection(mappingOfParametersToValues, httpsPostRequestConnection);
        _handleResponseFromHTTPSUrlConnectionWithCallback(httpsPostRequestConnection, httpsRequestCallback);
    }

    private static String _constructPOSTDataForHTTPRequestWithParameterToValueMapping(HashMap<String, String> parameterToValueMapping) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : parameterToValueMapping.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }

    private static TrustManager[] _getTrustManagerWhoTrustsAllCertificates() {
        return new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };
    }
}
